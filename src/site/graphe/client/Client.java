package site.graphe.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.graphe.SiteItf;
import site.utils.Tools;

public class Client {

	/*
	 * Le client a la possibilité de passer en paramètre:
	 * 1 - (OBLIGATOIRE) le port du site racine (depuis lequel le client souhaite propager le message)
	 * 2 - (optionnel) le message à propager
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			int port;

			String message;

			Registry registry;
			
			if(args[0] != null){
				port = Integer.parseInt(args[0]);
				if(args.length > 1)
					message = args[1];
				else message = "Le fameux message.";
			}
			else{
				message = "Le fameux message.";
				port = Registry.REGISTRY_PORT;
			}

			registry = LocateRegistry.getRegistry(port);

			SiteItf s = (SiteItf) registry.lookup(Tools.SITE_NAME);

			s.setData(message.getBytes());

			s.propager();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
