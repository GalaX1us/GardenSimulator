package Modele;
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
        humidite = 0.5f*(Meteo.getMeteo().precipitation - Meteo.getMeteo().ensoleillement);

        if (humidite>1) humidite=1;
        if (humidite<0) humidite=0;

        legume.calculCroissance();
    }
    
}
