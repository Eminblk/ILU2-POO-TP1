package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		
		//libererEtal
		try {
			Etal etal = new Etal();
			etal.libererEtal();
		}
		catch(NullPointerException e){
			System.out.println("L'étal ne comporte pas de vendeur ");
			e.printStackTrace();
		}
		
		//acheterProduit b)
		try {
			Etal etal = new Etal();
			Gaulois acheteur = new Gaulois("Acheteur", 10);
			etal.acheterProduit(-5, acheteur);
			
		}
		catch(IllegalArgumentException e) {
			System.out.println("L'achat n'a pas été effecuté ");
			e.printStackTrace();
		}
		
		
		//acheter produit c) 
		try {
			Etal etal = new Etal();
			Gaulois acheteur = new Gaulois("Acheteur", 10);
			etal.acheterProduit(3, acheteur);
        } catch (IllegalStateException e) {
            System.out.println("L'achat n'a pas été effectué car l'étal est vide");
            e.printStackTrace();
        }
		
		//village sans chef
		try {
            Village village = new Village("Village des Irréductibles", 10, 4);
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.out.println("Le village ne comporte pas de chef");
            e.printStackTrace();
        }
	}

}