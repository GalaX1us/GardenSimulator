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

    /**
     * Constructeur apr defaut de Météo
     */
    public Meteo(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);

        donneesCSV = new ArrayList<>();
        loadMeteo();
        cmpt = 0;
    }

    /**
     * Permet de renvoyer l'instance de la météo (en la créant si elle n'existe pas)
     * @return l'instance de météo
     */
    public static Meteo getMeteo(){
        if (m==null){
            m = new Meteo();
        }
        return m;
    }

    /**
     * Permet de charger la météo a partir d'un fichier csv
     */
    public void loadMeteo(){

        String cheminFichier = "assets/meteo_data.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            // lis chaque ligne
            while ((ligne = br.readLine()) != null) {
                List<Float> ligneCSV = new ArrayList<>();

                String[] result = ligne.split(",");
                
                // parse les résultats et normalisation des valeur pour qu'elles soient entre 0 et 1

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
    /**
     * Méthode appelée à chaque execution du thread
     * Elle permet de mettre a jour les données météo en changeant de jour
     */
    public void run() {
        Potager.nextDay();
        precipitation = donneesCSV.get(cmpt).get(0);
        temperature = donneesCSV.get(cmpt).get(1);
        ensoleillement = 1f-precipitation;
        cmpt = cmpt + 1;

        //permet de cycler pour éviter toute erreur
        if (cmpt == 3742) cmpt = 0;
    }
    
}
