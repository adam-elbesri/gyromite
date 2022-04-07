/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.Gravite;
import modele.deplacements.Ordonnanceur;
import modele.plateau.CompteARebours;
import modele.deplacements.ColonneDeplacement;

import java.awt.Point;
import java.util.HashMap;
import modele.deplacements.IA;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 12;
    public  int compteur_bombes = 0;
    public boolean encours=true;
    public boolean HeroSurCorde=false;   
    public boolean BotSurCorde=false;

         public int scorechiffre=0;
        public String score = "Score " + scorechiffre;
        public String statut="Jeu en cours";
  
   // Jeu.compteur_bombes=0;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;
    private Bot smick;
    private Colonne cube;
    private Colonne cube1;
    private Colonne cube2;
    private Colonne cube3;
    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return hector;
    }
    
    public Colonne getColonne(){
        return cube;
    }
    public Bot getSmick() {
        return smick;
    }
    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
        
    }
    private void initialisationDesEntites() {
        hector = new Heros(this);
        addEntite(hector, 1, 1);
        cube = new Colonne(this);
        addEntite(cube, 9,3);
         cube1 = new Colonne(this);
         addEntite(cube1, 9,2);
         cube2 = new Colonne(this);
        addEntite(cube2, 9,1);
        smick = new Bot (this);
        cube3 = new Colonne (this);
        addEntite(cube3, 17,8);
       
        
        addEntite (smick, 12, 3);
        System.out.println(smick.r);

        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        
        g.addEntiteDynamique(smick);
        ordonnanceur.add(g);
      

        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ColonneDeplacement.getInstance().addEntiteDynamique(cube);
        ColonneDeplacement.getInstance().addEntiteDynamique(cube1);
        ColonneDeplacement.getInstance().addEntiteDynamique(cube2);
        ColonneDeplacement.getInstance().addEntiteDynamique(cube3);


        
        IA.getInstance().addEntiteDynamique(smick);
   
        
        ordonnanceur.add(Controle4Directions.getInstance());
       ordonnanceur.add(ColonneDeplacement.getInstance());
       ordonnanceur.add(IA.getInstance());
      

     

        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X; x++) {
            
            addEntite(new Mur(this), x, SIZE_Y-1);
        }

        
        for (int b= 5; b< SIZE_X;b++)
        {addEntite(new Mur(this), b, 0);
        
        }

        // murs extérieurs verticaux
        for (int y = 1; y < SIZE_Y-1; y++) {
            addEntite(new SolVertical(this), 0, y);
            addEntite(new SolVertical(this), SIZE_X-1, y);
           
        }
          for (int z = 1; z < SIZE_Y-1; z++) {
            addEntite(new Corde(this), SIZE_X-2, z);}
 
        
        addEntite(new Sol(this),1,3);
         
          addEntite(new Sol(this),3,3);
          addEntite(new Sol(this),4,3);
           
             addEntite(new Sol(this),6,5);
             addEntite (new Corde(this), 6, 3);
             addEntite (new Corde(this), 6, 2);
                       addEntite (new Corde(this), 6, 1);
                       addEntite (new Corde(this), 6, 4);
        
                addEntite(new SolVertical(this), 5, 8);
        addEntite(new SolVertical(this), 5, 9);
        addEntite(new SolVertical(this), 5, 10);
        addEntite (new Bombe(this), 3, 2);
          addEntite(new SolVertical(this), 3, 5);
        addEntite(new SolVertical(this), 3, 6);
        addEntite(new SolVertical(this), 3, 4);
        addEntite (new Bombe(this), 6, 10);
        addEntite (new Bombe(this), 4, 5);
         addEntite (new Corde(this), 4, 10);
                 addEntite (new Corde(this), 4, 9);
                  addEntite (new Corde(this), 4, 8);
                     addEntite (new Corde(this), 4, 7);
                                  addEntite(new Sol(this),6,8);
                    addEntite(new Sol(this),7,8);
                     addEntite(new Sol(this),8,8);
                  addEntite(new Sol(this),4,6);
                  
                  addEntite(new Sol(this), 17, 7); 
                  addEntite(new Sol(this), 17, 5);
        
                  
        addEntite(new Sol(this), 15, 4);
        addEntite(new Sol(this), 14, 4);
        addEntite(new Sol(this), 13, 4); 
        addEntite(new Sol(this), 12, 4);
        addEntite(new Sol(this), 11, 4);
        addEntite(new Sol(this), 10, 4);        
        addEntite (new Corde(this), 10, 3);
        addEntite (new Corde(this), 10, 2);
        addEntite (new Corde(this), 10, 1);            
        addEntite(new Sol(this), 17, 5);
        addEntite(new Sol(this), 16, 5);
        addEntite(new Sol(this), 15, 5);
        addEntite(new Sol(this), 14, 5);
        addEntite(new Sol(this), 13, 5);
        addEntite(new Sol(this), 12, 5);
        addEntite(new Sol(this), 11, 5);
        addEntite(new Bombe(this),10, 5);   
        addEntite(new SolVertical(this), 10, 6);
        addEntite (new Bonus(this), 11, 6);    
        addEntite(new Sol(this), 17, 7);
        addEntite(new Sol(this), 16, 7);
        addEntite(new Sol(this), 15, 7);
        addEntite(new Sol(this), 14, 7);
        addEntite(new Sol(this), 13, 7);
        addEntite(new Sol(this), 12, 7);
        addEntite(new Sol(this), 11, 7);
        addEntite(new Sol(this), 10, 7);  
        addEntite(new Sol(this), 16, 9);
        addEntite(new Sol(this), 15, 9);
        addEntite(new Sol(this), 14, 9);
        addEntite(new Sol(this), 13, 9);
        addEntite(new Sol(this), 12, 9);
        addEntite(new Sol(this), 11, 9);
        addEntite(new Sol(this), 10, 9);
        addEntite(new Sol(this), 9, 9);
    }

    
    
    /** Permet par exemple a une entité  de percevoir son environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        //System.out.println(compteur_bombes);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        
        //COLLISION
        
        if (objetALaPosition(pCible) instanceof Bot && objetALaPosition(pCourant) instanceof Heros)
        {
            System.out.println("COLLISION");
            statut="Perdu"; 
        }
        if (objetALaPosition(pCible) instanceof Heros && objetALaPosition(pCourant) instanceof Bot)
        {
            System.out.println("COLLISION");
            statut="Perdu"; 
        }
       
 
        if( (objetALaPosition(pCible)instanceof Bot) && (objetALaPosition(pCourant) instanceof Colonne))
        {System.out.println("ECRASEMENT");
        
            retour=true;}
        
      
       
        
        
        if (contenuDansGrille(pCible) && objetALaPosition(pCible) == null || objetALaPosition(pCible).peutEtreRamasser() || objetALaPosition(pCible).peutPermettreDeMonterDescendre()  ){ // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
           
            
            
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);

                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;

                    }
                    break;
            }
        }
          
        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }
        
        return retour;
    }
    
   /* public void deplacementsmick(Entite e)
    {
        for (int x=0; x<10; x++){
        Point pCourant = map.get(e);
        Point pCible=pCourant;
        pCible.x=pCourant.x++;
        deplacerEntite(pCourant, pCible,e);
        }
    }*/
 
    
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }

    
    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {

        
        grilleEntites[pCourant.x][pCourant.y] = null;
        
        //(tonObjet instanceof Mur) te renvoie un booleen
        if( grilleEntites[pCible.x][pCible.y] instanceof Bombe){
        compteur_bombes++;
        scorechiffre=scorechiffre+100;
        score = "Score " + scorechiffre;
          //System.out.println(score);
        System.out.println(compteur_bombes);}
        if( grilleEntites[pCible.x][pCible.y] instanceof Bonus){
      scorechiffre=scorechiffre+500;
      score = "Score " + scorechiffre;
      System.out.println(scorechiffre);
       ;}
        if( grilleEntites[pCible.x][pCible.y] instanceof Bot && e instanceof Colonne)
        {   System.out.println("ECRASEMENT 2");
        smick.vivant=false;
            grilleEntites[pCible.x][pCible.y] = e;
            map.put(e, pCible);}
        
        
        
        
        if( (grilleEntites[pCible.x][pCible.y] instanceof Corde) && (e instanceof Heros))
        {//System.out.println("Ya une corde ici");
            grilleEntites[pCible.x][pCible.y] = e;
            map.put(e, pCible);

            if(HeroSurCorde) {
                grilleEntites[pCourant.x][pCourant.y] = new Corde(this);}
            HeroSurCorde = true;
        }
        else if((grilleEntites[pCible.x][pCible.y] instanceof Corde) && (e instanceof Bot)) {
            grilleEntites[pCible.x][pCible.y] = e;
            map.put(e, pCible);

            if(BotSurCorde) {
                grilleEntites[pCourant.x][pCourant.y] = new Corde(this);}
            BotSurCorde = true;

            
       }
        else {grilleEntites[pCourant.x][pCourant.y] = null;
            grilleEntites[pCible.x][pCible.y] = e;
            map.put(e, pCible);

            if(HeroSurCorde && e instanceof Heros) {
                grilleEntites[pCourant.x][pCourant.y] = new Corde(this);
                HeroSurCorde = false;
            }
            if(BotSurCorde && e instanceof Bot) {
                grilleEntites[pCourant.x][pCourant.y] = new Corde(this);
                BotSurCorde = false;
            }
            
    
    }}
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
