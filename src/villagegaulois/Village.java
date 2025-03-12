package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalsMarche);
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
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException(
					"Le village " + nom + " n'a pas de chef. Impossible d'afficher les villageois.");
		}

		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {

			int compteur = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					compteur++;
				}
			}

			Etal[] etalsContientProduit = new Etal[compteur];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsContientProduit[j] = etals[i];
					j++;
				}
			}

			return etalsContientProduit;
		}

		public Etal trouverVendeur(Gaulois gaulois) {

			for (int i = 0; i < etals.length; i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (vendeur != null && vendeur.equals(gaulois)) {
					return etals[i];
				}
			}

			return null;
		}

		public String afficherMarche() {
			StringBuilder affichage = new StringBuilder();
			int nbEtalsLibres = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					affichage.append(etals[i].afficherEtal());
				} else {
					nbEtalsLibres++;
				}
			}
			if (nbEtalsLibres > 0) {
				affichage.append("Il reste " + nbEtalsLibres + " étals non utilisés dans le marché.\n");
			}

			return affichage.toString();
		}
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {

		StringBuilder affichage = new StringBuilder();
		affichage.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");

		int etalLibre = marche.trouverEtalLibre();

		if (etalLibre != -1) {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			affichage.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°"
					+ (etalLibre + 1) + ".\n");
		} else {
			affichage.append("Malheureusement, il n'y a plus aucun étal libre, vous ne pourrez pas vendre de "
					+ produit + ".\n");
		}

		return affichage.toString();

	}

	public String rechercherVendeursProduit(String produit) {

		StringBuilder affichage = new StringBuilder();
		Etal[] etalsContenantProduit = marche.trouverEtals(produit);
		if (marche.trouverEtals(produit).length == 0) {
			affichage.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.");
		} else if (marche.trouverEtals(produit).length == 1) {
			affichage.append("Seul le vendeur " + etalsContenantProduit[0].getVendeur().getNom() + " propose des "
					+ produit + " au marché");
		} else {
			affichage.append("Les vendeurs qui proposent des " + produit + " sont : \n");
			for (int i = 0; i < etalsContenantProduit.length; i++) {
				affichage.append("- " + etalsContenantProduit[i].getVendeur().getNom() + " \n");

			}
		}

		return affichage.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		try {
			Etal etal = rechercherEtal(vendeur);
			if (etal != null) {
				return etal.libererEtal();
			} else {
				return "Le vendeur " + vendeur.getNom() + " n'a pas d'étal dans le marché.\n";
			}
		} catch (EtalNonOccupeException e) {
			e.printStackTrace();
			return " ";
		}
	}

	public String afficherMarche() {
		StringBuilder affichage = new StringBuilder();
		int nbEtalsLibres = 0;
		for (int i = 0; i < marche.etals.length; i++) {
			Etal etal = marche.etals[i];
			if (etal.isEtalOccupe()) {
				affichage.append(etal.afficherEtal());
			} else {
				nbEtalsLibres++;
			}
		}

		if (nbEtalsLibres > 0) {
			affichage.append("Il reste " + nbEtalsLibres + " étals non utilisés dans le marché.\n");
		}

		return affichage.toString();
	}

}