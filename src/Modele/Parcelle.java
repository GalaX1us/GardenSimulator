package Modele;

import java.time.YearMonth;

public class Parcelle implements Runnable {

    public float humidite;
    public float temperature;

    private Legume legume;

    public Parcelle(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);
        this.humidite = 0.5f;
        this.temperature = -1f;
    }

    @Override
    public void run() {
        temperature = Meteo.getMeteo().temperature;
        humidite += Meteo.getMeteo().precipitation-0.5f;

        if (humidite>1) humidite=1;
        if (humidite<0) humidite=0;

        legume.calculCroissance(humidite, temperature);
    }
    
}
