package Legumes;

import Modele.Legume;

public class Poireau extends Legume {
    public Poireau(){
        super();
        this.value = 30;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        if(!this.harvestable){
            float offset = Math.max(((humidite - Math.abs(0.2f-temperature))+1)/200, 0f);
            croissance += offset;
        }
        if (this.croissance>=1) this.harvestable = true;
    }
    
}
