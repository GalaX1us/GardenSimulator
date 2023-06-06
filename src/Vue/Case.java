package Vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import Modele.Ordonnanceur;
import Modele.Potager;

/**
 * Classe qui gère l'affichage graphique des parcelles du potager
 */
public class Case extends JLabel implements Runnable{

    private boolean estDansMenu;
    private String nomImage;
    private int scale;
    private Border bordureRecolte;
    private boolean contientLegume;
    private boolean locked;
    private int prix;
    private boolean maturite;
    private int i;
    private int j;

    public String getNomImage() {
        return this.nomImage;
    }

    public boolean getEstDansMenu() {
        return this.estDansMenu;
    }

    public int[] getCoords() {
        int[] coords = {this.i, this.j};
        return coords;
    }

    public boolean getMaturite() {
        return this.maturite;
    }

    public boolean getContientLegume() {
        return this.contientLegume;
    }

    public boolean getLocked() {
        return this.locked;
    }

    public int getPrix() {
        return prix;
    }

    /**
     * Applique un prix pour débloquer la case, et l'affiche
     * @param prix
     */
    public void setPrix(int prix) {
        this.prix = prix;
        this.setText(Integer.toString(this.prix)+"€");
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setFont(new Font("Arial", Font.PLAIN, 25));
        if(estDansMenu) this.setForeground(Color.black);
        else this.setForeground(Color.white);
    }

    /**
     * Constructeur de Case
     * @param nomImage Nom de l'image que l'on applique à la case
     * @param estDansMenu Booléen indiquant si la case que l'on crée se trouve dans le menu
     * @param i Indice de la case dans le tableau de la vue
     * @param j Indice de la case dans le tableau de la vue
     * @throws IOException Erreur lors du chargement de l'image
     */
    public Case(String nomImage, boolean estDansMenu, int i, int j) throws IOException {
        super();
        this.estDansMenu = estDansMenu;
        this.nomImage = nomImage;
        this.scale = 1;
        this.bordureRecolte = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GREEN);
        this.maturite = false;
        this.contientLegume = false;
        this.locked = true;
        this.prix = 0;
        this.i = i;
        this.j = j;


        Ordonnanceur.getOrdonnanceur().addRunnable(this);

        // Gestion du clic du menu
        if(estDansMenu) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(getLocked()) { // Si le légume n'est pas encore débloqué, on le débloque si l'on a assez d'argent
                        if(Potager.getArgent() >= getPrix()) {
                            unlock();
                            Potager.ajoutArgent(-getPrix());
                        }
                    }
                    else {
                        Potager.setSelection(getNomImage());
                        setBackground(Color.CYAN);
                    }
                }
            });
        }

        icone(nomImage);

        if(estDansMenu) {
            Color grisClair = new Color(238, 238, 238);
            Border bordureExterne = BorderFactory.createLineBorder(grisClair, 3);
            Border bordureInterne = BorderFactory.createLineBorder(Color.BLACK, 2);
            setBorder(BorderFactory.createCompoundBorder(bordureExterne, bordureInterne));
            setOpaque(true);
        }
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    /**
     * Gère dynamiquement les bordure et fonds des cases
     */
    public void run() {
        if(estDansMenu) {
            if(!(Potager.getSelection().equals(nomImage))) {
                setBackground(Color.white);
            }
        }
        else if(!nomImage.equals("terre")) { // Si la case contient un légume on le fait pousser
            if(this.scale >= 59) { // Le légume a atteint la taille max
                this.setBorder(bordureRecolte);
                this.maturite = true;
            }
        }
    }

    /**
     * Remplace l'icone de la case par l'image dont le nom est en paramètre
     * @param name Nom de l'image que l'on veut afficher
     * @throws IOException Erreur lors du chargement de l'image
     */
    public void icone(String name) throws IOException {
        this.nomImage = name;
        BufferedImage imageBuffer = null;

        if(name.equals("terre") || name.equals("")) {
            this.nomImage = "terre"; // dans le cas où le nom est vide
            imageBuffer = this.locked ? ImageIO.read(new File("assets/spriteTerreLocked.png")) : ImageIO.read(new File("assets/spriteTerre.png"));
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(82,82,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else if(name.equals("arrosoir")) {
            imageBuffer = ImageIO.read(new File("assets/arrosoir.png"));
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else if(name.equals("engrais")) { 
            imageBuffer = ImageIO.read(new File("assets/engrais.png"));
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else { // Légume
            this.contientLegume = true;
            this.scale = 1;
            BufferedImage legume = null;
            Image image = null;
            if(!this.estDansMenu) {
                legume = ImageIO.read(new File("assets/Legumes/"+name+"1.png")); // étape 1 de croissance du légume
                ImageIcon icon = new ImageIcon(legume);
                image = icon.getImage().getScaledInstance(79,79,Image.SCALE_SMOOTH);
            }
            else {
                legume = this.locked ? ImageIO.read(new File("assets/Legumes/"+name+"Locked.png")) : ImageIO.read(new File("assets/Legumes/"+name+".png"));
                ImageIcon icon = new ImageIcon(legume);
                image = icon.getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
            }
            ImageIcon iconeLegume = new ImageIcon(image);
            this.setIcon(iconeLegume);
        }

        
    }

    /**
     * Permet de changer l'image du plant en fonction de son niveau de croissance
     * @param croissance variable entre 0 et 1 qui indique le niveau de pousse du plant
     * @throws IOException Erreur lors du chargement de l'image
     */
    public void scale(float croissance) throws IOException {
        int stage = stageCroissance(croissance);
        BufferedImage legume = ImageIO.read(new File("assets/Legumes/"+this.nomImage+stage+".png"));
        ImageIcon icon = new ImageIcon(legume);
        Image image = null;
        if(stage==4) image = icon.getImage().getScaledInstance(78,78,Image.SCALE_SMOOTH);
        else image = icon.getImage().getScaledInstance(79,79,Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        this.setIcon(icon);

        scale = (int) (croissance*58f) + 1;
    }

    /**
     * Récolte un légume dans la parcelle, et remet l'image de terre
     */
    public void recolte() {
        setBorder(BorderFactory.createLineBorder(Color.black,0));
        this.contientLegume = false;
        this.maturite = false;
        try {
            icone("terre");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Débloque une case du potager
     */
    public void unlock() {
        this.locked = false;
        try {
            if(this.estDansMenu) icone(this.nomImage);
            else icone("terre");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setText("");
    }

    /**
     * Indique le stage de croissance auquel en est le plant
     * @param croissance variable entre 0 et 1 qui indique le niveau de pousse du plant
     * @return int qui correspond au stage de croissance du plant
     */
    private int stageCroissance(float croissance) {
        if(croissance < 0.33f) return 1;
        if(croissance < 0.66f) return 2;
        if(croissance < 0.99f) return 3;
        return 4;
    }
    
}
