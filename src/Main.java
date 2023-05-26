/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.SwingUtilities;

import Modele.Ordonnanceur;
import Modele.Potager;
import Vue.Vue;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Potager P = new Potager(8, 8);
        Vue V = new Vue(P);
        V.setVisible(true);
        P.addObserver(V);
        new Thread(Ordonnanceur.getOrdonnanceur()).start();
    }

}
