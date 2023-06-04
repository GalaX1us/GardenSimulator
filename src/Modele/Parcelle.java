package Modele;

import java.time.YearMonth;

import Legumes.Aubergine;
import Legumes.Carotte;
import Legumes.Champignon;
import Legumes.Mais;
import Legumes.Oignon;
import Legumes.Salade;

public class Parcelle implements Runnable {

    public float humidite;
    public float temperature;
    public boolean booster;

    private Legume legume;

    public Parcelle(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);
        this.humidite = 0.5f;
        this.temperature = -1f;
        this.booster = false;
    }

    @Override
    public void run() {
        temperature = Meteo.getMeteo().temperature;
        humidite += Meteo.getMeteo().precipitation-0.5f;

        if (humidite>1) humidite=1;
        if (humidite<0) humidite=0;


        if (this.legume!=null) legume.calculCroissance(humidite, temperature);
    }
    
    public Legume getLegume(){
        return legume;
    }

    public float recolte(){
        float v = this.legume.getValue();
        if (this.booster) v*=2;
        this.legume = null;
        this.booster = false;
        return v;
    }

    public void setLegume(String selection){
        switch (selection) {
            case "salade":
                this.legume = new Salade();
                break;
            case "champignon":
                this.legume = new Champignon();
                break;
            case "carotte":
                this.legume = new Carotte();
                break;
            case "mais":
                this.legume = new Mais();
                break;
            case "aubergine":
                this.legume = new Aubergine();
                break;
            case "oignon":
                this.legume = new Oignon();
                break;
            default:
                break;
        }
    }

    public void mettreEngrais() {
        this.booster = true;
    }
    
    public void arroser() {
        this.legume.addCroissance(0.5f);
    }
}
