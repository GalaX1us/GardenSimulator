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

    public boolean isHarvestable(){
        return this.harvestable;
    }
    
    public float croissance(){
        return this.croissance;
    }

    public void addCroissance(float value){
        this.croissance = value;
        if( this.croissance > 1){
            this.croissance = 1;
        }
    }
}
