/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

/**
 *
 * @author frederic
 */
public class _Vue extends JFrame implements Observer{

    public _Model M;
    public JComponent[][] tabG;

    public _Vue(_Model modele) {
        super();
        
        this.M = modele;
        this.tabG = new JComponent[M.size_x][M.size_y];

        build();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
        
        
    }
    
    public void build() {
        
        JMenuBar jm = new JMenuBar();
        
        JMenu m = new JMenu("Jeu");
        
        JMenuItem mi = new JMenuItem("Partie");
        
        //ItemListener i = new Item
        
        m.add(mi);
        
        jm.add(m);
    
        setJMenuBar(jm);
        
        setTitle("Ma première fenêtre");
        setSize(500, 500);
        JComponent pan = new JPanel (new GridLayout(M.size_x,M.size_y));
        Border blackline = BorderFactory.createLineBorder(Color.black,1);

        for(int i = 0; i<M.size_x;i++){
            for(int j = 0; j<M.size_y;j++){
                JComponent ptest = new _Case();
                tabG[i][j] = ptest;
                ptest.setBorder(blackline);
                pan.add(ptest);

                final int ii = i;
                final int jj = j;

                tabG[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        M.maj(ii,jj);
                    }
                });
            }
        }
        pan.setBorder(blackline);
        add(pan);
        //setContentPane(pan);
    }

    @Override
    public void update(Observable o, Object arg) {
        for(int i=0; i<M.size_x; i++) {
            for(int j=0; j<M.size_y; j++) {
                if(M.tab[i][j]) {
                    tabG[i][j].setBackground(Color.RED);
                }
                else {
                    tabG[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }


    
}


