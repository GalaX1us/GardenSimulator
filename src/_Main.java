/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.SwingUtilities;

/**
 *
 * @author frederic
 */
public class _Main {

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

        _Model M = new _Model(10, 10);
        _Vue V = new _Vue(M);
        V.setVisible(true);
        M.addObserver(V);
        new Thread(M).start();
    }

}
