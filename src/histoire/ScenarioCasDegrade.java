package histoire;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.EtalNonOccupeException;

public class ScenarioCasDegrade {

	 public static void main(String[] args) throws EtalNonOccupeException {
			Etal etal = new Etal();
			etal.libererEtal();
			System.out.println("Fin du test");
		 	Village village = new Village(null, 0, 0);
	        Gaulois bonemine = new Gaulois("Bonemine", 10);

	        System.out.println(village.partirVendeur(bonemine));  
	    }
	}
