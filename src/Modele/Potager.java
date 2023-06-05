package Modele;
import java.util.Observable;
import java.util.Observer;

public class Potager {
    public static int height;
    public static int width;
    private Parcelle[][] tab;
    private Meteo meteo;
    private static String selection;
    private int argent;
    
    /**
     * Constructeur par défaut de Potager
     * @param x nombre de ligne du potager
     * @param y nombre de colonne du potager
     */
    public Potager(int x, int y) {
        this.height = y;
        this.width = x;
        this.argent = 0;

        //créé le tableau des parcelle
        this.tab = new Parcelle[height][width];

        //initialise la météo
        this.meteo = Meteo.getMeteo();
        Potager.selection = "";

        //Instancie toute les parcelles
        for(int i=0; i<height; i++) {
            for(int j = 0; j<width; j++) {
                this.tab[i][j] = new Parcelle();
            }
        }

    }

    /**
     * Ajoute un observer à la liste des observer de l'ordonnanceur
     * @param ob l'objet observeur
     */
    public void addObserver(Observer ob){
        Ordonnanceur ord = Ordonnanceur.getOrdonnanceur();
        ord.addObserver(ob);
    }

    /**
     * Renvoie la parcelle correspondant aux coordonnées
     * @param i colonne
     * @param j ligne
     * @return la parcelle
     */
    public Parcelle getParcelle(int i, int j) {
        return tab[j][i];
    }

    /**
     * Permet de changer le type de légume ou objet qu'on utilise
     * @param nom la nouvelle selection
     */
    public static void setSelection(String nom) {
        selection = nom;
    }

    /**
     * Permet de renvoyer le type de légume ou objet qu'on utilise 
     * @return la sélection
     */
    public static String getSelection() {
        return selection;
    }
    
    /**
     * Permet d'ajouter de l'argent au solde
     * @param argent le montant à ajouter
     */
    public void ajoutArgent(int argent) {
        this.argent += argent;
    }

    /**
     * Permet de renvoyer la solde d'argent
     * @return la solde d'argent
     */
    public int getArgent() {
        return this.argent;
    }
}
