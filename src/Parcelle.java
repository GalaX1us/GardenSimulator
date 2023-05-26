public class Parcelle implements Runnable {

    public float humidity;

    public Parcelle(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);
        this.humidity = 20;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
