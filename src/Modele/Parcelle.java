package Modele;

import Legumes.Aubergine;
import Legumes.Carotte;
import Legumes.Chou;
import Legumes.Citrouille;
import Legumes.Poireau;
import Legumes.Salade;

public class Parcelle implements Runnable {

    public float humidite;
    public float temperature;
    public boolean booster;

    private Legume legume;

    /**
     * Constructeur par défaut de Parcelle
     */
    public Parcelle(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);
        this.humidite = 0.5f;
        this.temperature = -1f;
        this.booster = false;
    }

    @Override
    /**
     * Méthode appelée à chaque éxecution du thread
     * Elle met a jour les caractéristiques de la parcelle et fait pousser le légume
     */
    public void run() {
        temperature = Meteo.getMeteo().temperature;
        humidite += Meteo.getMeteo().precipitation-0.5f;

        if (humidite>1) humidite=1;
        if (humidite<0) humidite=0;


        if (this.legume!=null) legume.calculCroissance(humidite, temperature);
    }
    
    /**
     * Renvoie le légume qui est planté sur la parcelle
     * @return le légume
     */
    public Legume getLegume(){
        return legume;
    }

    /**
     * Permet de récolter le légume qui est planté et de libérer la parcelle
     * @return la valeur du légume récolté
     */
    public float recolte(){
        float v = this.legume.getValue();
        if (this.booster) v*=2;
        this.legume = null;
        this.booster = false;
        return v;
    }

    /**
     * Permet de planter un légume dans la parcelle
     * @param selection le nom du légume qui doit être planté
     */
    public void setLegume(String selection){
        switch (selection) {
            case "salade":
                this.legume = new Salade();
                break;
            case "chou":
                this.legume = new Chou();
                break;
            case "carotte":
                this.legume = new Carotte();
                break;
            case "citrouille":
                this.legume = new Citrouille();
                break;
            case "aubergine":
                this.legume = new Aubergine();
                break;
            case "poireau":
                this.legume = new Poireau();
                break;
            default:
                break;
        }
    }

    public void mettreEngrais() {
        this.booster = true;
    }
    
    public void arroser() {
        this.legume.addCroissance(0.3f);
    }
}
