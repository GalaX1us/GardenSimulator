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
        Border blackline = BorderFactory.createLineBorder(Color.black,1);

        // Création de l'image





        // BufferedImage imageBuffer = ImageIO.read(new File("assets/Terre.png")); // chargement de l'image globale

        // //BufferedImage salade = image.getSubimage(x, y, w, h); // image du légume le légume (x, y : coin supérieur gauche, w, h : largeur et hauteur)

        // //ImageIcon iconeSalade = imageBuffer.getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH); // icône redimentionnée
        // ImageIcon icon = new ImageIcon(imageBuffer);
        // Image image = icon.getImage().getScaledInstance(82, //icon.getIconWidth()*3,
        //                                                 82, //icon.getIconHeight()*2,
        //                                                 Image.SCALE_SMOOTH);
        // icon = new ImageIcon(image, icon.getDescription());
        // //ptest.setIcon(icon);



        // Remplissage de la grille d'images
        for(int i = 0; i<P.height;i++){
            for(int j = 0; j<P.width;j++){
                Case ptest = new Case("terre"); //TODO faire un set opaque pour faire un setBackground pour montrer le taux d'humidité par exemple
                //ptest.setIcon(icon);
                ptest.setBorder(blackline);
                tabG[i][j] = ptest;
                pan.add(ptest);
            }
        }
        pan.setBorder(blackline);

        JComponent sideMenu = new JPanel(new GridLayout(4, 2));
        for(int i = 0; i<6; i++) {
            Case choix1 = new Case("terre");
            //choix1.setIcon(icon);
            choix1.setBorder(blackline);
            sideMenu.add(choix1);
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
        c.insets = new Insets(0, 32, 0, 0);
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
