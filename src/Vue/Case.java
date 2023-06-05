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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.text.StyledEditorKit.BoldAction;

import Modele.Ordonnanceur;
import Modele.Potager;

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

    public void setPrix(int prix) {
        this.prix = prix;
        this.setText(Integer.toString(this.prix)+"€");
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setFont(new Font("Arial", Font.PLAIN, 25));
        if(estDansMenu) this.setForeground(Color.black);
        else this.setForeground(Color.white);
    }

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

    // Renvoie les coordonnées de l'image dont le nom est en paramètre
    private int[] getCoordsImage(String name) {
        int[] coordsLegume = new int[4]; // x, y, width, height
        coordsLegume[2] = 140;
        coordsLegume[3] = 140;
        switch(name) {
            case "salade":
                coordsLegume[0] = 0;
                coordsLegume[1] = 0;
                break;
            case "champignon":
                coordsLegume[0] = 390;
                coordsLegume[1] = 1;
                break;
            case "carotte":
                coordsLegume[0] = 390;
                coordsLegume[1] = 392;
                break;
            case "mais":
                coordsLegume[0] = 1954;
                coordsLegume[1] = 390;
                break;
            case "aubergine":
                coordsLegume[0] = 777;
                coordsLegume[1] = 392;
                break;
            case "oignon":
                coordsLegume[0] = 2732;
                coordsLegume[1] = 391;
                break;
            default: // salade
                coordsLegume[0] = 0;
                coordsLegume[1] = 0;
                break;
        }
        return coordsLegume;
    }

    // Remplace l'icone de la case par l'image dont le nom est en paramètre
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
        else {
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
                //int[] coords = getCoordsImage(name);
                //legume = imageBuffer.getSubimage(coords[0], coords[1], coords[2], coords[3]); // image du légume
                ImageIcon icon = new ImageIcon(legume);
                image = icon.getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
            }
            ImageIcon iconeLegume = new ImageIcon(image);
            this.setIcon(iconeLegume);
        }

        
    }

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

    private int stageCroissance(float croissance) {
        if(croissance < 0.33f) return 1;
        if(croissance < 0.66f) return 2;
        if(croissance < 0.99f) return 3;
        return 4;
    }
    
}
