package site.graphe;

import java.rmi.Remote;
import java.rmi.RemoteException;

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
	 * Ajoute un lien entre le site actuel et un autre site spécifié.
	 * @param voisin le site a connecter au site actuel
	 * @throws RemoteException
	 */
	public void ajouterVoisin(SiteItf voisin) throws RemoteException;
	
	/**
	 * Retourne le message reçu par le site actuel.
	 * @throws RemoteException
	 */
	public byte[] getData() throws RemoteException;
	
	/**
	 * Retourne l'identifiant du site actuel.
	 * @throws RemoteException
	 */
	public String getId() throws RemoteException;
	
	/**
	 * Teste si le site a été visité.
	 * @throws RemoteException
	 */
	public boolean isVisited() throws RemoteException;
	
	/**
	 * Réinitialise le graphe afin de le réutiliser.
	 * @throws RemoteException
	 */
	public void reset() throws RemoteException;
}

