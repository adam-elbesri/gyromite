package modele.plateau;

public class Corde extends EntiteStatique {
    public Corde(Jeu _jeu) { super(_jeu); }
    public boolean peutServirDeSupport() { return true; }
        //public boolean peutEtreEcrase() { return false; }
        public boolean peutPermettreDeMonterDescendre() { return true; };
         //public boolean peutEtreRamasser(){return false;};


}
