package Legumes;

import Modele.Legume;

public class Chou extends Legume {
    public Chou(){
        super();
        this.value = 25;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        if(!this.harvestable){
            float offset = Math.max(((humidite - (0.5f-temperature)*2)+1)/200, 0f);
            croissance += offset;
        }
        if (this.croissance>=1) this.harvestable = true;
    }
}
