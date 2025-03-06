import java.util.ArrayList;

import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC0_Algorithm;

/*
 * Travail à réaliser
 * 1. Rappelez les règles de réécriture de l'algorithme d'élection d'un leader dans un arbre.
 *  1.1 Au départ, tous les sommets sont à l'état N (non actif).
 *      Pour chaque sommet, on regarde le nombre de voisins marqué N.
 *      Si le sommet ne possède qu'un voisin N, et que ce même voisin possède plus d'un voisin marqué N, alors ce sommet passe à l'état "F" (fail), et l'autre garde son état N.
 *      Si le sommet ne possède qu'un voisin N, et que ce même voisin possède aussi un seul voisin marqué N, alors par la méthode du 
 * 2. Implémentez votre algorithm en utilisant la bibliothèque ViSiDiA.
 * 
 * 3. Vérifiez que votre algorithme élit bien un (et un seul) leader. Assurez-vous qu'au moment du choix du leader il ne reste plus de noeud dans l'état "N".
 * 
 * 4. Modifiez votre programme de manière à ce que chaque noeiud affiche le nombre de voisins à l'état "N" qu'il possède. Pensez à utiliser la méthode putProperty.
 * `putProperty("Affichage", valeur à afficher, SimulationConstants.PropertyStatus.DISPLAYED);`
 */
public class Election extends LC0_Algorithm {
    private String neighborProperty = "neighborsCount";
    private void setNeigborsCount(int count){
        setLocalProperty(neighborProperty, count);
    }

    private int getNeighborsCount(){
        return (int) getLocalProperty(neighborProperty);
    }

    @Override
    public String getDescription() {
        return "Election algorithm using LC0";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setNeigborsCount(getArity());
        putProperty("Arity", SimulationConstants.PropertyStatus.DISPLAYED);
    }

    @Override
    protected void onStarCenter() {
        if (isRuleOne())
            ruleOne();
        else if (isRuleTwo())
            ruleTwo();
        putProperty("Nombre voisins", getNeighborsCount(), SimulationConstants.PropertyStatus.DISPLAYED);
    }

    @Override
    public Object clone() {
        return new Election();
    }

    public Boolean isRuleOne() {
        return getLocalProperty("label").equals("N")
                && getNeighborProperty("label").equals("N")
                && getNeighborsCount() == 1;
    }

    public void ruleOne() {
        setLocalProperty("label", "F");
        setNeighborProperty(neighborProperty, (int)getNeighborProperty(neighborProperty) -1);
    }

    public Boolean isRuleTwo() {
        return getLocalProperty("label").equals("N")
                && getNeighborsCount() == 0;
    }

    public void ruleTwo() {
        setLocalProperty("label", "E");
    }

}