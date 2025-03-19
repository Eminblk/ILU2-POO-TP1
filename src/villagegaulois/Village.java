package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef.");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal[] etals ;
		
		private Marche(int nbrEtal) {
			this.etals = new Etal[nbrEtal];
			for(int i = 0; i < nbrEtal ; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			if(indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
			else {
				System.out.println(vendeur.getNom() + "n'a pas pu emménager dans l'étal" ) ;
			}
			
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbrEtal= 0; //TODO erreur
			for(int i = 0; i < etals.length ; i++) {
				if((etals[i].isEtalOccupe()) && etals[i].contientProduit(produit)) {
					nbrEtal ++;
				}
			}
			Etal[] etalTrouve = new Etal[nbrEtal]; //TODO erreur
			int index = 0;
			for(int i = 0; i < etals.length ; i++) {
				if((etals[i].isEtalOccupe()) && etals[i].contientProduit(produit)) {
					etalTrouve[index] = etals[i];
					index++;
				}
			}
			return etalTrouve;
			
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etals.length; i++) {
				if((etals[i].isEtalOccupe()) && etals[i].getVendeur() == gaulois ) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide= 0;
			String res = "L'étal est libre";
			for(int i = 0; i< etals.length ; i++) {
				if(res.equals(etals[i].afficherEtal())) {
					 nbEtalVide++;
				}
				else {
					chaine.append(etals[i].afficherEtal());
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché. \n");
			return chaine.toString();
		}
	 }
	
	public String installerVendeur (Gaulois vendeur , String produit, int nbProduit) {
		StringBuilder res = new StringBuilder();
		
		int etalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		
        if (etalLibre != -1) {
        	res.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " "+produit + ".\n");
            marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
            res.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal n°" + (etalLibre+1) + ".\n");
        } else {
            System.out.println("Aucun étal disponible pour " + vendeur.getNom());
        }
        return res.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder res = new StringBuilder();
		Etal[] tab = marche.trouverEtals(produit);
		if (tab.length == 0) {
			res.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		}
		else if(tab.length== 1) {
			res.append("Seul le vendeur " + tab[0].getVendeur().getNom() + " propose des "+ produit + " au marché.\n");
		}
		else {
			res.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for(int i = 0 ; i < tab.length; i++) {
				res.append("-" + tab[i].getVendeur().getNom() + "\n");
			}
		}
		return res.toString();
	}
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal res = rechercherEtal(vendeur);
		return res.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder res = new StringBuilder();
		res.append("Le marché du village ' " + getNom() + " ' possède plusieurs étals : \n");
		res.append(marche.afficherMarche());
		return res.toString();		
	}
	
	
}