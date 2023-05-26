package Vue;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Ordonnanceur;

public class Case extends JLabel implements Runnable{

    public Case() {
        super();

        Ordonnanceur.getOrdonnanceur().addRunnable(this);

        addMouseListener(new MouseAdapter() {
        
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Ca cliquece");
                super.mouseClicked(e);
                try {
                    icone("salade");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
    
        setOpaque(true);
    }

    public Case(String nomImage) throws IOException {
        super();

        Ordonnanceur.getOrdonnanceur().addRunnable(this);

        addMouseListener(new MouseAdapter() {
        
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Ca cliquece");
                super.mouseClicked(e);
                try {
                    icone("salade");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        
        icone(nomImage);
        setOpaque(true);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    // Renvoie les coordonnées de l'image dont le nom est en paramètre
    private int[] getCoordsImage(String name) {
        int[] coordsLegume = new int[4];
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
    private void icone(String name) throws IOException {
        BufferedImage imageBuffer;

        if(name.equals("terre")) {
            imageBuffer = ImageIO.read(new File("assets/Terre.png"));
            ImageIcon icon = new ImageIcon(imageBuffer);
            Image image = icon.getImage().getScaledInstance(82,82,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image, icon.getDescription());
            this.setIcon(icon);
        }
        else {
            imageBuffer = ImageIO.read(new File("assets/data.png")); // chargement de l'image globale
            int[] coords = getCoordsImage(name);
            BufferedImage legume = imageBuffer.getSubimage(coords[0], coords[1], coords[2], coords[3]); // image du légume
            ImageIcon icon = new ImageIcon(legume);
            Image image = icon.getImage().getScaledInstance(icon.getIconWidth() *10/17,icon.getIconHeight() *10/17,Image.SCALE_SMOOTH);
            icon = new ImageIcon(image, icon.getDescription());
            this.setIcon(icon);
        }

        
    }
    
}
