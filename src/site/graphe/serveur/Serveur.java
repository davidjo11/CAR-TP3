package site.graphe.serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.graphe.SiteImpl;
import site.graphe.SiteItf;
import site.utils.Tools;

public class Serveur {

	/*
	 * Paramètres possibles:
	 * 1 - (OBLIGATOIRE) le numéro de port du serveur 
	 * 2 - (OBLIGATOIRE) le nom du site 
	 * 3 - (optionnel) les numéros des ports voisins (entre guillements séparés par des virgules, Ex: 4001,4004,56132,6541,65156)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			int port = Integer.parseInt(args[0]);

			SiteImpl site = new SiteImpl(args[1]);

			Registry registry = LocateRegistry.createRegistry(port);

			registry.rebind(Tools.SITE_NAME, site);

			System.out.println("Server "+ site.getId() +" created!");

			if(args.length > 2){
				String[] ports = args[2].split(",");

				for(int i=0; i<ports.length; i++){
					registry = LocateRegistry.getRegistry(Integer.parseInt(ports[i]));

					SiteItf voisin = (SiteItf) registry.lookup(Tools.SITE_NAME);

					voisin.ajouterVoisin(site);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
