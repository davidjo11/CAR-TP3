package site.graphe.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import site.graphe.SiteItf;
import site.utils.Tools;
import site.utils.TransfertException;

/**
 * Créé un client RMI (pour les graphes).
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Client {

	/*
	 * Le client a la possibilité de passer en paramètre:
	 * 1 - (OBLIGATOIRE) le port du site racine (depuis lequel le client souhaite propager le message)
	 * 2 - (optionnel) le message à propager
	 */
	public static void main(String[] args) throws RemoteException, TransfertException, NotBoundException {

		int port;

		String message;

		Registry registry;
			
		if(args[0] != null){
			port = Integer.parseInt(args[0]);
			if(args.length > 1)
				message = args[1];
			else
				message = "Le fameux message.";
		}
		else {
			message = "Le fameux message.";
			port = Registry.REGISTRY_PORT;
		}

		// On récupère le site source
		registry = LocateRegistry.getRegistry(port);
		SiteItf s = (SiteItf) registry.lookup(Tools.SITE_NAME);

		// On transfert au site le message
		s.envoyerMessage(message.getBytes());
		// Une fois l'envoi effectué on réinitialise l'état des sites pour
		// Une eventuelle réutilisation
		s.reset();

	}

}
