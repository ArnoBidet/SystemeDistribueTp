import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC2_Algorithm;

public class Election extends LC2_Algorithm {
    private String neighborProperty = "neighborsCount";

    private int getNeighborsCount() {
        return (int)getLocalProperty(neighborProperty);
    }

    private void setNeighborsCount(int count) {
        setLocalProperty(neighborProperty, count);
    }

    private int countActiveNeigbors() {
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
        return "Election algorithm using LC2";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setNeighborsCount(getArity());
    }

    @Override
    protected void onStarCenter() {
        if (isRuleOne())
            ruleOne();
        else if (isRuleTwo())
            ruleTwo();
        else if (getLocalProperty("label").equals("F"))
            localTermination();
        setNeighborsCount(countActiveNeigbors());
        putProperty("Nombre voisins", getNeighborsCount(), SimulationConstants.PropertyStatus.DISPLAYED);
    }

    @Override
    public Object clone() {
        return new Election();
    }

    public Boolean isRuleOne() {
        return getLocalProperty("label").equals("N") && getNeighborsCount() > 0;
    }

    public void ruleOne() {
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if (getNeighborProperty(numPort, "label").equals("N") && getNeighborProperty(numPort, neighborProperty).equals(1)) {
                setNeighborProperty(numPort, "label", "F");
            }
        }
    }

    public Boolean isRuleTwo() {
        return getLocalProperty("label").equals("N")
                && getNeighborsCount() == 0;
    }

    public void ruleTwo() {
        setLocalProperty("label", "E");
        localTermination();
    }
}