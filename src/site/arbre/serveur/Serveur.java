package site.arbre.serveur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.arbre.SiteImpl;
import site.arbre.SiteItf;
import site.utils.Tools;

/**
 * Créé un serveur RMI (pour les arbres).
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Serveur {

	/*
	 * Paramètres possibles:
	 * 1 - (OBLIGATOIRE) le numéro de port du serveur 
	 * 2 - (OBLIGATOIRE) le nom du site 
	 * 3 - (optionnel) le numéro de port du parent
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {

		if (args.length >= 2) {
			// On récupère le port passé en argument.
			int port = Integer.parseInt(args[0]);
			// On créé le site avec le port.
			SiteImpl site = new SiteImpl(args[1]);
			// On créé un registre.
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(Tools.SITE_NAME, site);
	
			System.out.println("Server "+ site.getId() +" created!");
	
			// Si on a plus de 2 arguments c'est que l'on souhaite ajouter des voisins.
			if(args.length > 2){
				// On récupère l'ensemble des ports voisins qui sont séparés par une virgule.
				registry = LocateRegistry.getRegistry(Integer.parseInt(args[2]));
				SiteItf pere = (SiteItf) registry.lookup(Tools.SITE_NAME);
				
				// On ajoute le fils au père
				pere.ajouterFils(site);
				// On ajoute le père au fils
				site.setPere(pere);
			}
		}
		else {
			Tools.usageArbreServeur();
		}
	}
}
