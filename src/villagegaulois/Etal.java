package villagegaulois;

import personnages.Gaulois;

public class Etal {
    private Gaulois vendeur;
    private String produit;
    private int quantiteDebutMarche;
    private int quantite;
    private boolean etalOccupe = false;

    public boolean isEtalOccupe() {
        return etalOccupe;
    }

    public Gaulois getVendeur() {
        return vendeur;
    }

    public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
        this.vendeur = vendeur;
        this.produit = produit;
        this.quantite = quantite;
        this.quantiteDebutMarche = quantite;
        etalOccupe = true;
    }

    public String libererEtal() {
       
            etalOccupe = false;
            StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
            int produitVendu = quantiteDebutMarche - quantite;
            if (produitVendu > 0) {
                chaine.append("il a vendu " + produitVendu + " " + produit 
                        + " parmi les " + quantiteDebutMarche + " qu'il voulait vendre.\n");
            } else {
                chaine.append("il n'a malheureusement rien vendu.\n");
            }
            return chaine.toString();
      
    }

    public String afficherEtal() {
        if (etalOccupe) {
            return "L'étal de " + vendeur.getNom() + " est garni de " + quantite + " " + produit + "\n";
        }
        return "L'étal est libre";
    }

    public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
       //TODO traiter les erreurs
        if (quantiteAcheter < 1) {
            throw new IllegalArgumentException("La quantité achetée doit être supérieure à 0.");
        }
        if (!etalOccupe) {
            throw new IllegalStateException("L'étal est vide, on ne peut pas acheter un produit.");
        }
        
        StringBuilder chaine = new StringBuilder();
        chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter + " " 
                + produit + " à " + vendeur.getNom());
        if (quantite == 0) {
            chaine.append(", malheureusement il n'y en a plus !");
        } else if (quantiteAcheter > quantite) {
            chaine.append(", comme il n'y en a plus que " + quantite + ", " 
                    + acheteur.getNom() + " vide l'étal de " + vendeur.getNom() + ".\n");
            quantiteAcheter = quantite;
            quantite = 0;
        } else {
            quantite -= quantiteAcheter;
            chaine.append(". " + acheteur.getNom() + ", est ravi de tout trouver sur l'étal de " 
                    + vendeur.getNom() + "\n");
        }
        return chaine.toString();
    }

    public boolean contientProduit(String produit) {
        return produit.equals(this.produit);
    }
}
