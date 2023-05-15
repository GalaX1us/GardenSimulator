import java.util.Observable;

public class Ordonnanceur extends Observable implements Runnable{

    private Runnable[] tab;
    private static Ordonnanceur o;

    public static Ordonnanceur getOrdonnanceur() {
        if(o==null) {
            o = new Ordonnanceur();
        }
        return o;
    }

    @Override
    public void run() {
        while(true) {
            for(Runnable r : tab) {
                r.run();
            }
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
