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
    
    /**
     * Calcule la croissance du legume
     * @param humidite
     * @param temperature
     */
    public abstract void calculCroissance(float humidite, float temperature);

    /**
     * Revoie la valeur du légume à la revente
     * @return la valeur du légume
     */
    public float getValue(){
        return this.value;

    }

    /**
     * Permet de savoir si le légume est récoltable ou non
     * @return True si il l'est et False sinon
     */
    public boolean isHarvestable(){
        return this.harvestable;
    }
    
    /**
     * Renvoie l'avancé de la croissance du légume
     * @return la valeur de sa croissance
     */
    public float getCroissance(){
        return this.croissance;
    }

    public void addCroissance(float value){
        this.croissance += value;
        if( this.croissance > 1){
            this.croissance = 1;
        }
    }
}
