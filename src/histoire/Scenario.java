package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class Scenario {

    public static void main(String[] args) {
        Village village = new Village("le village des irreductibles", 10, 5);
        Chef abraracourcix = new Chef("Abraracourcix", 10, village);
        village.setChef(abraracourcix);
        Druide druide = new Druide("Panoramix", 2, 5, 10);
        Gaulois obelix = new Gaulois("Obelix", 25);
        Gaulois asterix = new Gaulois("Asterix", 8);
        Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
        Gaulois bonemine = new Gaulois("Bonemine", 7);

        village.ajouterHabitant(bonemine);
        village.ajouterHabitant(assurancetourix);
        village.ajouterHabitant(asterix);
        village.ajouterHabitant(obelix);
        village.ajouterHabitant(druide);
        
        try {
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
         
        System.out.println(village.rechercherVendeursProduit("fleurs"));
        System.out.println(village.installerVendeur(bonemine, "fleurs", 20));
        System.out.println(village.rechercherVendeursProduit("fleurs"));
        System.out.println(village.installerVendeur(assurancetourix, "lyres", 5));
        System.out.println(village.installerVendeur(obelix, "menhirs", 2));
        System.out.println(village.installerVendeur(druide, "fleurs", 10));

        System.out.println(village.rechercherVendeursProduit("fleurs"));
        Etal etalFleur = village.rechercherEtal(bonemine);

        try {
            System.out.println(etalFleur.acheterProduit(10, abraracourcix));
        } catch (Exception e) {
            System.out.println("Erreur lors de l'achat par Abraracourcix : " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            System.out.println(etalFleur.acheterProduit(15, obelix));
        } catch (Exception e) {
            System.out.println("Erreur lors de l'achat par Obélix : " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            System.out.println(etalFleur.acheterProduit(15, assurancetourix));
        } catch (IllegalStateException e) {
            System.out.println("L'achat n'a pas été effectué car l'étal est vide ");
            e.printStackTrace();
        }
        
        System.out.println("Test d'achat avec quantité négative sur un étal occupé :");
        System.out.println(village.installerVendeur(asterix, "boucliers", 5));
        Etal etalBoucliers = village.rechercherEtal(asterix);
        try {
            System.out.println(etalBoucliers.acheterProduit(-3, abraracourcix));
        } catch (IllegalArgumentException e) {
            System.out.println("L'achat n'a pas été effectué car la quantité doit être positive");
            e.printStackTrace();
        }
        
        System.out.println(village.partirVendeur(bonemine));
        System.out.println(village.afficherMarche());
    }
}
