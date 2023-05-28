package Vue;
import java.awt.Color;
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
    private BufferedImage imageBuffer;
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

    public Case(String nomImage, boolean estDansMenu, int i, int j) throws IOException {
        super();
        this.estDansMenu = estDansMenu;
        this.nomImage = nomImage;
        this.scale = 1;
        this.bordureRecolte = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GREEN);
        this.imageBuffer = null;
        this.maturite = false;
        this.i = i;
        this.j = j;

        Ordonnanceur.getOrdonnanceur().addRunnable(this);

        if(estDansMenu) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Potager.setSelection(nomImage);
                    setBackground(Color.CYAN);
                }
            });
        }
        
        icone(nomImage);
        if(estDansMenu) {
            Color grisClair = new Color(238, 238, 238);
            Border bordureExterne = BorderFactory.createLineBorder(grisClair, 4);
            Border bordureInterne = BorderFactory.createLineBorder(Color.BLACK, 2);
            setBorder(BorderFactory.createCompoundBorder(bordureExterne, bordureInterne));
        }
        else {
            setBorder(BorderFactory.createLineBorder(Color.black,1));
        }
        setOpaque(true);
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
            try {
                scale();
            } catch (IOException e) {
                e.printStackTrace();
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
        BufferedImage imageBuffer;

        if(name.equals("terre") || name.equals("")) {
            this.nomImage = "terre"; // dans le cas où le nom est vide
            imageBuffer = ImageIO.read(new File("assets/Terre.png"));
            this.imageBuffer = imageBuffer;
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(82,82,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else if(name.equals("arrosoir")) {
            imageBuffer = ImageIO.read(new File("assets/arrosoir.png"));
            this.imageBuffer = imageBuffer;
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else if(name.equals("engrais")) {
            imageBuffer = ImageIO.read(new File("assets/engrais.png"));
            this.imageBuffer = imageBuffer;
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }
        else {
            imageBuffer = ImageIO.read(new File("assets/data.png")); // chargement de l'image globale
            int[] coords = getCoordsImage(name);
            BufferedImage legume = imageBuffer.getSubimage(coords[0], coords[1], coords[2], coords[3]); // image du légume
            this.imageBuffer = legume;
            ImageIcon icon = new ImageIcon(legume);
            Image image = icon.getImage().getScaledInstance(79,79,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        }

        
    }

    private void scale() throws IOException {
        if(this.scale >= 59) { // Le légume a atteint la taille max
            this.setBorder(bordureRecolte);
            return;
        }
        ImageIcon icon = new ImageIcon(this.imageBuffer);
        Image image = icon.getImage().getScaledInstance(20+this.scale,20+this.scale,Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        this.setIcon(icon);

        //scale += scale<59?1:0; //augmente la scale si inférieur à 72
    }
    
}
