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
    }

    public void addObserver(Observer ob){
        Ordonnanceur ord = Ordonnanceur.getOrdonnanceur();
        ord.addObserver(ob);
    }
}
