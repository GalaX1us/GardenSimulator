package Modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        donneesCSV = new ArrayList<>();
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
        Potager.nextDay();
        precipitation = donneesCSV.get(cmpt).get(0);
        temperature = donneesCSV.get(cmpt).get(1);
        ensoleillement = 1f-precipitation;
        cmpt = cmpt + 1;
    }
    
}
