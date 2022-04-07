package modele.plateau;

public class Bonus extends EntiteStatique {
    public Bonus(Jeu _jeu) { super(_jeu); }
    
 
        public boolean peutEtreRamasser(){return true;};
        public boolean peutServirDeSupport(){return false;}

}
