package Legumes;

import Modele.Legume;

public class Salade extends Legume {

    public Salade(){
        super();
        this.value = 10;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        croissance += ((humidite - (0.5f-temperature)*2)+1)/50;
    }
    
}
