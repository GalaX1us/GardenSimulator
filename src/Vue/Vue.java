package Vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
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
import javax.swing.JPanel;

import Modele.Potager;

/**
 * Classe qui gère l'affichage de la fenêtre
 */
public class Vue extends JFrame implements Observer {

    /**
     * Potager qui est un modèle qui fère la partie backend de l'application
     */
    private Potager P;

    /**
     * Tableau de Case qui permet l'affichage des cases du potager
     */
    public Case[][] tabG;
    
    /**
     * Largeur de la fenêtre
     */
    private static int WIDTH = 900;

    /**
     * Hauteur de la fenêtre
     */
    private static int HEIGHT = 700;

    /**
     * Label qui permet d'afficher l'argent dont on dispose
     */
    private Label affichageArgent;

    /**
     * Label qui permet d'afficher la date à laquelle nous sommes
     */
    private Label affichageDate;

    /**
     * Label qui permet d'afficher la saison en cours
     */
    private Label affichageSaison;

    /**
     * Constructeur de la vue du jeu
     * @param potager Potager du jeu
     */
    public Vue(Potager potager) {
        super();

        this.P = potager;
        this.tabG = new Case[Potager.height][Potager.width];

        // Initialisation des Label
        Font font = new Font("Arial", Font.PLAIN, 20);
        this.affichageArgent = new Label("Argent : 0€", JLabel.CENTER);
        affichageArgent.setFont(font);

        this.affichageDate = new Label("", JLabel.CENTER);
        affichageDate.setFont(font);

        this.affichageSaison = new Label("", JLabel.CENTER);
        affichageSaison.setFont(font);

        // Construction de la fenêtre
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

    /**
     * Construit l'affichage de la fenêtre qui est affichée à l'écran
     * @throws IOException erreur lors du chargement des images
     */
    public void build() throws IOException {
        
        // Initialisation de la fenêtre
        setTitle("Garden Simulator");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocation(140, 70);

        // JPanel qui contient tous les éléments de la fenêtre
        JPanel window = new JPanel(new GridBagLayout());

        // Création de la grille du potager
        JComponent pan = new JPanel (new GridLayout(Potager.width, Potager.height));

        // Remplissage de la grille d'images
        for(int i = 0; i<Potager.height;i++){
            for(int j = 0; j<Potager.width;j++){
                Case casePotager = new Case("terre", false, i ,j);
                casePotager.setPrix((i+1)*(j+1)*5);


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
                    public void mousePressed(MouseEvent e) {
                        super.mouseClicked(e);
                        if(casePotager.getLocked()) { // Si la parcelle n'est pas encore débloquée, on la débloque si l'on a assez d'argent
                            if(Potager.getArgent() >= casePotager.getPrix()) {
                                casePotager.unlock();
                                Potager.ajoutArgent(-casePotager.getPrix());
                            }
                        }
                        else if(Potager.getSelection().equals("arrosoir") && 
                                casePotager.getContientLegume() && 
                                !P.getParcelle(ii, jj).getLegume().isHarvestable()){
                            P.getParcelle(ii, jj).arroser();
                        }
                        else if(Potager.getSelection().equals("engrais") && 
                                casePotager.getContientLegume() && 
                                !P.getParcelle(ii, jj).getLegume().isHarvestable()){
                            P.getParcelle(ii, jj).mettreEngrais();
                        }
                        else if(!Potager.getSelection().equals("")) {
                            if(!casePotager.getContientLegume() && !casePotager.getLocked() &&
                            !Potager.getSelection().equals("arrosoir") &&
                            !Potager.getSelection().equals("engrais")) { // On plante le légume
                                try {
                                    casePotager.icone(Potager.getSelection());
                                    P.getParcelle(ii, jj).setLegume(Potager.getSelection());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            if(casePotager.getContientLegume() && P.getParcelle(ii, jj).getLegume().isHarvestable()) { // Si le légume est à maturité on le récolte
                                Potager.ajoutArgent((int) P.getParcelle(ii, jj).recolte());
                                casePotager.recolte();
                            }
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
        JComponent sideMenu = new JPanel(new GridBagLayout());
        JComponent menuLegumes = new JPanel(new GridLayout(4, 2));
        String[] listeLegumes = {"salade", "chou", "carotte", "poireau", "aubergine", "citrouille"};
        for(int i = 0; i<6; i++) {
            Case choixMenu = new Case(listeLegumes[i], true, -1, -1);
            if(i==0) choixMenu.unlock();
            else choixMenu.setPrix(100*i);

            menuLegumes.add(choixMenu);
        }

        Case arrosoir = new Case("arrosoir", true, -1, -1);
        Case engrais = new Case("engrais", true, -1, -1);
        menuLegumes.add(arrosoir);
        menuLegumes.add(engrais);

        GridBagConstraints cMenu = new GridBagConstraints();
        cMenu.fill = GridBagConstraints.HORIZONTAL;

        // Insertion des éléments dans le menu menu
        cMenu.gridy = 0;
        sideMenu.add(menuLegumes, cMenu);
        
        cMenu.insets = new Insets(15, 0, 0, 0);
        cMenu.gridy = 1;
        sideMenu.add(affichageArgent, cMenu);
        cMenu.gridy = 2;
        sideMenu.add(affichageDate, cMenu);
        cMenu.gridy = 3;
        sideMenu.add(affichageSaison, cMenu);

        
        // Insertion de la grille et du menu dans la fenêtre
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
        
        add(window);
    }

    @Override
    /**
     * Met à jour l'affichage des Label et la croissance des légumes
     */
    public void update(Observable o, Object arg) {
        affichageArgent.setText("Argent : "+Potager.getArgent()+"€");
        affichageDate.setText(P.getMois()+" "+P.getAnnee());
        affichageSaison.setText(P.getSaison());
        for(int i = 0; i<Potager.height; i++) {
            for(int j = 0; j<Potager.width; j++) {
                if(P.getParcelle(i, j).getLegume()!=null) {
                    try {
                        this.tabG[i][j].scale(P.getParcelle(i, j).getLegume().getCroissance());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
