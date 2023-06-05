package Legumes;

import Modele.Legume;

public class Carotte extends Legume {
    public Carotte(){
        super();
        this.value = 15;
    }

    @Override
    public void calculCroissance(float humidite, float temperature) {
        if(!this.harvestable){
            float offset = Math.max(((humidite - Math.abs(0.8f-temperature)*1.5f)+1)/120, 0f);
            croissance += offset;
        }
        if (this.croissance>=1) this.harvestable = true;
    }
}
