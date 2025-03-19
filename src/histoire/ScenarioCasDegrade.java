package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

    public static void main(String[] args) {
        try {
            Etal etal = new Etal();
            System.out.println(etal.libererEtal());
        } catch (IllegalStateException e) {
            System.out.println("L'étal ne comporte pas de vendeur");
            e.printStackTrace();
        }
        
        try {
            Etal etal = new Etal();
            Gaulois vendeur = new Gaulois("Vendeur", 10);
            etal.occuperEtal(vendeur, "produit", 10);
            Gaulois acheteur = new Gaulois("Acheteur", 10);
            etal.acheterProduit(-5, acheteur);
        } catch (IllegalArgumentException e) {
            System.out.println("L'achat n'a pas été effectué (quantité négative)");
            e.printStackTrace();
        }
        
        try {
            Etal etal = new Etal();
            Gaulois vendeur = new Gaulois("Vendeur", 10);
            etal.occuperEtal(vendeur, "produit", 10);
            etal.acheterProduit(5, null);
        } catch (IllegalArgumentException e) {
            System.out.println("L'achat n'a pas été effectué car l'acheteur est null");
            e.printStackTrace();
        }

        try {
            Etal etal = new Etal(); 
            Gaulois acheteur = new Gaulois("Acheteur", 10);
            etal.acheterProduit(3, acheteur);
        } catch (IllegalStateException e) {
            System.out.println("L'achat n'a pas été effectué car l'étal est vide");
            e.printStackTrace();
        }

        try {
            Village village = new Village("Village des Irréductibles", 10, 4);
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.out.println("Le village ne comporte pas de chef");
            e.printStackTrace();
        }
    }
}
