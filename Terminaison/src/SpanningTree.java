
import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

/*
 * Exercice 1 - Question 4
 * Que se passerait-il si chaque noeud décidait d'exécuter localTermination() lorsque son compteur "a" vaut 5 ?
 * - Est-ce que tous les "a" seront égaux à 5 ?
 * - Un seul noeud aura un "a" égal à 5 (lequel à votre avis) ?
 * - Tous les noeuds auront des "a" égaux à 5 à l'exception d'un seul noeud (lequel à votre avis) ?
 *  ---- La bonne réponse consiste en ce que tous les noeuds n'arriveront pas à 5 en fonction de la topologie, par exemple, une ligne risque d'avoir plusieurs valeurs 4
 * 
 * Question 5
 * Que se passerait-il si un noeud décidait d'exécuter globalTermination() lorsque "a" vaut 5 ? Répondez d'abord à la question et ensuite vérifiez votre réponse par simulation.
 *  ---- On verrait alors un snapshot des distances des points par rapport au point le plus proche à laquelle tous les noeuds ont finit leur tache
 * 
 */
public class SpanningTree extends LC1_Algorithm {
    private String pAttr = "pAttr";
    private String aAttr = "aAttr";
    @Override
    public String getDescription() {
        return "Spanning Tree Algorithm using LC1";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setLocalProperty(pAttr, false);
        setLocalProperty(aAttr, -1);
    }

    @Override
    protected void onStarCenter() {
        boolean pAttrVal = (boolean)getLocalProperty(pAttr);
        int a_neigbors = 0;
        int lowestNeighbor_a_Attr = (int)getLocalProperty(aAttr);
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if (getLocalProperty("label").equals("N") && getNeighborProperty(numPort,"label").equals("A")) {
                setLocalProperty("label", "A");
                setDoorState(new MarkedState(true), numPort);
            } else if(isNeighborActive(numPort))
                a_neigbors++;
            if(pAttrVal && lowestNeighbor_a_Attr > (int)getNeighborProperty(numPort,"aAttr"))
                lowestNeighbor_a_Attr = (int)getNeighborProperty(numPort,"aAttr");
        }
        if(isTerminated(a_neigbors)){
            setLocalProperty("label", "W");
            setLocalProperty(pAttr, true);
        }
        if(pAttrVal)
            setLocalProperty(aAttr, lowestNeighbor_a_Attr + 1);
        putProperty(aAttr, getLocalProperty(aAttr), SimulationConstants.PropertyStatus.DISPLAYED);
        if((int)getLocalProperty(aAttr) == 5){
            localTermination();
        }
    }

    @Override
    public Object clone() {
        return new SpanningTree();
    }

    private boolean isTerminated(int a_neigbors){
        return a_neigbors == getActiveDoors().size();
    }

    private boolean isNeighborActive(int numPort){
        return getNeighborProperty(numPort,"label").equals("A") || getNeighborProperty(numPort,"label").equals("W");
    }
}