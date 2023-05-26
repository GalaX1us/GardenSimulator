import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Meteo implements Runnable{

    private List<List<Float>> donneesCSV;

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
    }

    public void loadMeteo(){

        String cheminFichier = "assets/meteo_data.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                List<Float> ligneCSV = new ArrayList<>();

                String[] result = ligne.split(",");
                
                //jour
                ligneCSV.add(Float.parseFloat(result[1]));
                //precipitation
                ligneCSV.add(Float.parseFloat(result[5]));
                //température
                ligneCSV.add(Float.parseFloat(result[6]));
                //humidité
                ligneCSV.add(Float.parseFloat(result[7]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
