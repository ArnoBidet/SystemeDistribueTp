import visidia.simulation.process.algorithm.LC1_Algorithm;

/*
 * Le type de synchro indiqué est l'étoile ouverte.
 */
public class Coloration extends LC1_Algorithm {
    private String color1 = "R", color2 = "S", color3 = "L";
    String neighborColor[] = {"", ""};

    @Override
    public String getDescription() {
        return "Spanning Tree Algorithm using LC1";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", color1);
    }

    @Override
    protected void onStarCenter() {
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            neighborColor[i] = (String)getNeighborProperty(i, "label");
        }
        
    }

    @Override
    public Object clone() {
        return new SpanningTree();
    }

    private boolean isRuleOne(){
        boolean isAllSame = true;
        for(int i = 0; i < neighborColor.length; i++)
            isAllSame = isAllSame && !getLocalProperty("label").equals(neighborColor[i]);
        return isAllSame;
    }

}