package Modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Meteo implements Runnable{

    private List<List<Float>> donneesCSV;

    private int cmpt;

    public float jour;
    public float annee;

    public float temperature;
    public float ensoleillement;
    public float precipitation;

    private static Meteo m;

    public Meteo(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);

        donneesCSV = new ArrayList<>(); //TODO
        loadMeteo();
        cmpt = 0;
    }

    public static Meteo getMeteo(){
        if (m==null){
            m = new Meteo();
        }
        return m;
    }

    public void loadMeteo(){

        String cheminFichier = "assets/meteo_data.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                List<Float> ligneCSV = new ArrayList<>();

                String[] result = ligne.split(",");
                
                //annee
                ligneCSV.add(Float.parseFloat(result[0]));
                //jour
                ligneCSV.add(Float.parseFloat(result[1]));
                //precipitation
                ligneCSV.add((((Float.parseFloat(result[5])-2.251f)/4.743f)+1)/2);
                //température
                ligneCSV.add((((Float.parseFloat(result[6])-10.66f)/7.561f)+1)/2);

                donneesCSV.add(ligneCSV);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        annee = donneesCSV.get(cmpt).get(0);
        jour = donneesCSV.get(cmpt).get(1);
        precipitation = donneesCSV.get(cmpt).get(2);
        temperature = donneesCSV.get(cmpt).get(3);
        ensoleillement = 1f-precipitation;
        cmpt = cmpt + 1;
        if (cmpt == 3742) cmpt = 0;
    }
    
}
