import javax.swing.JComponent;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
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

public class Vue extends JFrame implements Observer {

    private Potager P;
    public JComponent[][] tabG;

    public Vue(Potager potager) {
        super();

        this.P = potager;
        this.tabG = new JComponent[10][10];

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
        
        JMenuBar jm = new JMenuBar();
        
        JMenu m = new JMenu("Jeu");
        
        JMenuItem mi = new JMenuItem("Partie");
        
        //ItemListener i = new Item
        
        m.add(mi);
        
        jm.add(m);
    
        setJMenuBar(jm);
        
        setTitle("Ma première fenêtre");
        setSize(500, 500);
        JComponent pan = new JPanel (new GridLayout(P.width, P.height));
        Border blackline = BorderFactory.createLineBorder(Color.black,1);

        for(int i = 0; i<P.height;i++){
            for(int j = 0; j<P.width;j++){
                JLabel ptest = new JLabel();
                tabG[i][j] = ptest;
                ptest.setBorder(blackline);

                BufferedImage imageBuffer = ImageIO.read(new File("assets/Terre.png")); // chargement de l'image globale

                //BufferedImage salade = image.getSubimage(x, y, w, h); // image du légume le légume (x, y : coin supérieur gauche, w, h : largeur et hauteur)

                //ImageIcon iconeSalade = imageBuffer.getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH); // icône redimentionnée
                ImageIcon icon = new ImageIcon(imageBuffer);
                Image image = icon.getImage().getScaledInstance(icon.getIconWidth()*2,
                                                                icon.getIconHeight()*2,
                                                                Image.SCALE_SMOOTH);
                icon = new ImageIcon(image, icon.getDescription());
                ptest.setIcon(icon); // partie rafraichissement



                pan.add(ptest);

                final int ii = i;
                final int jj = j;

                /*tabG[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        M.maj(ii,jj);
                    }
                });*/
            }
        }
        pan.setBorder(blackline);
        add(pan);
        //setContentPane(pan);
    }

    @Override
    public void update(Observable o, Object arg) {
        for (int i = 0; i < P.height; i++) {
            for (int j = 0; j < P.width; j++) {
                if (P.getParcelle(j, i).humidity > 10) {
                    tabG[i][j].setBackground(Color.RED);
                } else {
                    tabG[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}
