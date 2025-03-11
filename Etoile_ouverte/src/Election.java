import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC1_Algorithm;

public class Election extends LC1_Algorithm {
    private String neighborProperty = "neighborsCount";

    private int getNeighborsCount() {
        int count = 0;
        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            if (getNeighborProperty(numPort, "label").equals("N")) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String getDescription() {
        return "Election algorithm using LC1";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
    }

    @Override
    protected void onStarCenter() {
        for (int i = 0; i < getActiveDoors().size(); i++) {
            int numPort = getActiveDoors().get(i);
            if (isRuleOne(numPort))
                ruleOne(numPort);
            else if (isRuleTwo(numPort))
                ruleTwo(numPort);
            putProperty("Nombre voisins", getNeighborsCount(), SimulationConstants.PropertyStatus.DISPLAYED);
        }
    }

    @Override
    public Object clone() {
        return new Election();
    }

    public Boolean isRuleOne(int doorNumber) {
        return getLocalProperty("label").equals("N")
                && getNeighborProperty(doorNumber, "label").equals("N")
                && getNeighborsCount() == 1;
    }

    public void ruleOne(int doorNumber) {
        setLocalProperty("label", "F");
    }

    public Boolean isRuleTwo(int doorNumber) {
        return getLocalProperty("label").equals("N")
                && getNeighborsCount() == 0;
    }

    public void ruleTwo(int doorNumber) {
        setLocalProperty("label", "E");
    }
}