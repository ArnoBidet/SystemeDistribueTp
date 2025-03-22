
import java.util.ArrayList;

import visidia.simulation.SimulationConstants;
import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class SpanningTreeAndElection extends LC2_Algorithm {
    private String neighborProperty = "neighborsCount";
    private String doorsProperty = "markedDoors";

    private ArrayList<Integer> getMarkedDoors() {
        return stringToArray((String) getLocalProperty(doorsProperty));
    }

    private ArrayList<Integer> addMarkedDoor(ArrayList<Integer> list, int doorNumber) {
        if (!list.contains(doorNumber)) {
            list.add(doorNumber);
        }
        return list;
    }

    private void setMarkedDoorsList(ArrayList<Integer> list){
        setLocalProperty(doorsProperty, arrayToSring(list));
    }

    private ArrayList<Integer> stringToArray(String list) {
        String[] doorStrings = list.split(",");
        ArrayList<Integer> newList = new ArrayList<>();
        if (doorStrings[0].length() != 0)
            for (String door : doorStrings) {
                newList.add(Integer.parseInt(door));
            }
        return newList;
    }

    private String arrayToSring(ArrayList<Integer> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private int getNeighborsCount() {
        return (int) getLocalProperty(neighborProperty);
    }

    private void setNeighborsCount(int count) {
        setLocalProperty(neighborProperty, count);
    }

    @Override
    public String getDescription() {
        return "Spanning Tree and Election Algorithm using LC2";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel());
        setNeighborsCount(0);
        setLocalProperty(doorsProperty, "");
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
        if (getLocalProperty("label").equals("A")) {
            for (int i = 0; i < getActiveDoors().size(); i++) {
                int numPort = getActiveDoors().get(i);
                if (getNeighborProperty(numPort, "label").equals("N")) {
                    setNeighborProperty(numPort, "label", "A");
                    setDoorState(new MarkedState(true), numPort);
                    setMarkedDoorsList(addMarkedDoor(getMarkedDoors(), numPort));
                    setNeighborsCount(getNeighborsCount() + 1);
                    setNeighborProperty(numPort, neighborProperty, (int) getNeighborProperty(i, neighborProperty) + 1);
                }
            }
            if (countActiveNeigbors() == 0) {
                setLocalProperty("label", "W");
            }
        } else if (getLocalProperty("label").equals("W")) {
            if (getNeighborsCount() > 0) {
                for (int i = 0; i < getActiveDoors().size(); i++) {
                    int numPort = getActiveDoors().get(i);
                    if (getMarkedDoors().contains(numPort))
                        if (getNeighborProperty(numPort, "label").equals("W")) {
                            if ((int) getNeighborProperty(numPort, neighborProperty) == 1) {
                                setNeighborProperty(numPort, "label", "F");
                                setNeighborsCount((int) getNeighborsCount() - 1);
                                setNeighborProperty(numPort, neighborProperty,
                                        (int) getNeighborProperty(numPort, neighborProperty) - 1);
                            }
                        }
                }
            } else if (getNeighborsCount() == 0) {
                boolean neighborFinished = true;
                for (int i = 0; i < getActiveDoors().size(); i++) {
                    int numPort = getActiveDoors().get(i);
                    if (getMarkedDoors().contains(numPort))
                        if (!getNeighborProperty(numPort, "label").equals("F")) {
                            neighborFinished = false;
                            break;
                        }
                }
                if (neighborFinished) {
                    setLocalProperty("label", "E");
                    globalTermination();
                }
            }
        } else if (getLocalProperty("label").equals("F")) {
            localTermination();
        }
        putProperty("Nombre voisins", getNeighborsCount(), SimulationConstants.PropertyStatus.DISPLAYED);
        putProperty("Liste Portes", getMarkedDoors(), SimulationConstants.PropertyStatus.DISPLAYED);
    }

    @Override
    public Object clone() {
        return new SpanningTreeAndElection();
    }
}