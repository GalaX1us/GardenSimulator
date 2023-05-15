import java.util.Observable;
import java.util.Observer;

public class Potager {
    public int height;
    public int width;
    public Parcelle[][] tab;
    


    public Potager(int x, int y) {
        this.height = y;
        this.width = x;
        this.tab = new Parcelle[height][width];

        for (Parcelle[] pt : this.tab){
            for (Parcelle p : pt){
                p = new Parcelle();
            }
        }

    }

    public void addObserver(Observer ob){
        Ordonnanceur ord = Ordonnanceur.getOrdonnanceur();
        ord.addObserver(ob);
    }
}
