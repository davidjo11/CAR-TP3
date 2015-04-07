package site.graphe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import site.utils.TransfertException;

/**
 * Interface pour l'implémentation de Sites.
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public interface SiteItf extends Remote{
	
	/**
	 * Transmet un message à un site.
	 * @param donnees le message a envoyer
	 * @throws RemoteException
	 * @throws TransfertException 
	 */
	public void envoyerMessage(byte[] data) throws RemoteException, TransfertException;

	/**
	 * Propage le message donné en paramètre aux voisins du site actuel.
	 * @param donnees le message a transferer
	 * @throws RemoteException
	 * @throws TransfertException 
	 */
	public void propagerMessageAuxVoisins(byte[] data) throws RemoteException,TransfertException ;
	
	/**
	 * Modifie le message reçu par le site actuel.
	 * @throws RemoteException
	 */
	public void setData(byte[] message) throws RemoteException;
	
	/**
	 * Ajoute un voisin au site actuel.
	 * @param voisin le site a connecter au site actuel
	 * @throws RemoteException
	 */
	public void ajouterVoisin(SiteItf voisin) throws RemoteException;
	
	/**
	 * Retourne l'identifiant du site actuel.
	 * @throws RemoteException
	 */
	public String getId() throws RemoteException;
	
	/**
	 * Réinitialise le graphe afin de le réutiliser.
	 * @param data les données a effacer
	 * @throws RemoteException
	 */
	public void reset(byte[] data) throws RemoteException;
	
	/**
	 * Retourne la liste des données reçue par le site.
	 * @return les données
	 * @throws RemoteException
	 */
	public List<String> getDatas() throws RemoteException;
}

