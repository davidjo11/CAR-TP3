package site.graphe.serveur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.graphe.SiteImpl;
import site.graphe.SiteItf;
import site.utils.Tools;

/**
 * Créé un serveur RMI (pour les graphe).
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Serveur {

	/*
	 * Paramètres possibles:
	 * 1 - (OBLIGATOIRE) le numéro de port du serveur 
	 * 2 - (OBLIGATOIRE) le nom du site (à mettre entre guillemets)
	 * 
	 * 3 - (optionnel) les numéros des ports voisins (séparés par des virgules, Ex: 4001,4004,56132,6541,65156)
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
				String[] ports = args[2].split(",");
	
				// On créé les liens entre sites pour chacun des voisins
				for(int i=0; i<ports.length; i++){
					registry = LocateRegistry.getRegistry(Integer.parseInt(ports[i]));
	
					SiteItf voisin = (SiteItf) registry.lookup(Tools.SITE_NAME);
	
					voisin.ajouterVoisin(site);
					site.ajouterVoisin(voisin);
				}
			}
		}
		else {
			Tools.usageGrapheServeur();
		}

	}

}
