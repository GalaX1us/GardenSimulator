package Modele;
import java.util.Observable;
import java.util.Observer;
import java.util.Calendar;
import java.text.DateFormatSymbols;

public class Potager {
    public static int height;
    public static int width;
    private Parcelle[][] tab;
    private Meteo meteo;
    private static String selection;
    private static int argent;
    private static Calendar calendrier;
    

    public Potager(int x, int y) {
        Potager.height = y;
        Potager.width = x;
        this.argent = 0;
        this.tab = new Parcelle[height][width];
        this.meteo = Meteo.getMeteo();
        Potager.selection = "";
        for(int i=0; i<height; i++) {
            for(int j = 0; j<width; j++) {
                this.tab[i][j] = new Parcelle();
            }
        }
        calendrier = Calendar.getInstance();
        calendrier.set(2023, 0, 1);
    }

    public static void nextDay(){
        calendrier.add(Calendar.DAY_OF_YEAR, 1);
    }

    public String getMois(){
        String nomMois = new DateFormatSymbols().getMonths()[calendrier.get(Calendar.MONTH)];
        nomMois = nomMois.substring(0, 1).toUpperCase() + nomMois.substring(1); //Majuscule
        return nomMois;
    }

    public String getAnnee(){
        return String.valueOf(calendrier.get(Calendar.YEAR));
         
    }

    public String getSaison(){

        int moisCalendrier = calendrier.get(Calendar.MONTH);
        int jourCalendrier = calendrier.get(Calendar.DAY_OF_MONTH);

        String saison;
        if ((moisCalendrier == Calendar.DECEMBER && jourCalendrier >= 21) || (moisCalendrier == Calendar.JANUARY) || (moisCalendrier == Calendar.FEBRUARY) || (moisCalendrier == Calendar.MARCH && jourCalendrier < 20)) {
            saison = "Hiver";
        } else if ((moisCalendrier == Calendar.MARCH && jourCalendrier >= 20) || (moisCalendrier == Calendar.APRIL) || (moisCalendrier == Calendar.MAY) || (moisCalendrier == Calendar.JUNE && jourCalendrier < 21)) {
            saison = "Printemps";
        } else if ((moisCalendrier == Calendar.JUNE && jourCalendrier >= 21) || (moisCalendrier == Calendar.JULY) || (moisCalendrier == Calendar.AUGUST) || (moisCalendrier == Calendar.SEPTEMBER && jourCalendrier < 23)) {
            saison = "Été";
        } else {
            saison = "Automne";
        }
        
        // Retourner la saison
        return saison;
    }

    public void addObserver(Observer ob){
        Ordonnanceur ord = Ordonnanceur.getOrdonnanceur();
        ord.addObserver(ob);
    }

    public Parcelle getParcelle(int i, int j) {
        return tab[j][i];
    }

    public static void setSelection(String nom) {
        selection = nom;
    }

    public static String getSelection() {
        return selection;
    }

    public void maj(int i, int j) {
        //tab[j][i].testImg = !tab[j][i].testImg;
        //System.out.println("i = "+i+", j = "+j);
    }
    public static void getClick(int i, int j){
        
    }
    
    public static void ajoutArgent(int argent) {
        Potager.argent += argent;
    }

    public static int getArgent() {
        return Potager.argent;
    }
}
