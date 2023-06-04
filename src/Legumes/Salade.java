package Legumes;

import Modele.Legume;

public class Salade extends Legume {

    public Salade(){
        super();
        this.value = 10;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        if(!this.harvestable){
            float offset = Math.max(((humidite - Math.abs(0.8f-temperature)*1.5f)+1)/200, 0f);
            croissance += offset;
        }
        if (this.croissance>=1) this.harvestable = true;
    }
}
