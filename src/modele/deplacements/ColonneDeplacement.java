package modele.deplacements;

import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ColonneDeplacement extends RealisateurDeDeplacement {
private Direction directionCourante;
    // Design pattern singleton
    private static ColonneDeplacement c3d;

    public static ColonneDeplacement getInstance() {
        if (c3d == null) {
            c3d = new ColonneDeplacement();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (directionCourante != null)
                switch (directionCourante) {
                    case haut:  Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                        if (eHaut == null || eHaut instanceof Colonne){
                        if (e.avancerDirectionChoisie(Direction.haut)){
                                ret = true;
                        }break;}
                    case bas:
                       Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || eBas.peutEtreEcrase()){
                            if(e.avancerDirectionChoisie(Direction.bas)) {
                                ret = true;
                            } break;
                }}
        }

        return ret;

    }

    public void resetDirection() {
        directionCourante = null;
    }
}
