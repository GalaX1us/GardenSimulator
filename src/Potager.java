import java.util.Observable;
import java.util.Observer;

public class Potager {
    public int height;
    public int width;
    private Parcelle[][] tab;
    private Meteo meteo;

    public Potager(int x, int y) {
        this.height = y;
        this.width = x;
        this.tab = new Parcelle[height][width];
        this.meteo = new Meteo();
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

    public void maj(int i, int j) {
        //tab[j][i].testImg = !tab[j][i].testImg;
        //System.out.println("i = "+i+", j = "+j);
    }
}
