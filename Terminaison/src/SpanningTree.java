
import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class SpanningTree extends LC1_Algorithm {
    @Override
    public String getDescription() {
        return "Spanning Tree Algorithm using LC1";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
    }

    @Override
    protected void onStarCenter() {
        int a_neigbors = 0;
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if (getLocalProperty("label").equals("N") && getNeighborProperty(numPort,"label").equals("A")) {
                setLocalProperty("label", "A");
                setDoorState(new MarkedState(true), numPort);
            } else if(getNeighborProperty(numPort,"label").equals("A") || getNeighborProperty(numPort,"label").equals("W"))
                a_neigbors++;
        }
        if(a_neigbors == getActiveDoors().size())
            setLocalProperty("label", "W");
    }

    @Override
    public Object clone() {
        return new SpanningTree();
    }
}