package Vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.Icon;
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
    private Label affichageArgent;

    public Vue(Potager potager) {
        super();

        this.P = potager;
        this.tabG = new Case[Potager.height][Potager.width];
        this.affichageArgent = new Label("Vous avez 0€.", JLabel.CENTER);
        affichageArgent.setFont(new Font("Arial", Font.PLAIN, 20));

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
        JComponent pan = new JPanel (new GridLayout(Potager.width, Potager.height));

        // Remplissage de la grille d'images
        for(int i = 0; i<Potager.height;i++){
            for(int j = 0; j<Potager.width;j++){
                Case casePotager = new Case("terre", false, i ,j); //TODO faire un set opaque pour faire un setBackground pour montrer le taux d'humidité par exemple
                casePotager.setPrix(Math.max(i, j)*10-10);


                // Superposition de la case avec le fond en terre
                JPanel caseTerre = new JPanel();

                BufferedImage imageBuffer = ImageIO.read(new File("assets/spriteTerre.png"));
                ImageIcon icon = new ImageIcon(imageBuffer);
                Image image = icon.getImage().getScaledInstance(82,82,Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);
                JLabel terre = new JLabel(icon);
                terre.setBorder(BorderFactory.createLineBorder(Color.black,1));

                caseTerre.setLayout(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = 0;
                caseTerre.add(casePotager, constraints);
                caseTerre.add(terre, constraints);

                int ii = i;
                int jj = j;

                // Gestion du clic de la case
                casePotager.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!casePotager.getEstDansMenu()) { // Case du potager
                            super.mouseClicked(e);
                            if(casePotager.getLocked()) { // Si la parcelle n'est pas encore débloquée, on la débloque si l'on a assez d'argent
                                if(P.getArgent() >= casePotager.getPrix()) {
                                    casePotager.unlock();
                                    P.ajoutArgent(-casePotager.getPrix());
                                }
                            }
                            else if(!Potager.getSelection().equals("")) {
                                if(!casePotager.getContientLegume() && !casePotager.getLocked()) { // On plante le légume
                                    try {
                                        casePotager.icone(Potager.getSelection());
                                        P.getParcelle(ii, jj).setLegume(Potager.getSelection());
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                    int[] coords = casePotager.getCoords();
                                    Potager.getClick(coords[0],coords[1]);
                                }
                                if(P.getParcelle(ii, jj).getLegume().isHarvestable()) { // Si le légume est à maturité on le récolte
                                    P.ajoutArgent((int) P.getParcelle(ii, jj).recolte());
                                    casePotager.recolte();
                                }
                            }
                        }
                        else { // Case du menu
                            Potager.setSelection(casePotager.getNomImage());
                            setBackground(Color.CYAN);
                        }
                    }
                });
                
                tabG[i][j] = casePotager;
                pan.add(caseTerre);
            }
        }

        // Débloquage des 4 premières cases pour commencer le jeu
        this.tabG[0][0].unlock();
        this.tabG[0][1].unlock();
        this.tabG[1][0].unlock();
        this.tabG[1][1].unlock();


        // Création du menu de choix de légume
        JComponent sideMenu = new JPanel(new GridLayout(2, 1));
        JComponent menuLegumes = new JPanel(new GridLayout(4, 2));
        String[] listeLegumes = {"salade", "champignon", "carotte", "mais", "aubergine", "oignon"};
        for(int i = 0; i<6; i++) {
            Case choixMenu = new Case(listeLegumes[i], true, -1, -1);
            menuLegumes.add(choixMenu);
        }

        Case arrosoir = new Case("arrosoir", true, -1, -1);
        Case engrais = new Case("engrais", true, -1, -1);
        menuLegumes.add(arrosoir);
        menuLegumes.add(engrais);
        sideMenu.add(menuLegumes);
        
        sideMenu.add(affichageArgent);
        
        
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
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.NORTH;
        window.add(sideMenu, c);
        
        //pack();
        add(window);
    }

    @Override
    public void update(Observable o, Object arg) {
        affichageArgent.setText("Vous avez "+P.getArgent()+"€.");
        for(int i = 0; i<Potager.height; i++) {
            for(int j = 0; j<Potager.width; j++) {
                if(P.getParcelle(i, j).getLegume()!=null) {
                    try {
                        this.tabG[i][j].scale(P.getParcelle(i, j).getLegume().croissance());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
