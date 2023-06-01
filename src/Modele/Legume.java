package Modele;
public abstract class Legume {

    //entre 0 et 1
    protected float croissance;
    protected boolean harvestable;
    protected float value;

    public Legume(){
        croissance = 0;
        harvestable = false;
    }
    
    public abstract void calculCroissance(float humidite, float temperature);

    public float getValue(){
        return this.value;

    }
}
