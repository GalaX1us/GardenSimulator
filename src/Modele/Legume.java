package Modele;
public abstract class Legume {

    //entre 0 et 1
    protected float croissance;

    public Legume(){
        croissance = 0;
    }
    
    public abstract void calculCroissance(float humidite, float temperature);
}
