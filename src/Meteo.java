public class Meteo implements Runnable{

    public Meteo(){
        super();
        // Ajout de la météo à l'ordonnanceur
        Ordonnanceur.getOrdonnanceur().addRunnable(this);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
