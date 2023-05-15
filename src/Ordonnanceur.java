import java.util.Observable;

public class Ordonnanceur extends Observable implements Runnable{

    private Runnable[] tab;

    @Override
    public void run() {
        for(Runnable r : tab) {
            r.run();
        }
    }
    
}
