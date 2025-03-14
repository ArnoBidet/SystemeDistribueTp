
import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class SpanningTree extends LC2_Algorithm {
    @Override
    public String getDescription() {
        return "Spanning Tree Algorithm using LC2";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
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
    protected void onStarCenter() {
        if (getLocalProperty("label").equals("A")){
            if(countActiveNeigbors() == 0){
                localTermination();
            }
            for(int i = 0; i < getActiveDoors().size(); i++){
                int numPort = getActiveDoors().get(i);
                if (getNeighborProperty(numPort, "label").equals("N")) {
                    setNeighborProperty(numPort, "label", "A");
                    setDoorState(new MarkedState(true), numPort);
                }
            }
        }
    }

    @Override
    public Object clone() {
        return new SpanningTree();
    }
}