/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.SwingUtilities;

/**
 *
 * @author frederic
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crÃ©e une nouvelle instance de notre JDialog
				Vue fenetre = new Vue();
				fenetre.setVisible(true);//On la rend visible
			}
		});*/

        Model M = new Model(10, 10);
        Vue V = new Vue(M);
        V.setVisible(true);
        M.addObserver(V);
        new Thread(M).start();
    }

}
