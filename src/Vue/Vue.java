package Vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import Modele.Potager;

public class Vue extends JFrame implements Observer {

    private Potager P;
    public Case[][] tabG;
    private static int WIDTH = 900;
    private static int HEIGHT = 700;

    public Vue(Potager potager) {
        super();

        this.P = potager;
        this.tabG = new Case[10][10];

        try {
            build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });

    }

    public void build() throws IOException {
        
        // Initialisation de la fenêtre
        setTitle("Garden Simulator");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocation(140, 70);

        JPanel window = new JPanel(new GridBagLayout());

        // Création de la grille du potager
        JComponent pan = new JPanel (new GridLayout(P.width, P.height));

        // Remplissage de la grille d'images
        for(int i = 0; i<P.height;i++){
            for(int j = 0; j<P.width;j++){
                Case casePotager = new Case("terre", false, i ,j); //TODO faire un set opaque pour faire un setBackground pour montrer le taux d'humidité par exemple
                
                casePotager.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!casePotager.getEstDansMenu()) {
                            super.mouseClicked(e);
                            try {
                                casePotager.icone(Potager.getSelection());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            int[] coords = casePotager.getCoords();
                            Potager.getClick(coords[0],coords[1]);
                        }
                        else {
                            Potager.setSelection(casePotager.getNomImage());
                            setBackground(Color.CYAN);
                        }
                    }
                });
                
                tabG[i][j] = casePotager;
                pan.add(casePotager);
            }
        }

        // Création du menu de choix de légume
        JComponent sideMenu = new JPanel(new GridLayout(4, 2));
        String[] listeLegumes = {"salade", "champignon", "carotte", "mais", "aubergine", "oignon"};
        for(int i = 0; i<6; i++) {
            Case choixMenu = new Case(listeLegumes[i], true, -1, -1);
            sideMenu.add(choixMenu);
        }
        
        //sideMenu.add(new Label("test"));
        
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        window.add(pan, c);
        
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(30, 23, 0, 22);
        c.anchor = GridBagConstraints.NORTH;
        window.add(sideMenu, c);
        
        //pack();
        add(window);
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("je suis dans la vue");
        // for (int i = 0; i < P.height; i++) {
        //     for (int j = 0; j < P.width; j++) {
        //         if (P.getParcelle(j, i).testImg) {
        //             tabG[i][j].setBackground(new Color(0, 200, 0, 1));
        //         } else {
        //             tabG[i][j].setBackground(new Color(200, 0, 0, 1));
        //         }
        //     }
        // }
    }

}
