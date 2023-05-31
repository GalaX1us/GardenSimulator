package Modele;
import java.util.Observable;
import java.util.Observer;

public class Potager {
    public int height;
    public int width;
    private Parcelle[][] tab;
    private Meteo meteo;
    private static String selection;
    private int argent;
    

    public Potager(int x, int y) {
        this.height = y;
        this.width = x;
        this.argent = 0;
        this.tab = new Parcelle[height][width];
        this.meteo = Meteo.getMeteo();
        Potager.selection = "";
        for(int i=0; i<height; i++) {
            for(int j = 0; j<width; j++) {
                this.tab[i][j] = new Parcelle();
            }
        }
        /*for (Parcelle[] pt : this.tab){
            for (Parcelle p : pt){
                p = new Parcelle();
            }
        }*/

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
    
    public void ajoutArgent(int argent) {
        this.argent += argent;
    }

    public int getArgent() {
        return this.argent;
    }
}
