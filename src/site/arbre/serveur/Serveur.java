package site.arbre.serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.arbre.SiteImpl;
import site.arbre.SiteItf;
import site.utils.Tools;

public class Serveur {

	/*
	 * Paramètres possibles:
	 * 1 - (OBLIGATOIRE) le numéro de port du serveur 
	 * 2 - (OBLIGATOIRE) le nom du site 
	 * 3 - (optionnel) le numéro de port du parent
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
				registry = LocateRegistry.getRegistry(Integer.parseInt(args[2]));
				
				SiteItf pere = (SiteItf) registry.lookup(Tools.SITE_NAME);
				
				pere.ajouterFils(site);
				
				site.setPere(pere);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
