package Modele;
import java.util.ArrayList;
import java.util.Observable;

public class Ordonnanceur extends Observable implements Runnable{

    private ArrayList<Runnable> listeRunnable;
    private static Ordonnanceur o;

    private Ordonnanceur() {
        super();

        this.listeRunnable = new ArrayList<Runnable>();
    }

    public static Ordonnanceur getOrdonnanceur() {
        if(o==null) {
            o = new Ordonnanceur();
        }
        return o;
    }

    public void addRunnable(Runnable r) {
        listeRunnable.add(r);
    }

    @Override
    public void run() {
        while(true) {
            for(Runnable r : listeRunnable) {
                r.run();
            }
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
