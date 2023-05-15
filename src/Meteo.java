public class Meteo implements Runnable{

    public Meteo(){
        super();
        Ordonnanceur o = Ordonnanceur.getOrdonnanceur();
        o.addRunnable(this);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
