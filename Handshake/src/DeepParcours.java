import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class DeepParcours extends LC0_Algorithm {
    String etatVoisins[];
    int portPere = -1;
    @Override
    public String getDescription() {
        return "Deep Parcours Algorithm using LC0";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        
        etatVoisins = new String[getArity()];

        for (int i = 0; i < getArity(); i++) {
            etatVoisins[i] = "N";
        }
    }

    @Override
    protected void onStarCenter() {
        etatVoisins[neighborDoor] =  getNeighborProperty("label").toString();
        if (isRuleOne()) {
            ruleOne();
        } else if (isRuleTwo()) {
            ruleTwo();
        }
    }

    @Override
    public Object clone() {
        return new DeepParcours();
    }

    public Boolean isRuleOne() {
        return getLocalProperty("label").equals("N") && getNeighborProperty("label").equals("A");
    }

    public void ruleOne() {
        setLocalProperty("label", "A");
        setNeighborProperty("label", "M");
        setDoorState(new MarkedState(true), neighborDoor);
        portPere = neighborDoor;
        etatVoisins[neighborDoor] = "M";

    }

    public Boolean isRuleTwo() {
        return getLocalProperty("label").equals("A")
                && getNeighborProperty("label").equals("M")
                && portPere == neighborDoor
                && nbVoisinsN() == 0;
    }

    public void ruleTwo() {
        setLocalProperty("label", "F");
        setNeighborProperty("label", "A");
        etatVoisins[neighborDoor] = "A";
    }

    public int nbVoisinsN(){
        int nbVoisinsN = 0;

        for (int i = 0; i < getArity(); i++) {
            if (etatVoisins[i] != null && etatVoisins[i].equals("N")) {
                nbVoisinsN++;
            }
        }
        return nbVoisinsN;
    }

}