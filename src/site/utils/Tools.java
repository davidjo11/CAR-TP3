package site.utils;

/**
 * Utilitaires.
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Tools {

	public static final String SITE_NAME = "Site";

	/**
	 * Affiche l'utilisation d'un serveur pour les graphes.
	 */
	public static void usageGrapheServeur() {
		System.out.println("Mauvaise utilisation ! nécéssite :\n"
		+"- port (obligatoire)  : le port associé au site.\n"
		+"- name (obligatoire)  : le nom du site.\n"
		+"- portPere (optionnel): le port du site pere.\n"
		);
	}
	
	/**
	 * Affiche l'utilisation d'un serveur pour les arbres.
	 */
	public static void usageArbreServeur() {
		System.out.println("Mauvaise utilisation ! nécéssite :\n"
		+"- port (obligatoire)  : le port associé au site.\n"
		+"- name (obligatoire)  : le nom du site.\n"
		+"- portsVoisins (optionnel): les ports des sites voisins séparés par une virgule (ex: 4504,5780).\n"
		);		
	}
}
