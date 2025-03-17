import visidia.simulation.process.algorithm.LC1_Algorithm;

/*
 * Le type de synchro indiqué est l'étoile ouverte.
 */
public class Coloration extends LC1_Algorithm {
    String currentColor = "";
    private String color1 = "R", color2 = "S", color3 = "L";
    String neighborColor[] = { "", "" };

    @Override
    public String getDescription() {
        return "Coloration Tree Algorithm using LC1";
    }

    @Override
    protected void beforeStart() {
        currentColor = color1;
        setLocalProperty("label", currentColor);
    }

    @Override
    protected void onStarCenter() {
        currentColor = (String)getLocalProperty("label");
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            neighborColor[i] = (String)getNeighborProperty(numPort, "label");
        }
        if(isRuleOne())
            if(currentColor.equals(color1))
                setLocalProperty("label", color2);
            else if(currentColor.equals(color2))
                setLocalProperty("label", color3);
            else
                setLocalProperty("label", color1);
        else if(isRuleTwo()){
            String newColor = color2;
            if((neighborColor[0].equals(color1) &&  neighborColor[1].equals(color2)) || (neighborColor[1].equals(color1) &&  neighborColor[0].equals(color2)))
                newColor = color3;
            else if((neighborColor[0].equals(color2) &&  neighborColor[1].equals(color3)) || (neighborColor[1].equals(color2) &&  neighborColor[0].equals(color3)))
                newColor = color1;
            setLocalProperty("label", newColor);
        }
        
    }

    @Override
    public Object clone() {
        return new Coloration();
    }

    private boolean isRuleOne() {
        String currentColor = (String) getLocalProperty("label");
        return currentColor.equals(neighborColor[0]) && currentColor.equals(neighborColor[1]);
    }

    private boolean isRuleTwo(){
        String currentColor = (String)getLocalProperty("label");
        return currentColor.equals(neighborColor[0]) || currentColor.equals(neighborColor[1]);
    }

}