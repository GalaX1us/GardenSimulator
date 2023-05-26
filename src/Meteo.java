import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Meteo implements Runnable{

    private List<List<Float>> donneesCSV;

    private int cmpt;

    private float jour;
    private float annee;

    private float temperature;
    private float ensoleillement;
    private float precipitation;
    private float humidite; 

    public Meteo(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);

        donneesCSV = new ArrayList<>();
        loadMeteo();
        cmpt = 0;
    }

    public void loadMeteo(){

        String cheminFichier = "assets/meteo_data.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                List<Float> ligneCSV = new ArrayList<>();

                String[] result = ligne.split(",");
                
                //jour
                ligneCSV.add(Float.parseFloat(result[0]));
                //jour
                ligneCSV.add(Float.parseFloat(result[1]));
                //precipitation
                ligneCSV.add((Float.parseFloat(result[5])-2.251f)/4.743f);
                //température
                ligneCSV.add((Float.parseFloat(result[6])-10.66f)/7.561f);
                //humidité
                ligneCSV.add(Float.parseFloat(result[7]));
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
        humidite = donneesCSV.get(cmpt).get(4);
        ensoleillement = 1f-precipitation;
        cmpt = cmpt + 1;
    }
    
}
