package VueControleur;
import modele.plateau.CompteARebours;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import modele.deplacements.ColonneDeplacement;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    
     
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    // taille de la grille affichée
    private final int sizeX; 
    private final int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoVictoire;
    private ImageIcon icoPerdu;
    private ImageIcon icoHeroSurCorde;
    private ImageIcon icoBombe;
    private ImageIcon icoBonus;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoSol;
    private ImageIcon icoSolVertical;
    private ImageIcon icoColonne;
    private ImageIcon icoCorde;
    private ImageIcon icoSmick;
    private Image Fond;
    private CompteARebours compteARebours;
    //private Graphics g;
    
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

 
    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_X : ColonneDeplacement.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_C : ColonneDeplacement.getInstance().setDirectionCourante(Direction.bas); break;

                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/Hector.png");
        icoVictoire = chargerIcone("Images/Victoire.png");
        icoPerdu = chargerIcone("Images/Perdu.png");
        icoHeroSurCorde = chargerIcone("Images/HeroSurCorde.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoColonne = chargerIcone("Images/Colonne.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoSol = chargerIcone("Images/Sol.png");
        icoSolVertical = chargerIcone("Images/SolVertical.png");
        icoBombe = chargerIcone("Images/Bombe.png");
        icoBonus = chargerIcone("Images/Radis.png");
        icoCorde = chargerIcone("Images/Corde.png");
        icoSmick = chargerIcone("Images/Smick.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(1900, 1100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        
      
        add(grilleJLabels);
 
        
       
        
        compteARebours = new CompteARebours();
       
    }
    

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
              
                 if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    // System.out.println("Héros !");
                    if(jeu.statut=="VICTOIRE")
                    {tabJLabel[x][y].setIcon(icoVictoire);}
                    else if(jeu.statut=="Perdu")
                        {tabJLabel[x][y].setIcon(icoPerdu);}
                       else if(jeu.HeroSurCorde)
                        {tabJLabel[x][y].setIcon(icoHeroSurCorde);}
                    else
                    tabJLabel[x][y].setIcon(icoHero);
                } else if (jeu.getGrille()[x][y] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (jeu.getGrille()[x][y] instanceof Colonne) {
                    tabJLabel[x][y].setIcon(icoColonne);
                } else if (jeu.getGrille()[x][y] instanceof Sol) {
                    tabJLabel[x][y].setIcon(icoSol);}
                  else if (jeu.getGrille()[x][y] instanceof SolVertical) {
                    tabJLabel[x][y].setIcon(icoSolVertical);}
                 else if (jeu.getGrille()[x][y] instanceof Bombe){ 
                    tabJLabel[x][y].setIcon(icoBombe);
                 }
                   else if (jeu.getGrille()[x][y] instanceof Corde){ 
                    tabJLabel[x][y].setIcon(icoCorde);
                 }
                 else if (jeu.getGrille()[x][y] instanceof Bonus){ 
                    tabJLabel[x][y].setIcon(icoBonus);
                 }
                 else if (jeu.getGrille()[x][y] instanceof Bot){ 
                    tabJLabel[x][y].setIcon(icoSmick);
                 }
                 else if (x==0 && y==0) {
                     String timer = this.compteARebours.getStr();
                     
               
                     tabJLabel[0][0].setText(timer);
                     tabJLabel[0][0].setForeground(Color.red);
                                           
                 }
                   else if (x==1 && y==0) {
                    
               
                     tabJLabel[1][0].setText("Nb Bombes :");
                      tabJLabel[1][0].setForeground(Color.red);
                                           
                 }
                  else if (x==2 && y==0) {
                     int timer1 = jeu.compteur_bombes;
                     
               
                     tabJLabel[2][0].setText(String.valueOf(timer1));
                      tabJLabel[2][0].setForeground(Color.red);
                                           
                 }
                    else if (x==3 && y==0) {
                     
                     
               
                     tabJLabel[3][0].setText(jeu.score);
                      tabJLabel[3][0].setForeground(Color.red);
                                           
                 }    else if (x==4 && y==0) {
                     
                     
               
                     tabJLabel[4][0].setText(jeu.statut);
                     tabJLabel[4][0].setForeground(Color.green);
                                           
                 }
                else {
                    tabJLabel[x][y].setIcon(icoVide);
                }    
         }       
    }
  
    
    }
 

    @Override
    public void update(Observable o, Object arg) {
        //print
        //System.out.print(tabJLabel[6][9]);
                //System.out.println(this.compteARebours.getStr());
    if(jeu.encours)
    {
       
        
        mettreAJourAffichage();
        if (jeu.statut=="Perdu")
        {System.out.println("PERDU LOL");
            mettreAJourAffichage();
            jeu.encours=false;}
        if(jeu.compteur_bombes >=4 || this.compteARebours.getCompteurTemps()==0)
        {System.out.println("JEU FINI");
        jeu.scorechiffre=jeu.scorechiffre+(this.compteARebours.getCompteurTemps()*10);
        jeu.score="Score " + jeu.scorechiffre;
       
         jeu.statut="VICTOIRE";
        mettreAJourAffichage();
        jeu.encours=false;}
        
        if(this.compteARebours.getCompteurTemps()==0)
        {System.out.println("JEU FINI");
        jeu.scorechiffre=jeu.scorechiffre+(this.compteARebours.getCompteurTemps()*10);
        jeu.score="Score " + jeu.scorechiffre;
        jeu.statut="Perdu";
        mettreAJourAffichage();
        jeu.encours=false;}
        
           
    }
    
    
       /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }


}
;