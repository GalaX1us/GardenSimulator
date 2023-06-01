package Legumes;

import Modele.Legume;

public class Oignon extends Legume {
    public Oignon(){
        super();
        this.value = 10;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        if(this.croissance<1){
            croissance += ((humidite - (0.5f-temperature)*2)+1)/50;
        }
        if (this.croissance>1) this.croissance = 1;
    }
    
}
