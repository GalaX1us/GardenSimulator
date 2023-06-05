package Modele;
import java.util.ArrayList;
import java.util.Observable;

public class Ordonnanceur extends Observable implements Runnable{

    private ArrayList<Runnable> listeRunnable;
    private static Ordonnanceur o;

    /**
     * Constructeur par défaut de Ordonnanceur
     */
    private Ordonnanceur() {
        super();

        this.listeRunnable = new ArrayList<Runnable>();
    }

    /**
     * Renvoie l'instance de l'ordonnanceur en le créant si il n'existe pas
     * @return l'instance de l'ordonnanceur
     */
    public static Ordonnanceur getOrdonnanceur() {
        if(o==null) {
            o = new Ordonnanceur();
        }
        return o;
    }

    /**
     * Ajoute un objet runable à la liste des runnable de l'ordonnanceur
     * @param r le runnable à ajouter
     */
    public void addRunnable(Runnable r) {
        listeRunnable.add(r);
    }

    /**
     * Méthode appelé lors de l'execution du thread
     * Elle execute à tour de rôle tous les runnable enregistrés par l'ordonnanceur
     */
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
