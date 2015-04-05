package site.arbre;

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
	 * Propage le message du site vers ses fils.
	 * @throws RemoteException
	 * @throws TransfertException
	 */
	public void propager() throws RemoteException, TransfertException;
	
	/**
	 * Modifie le message reçu par le site actuel.
	 * @throws RemoteException
	 */
	public void setData(byte[] message) throws RemoteException;
	
	/**
	 * Ajoute un fils au site actuel.
	 * @param fils le site a connecter au site actuel
	 * @throws RemoteException
	 */
	public void ajouterFils(SiteItf fils) throws RemoteException;
	
	/**
	 * Fixe le père du site actuel.
	 * @param message
	 * @throws RemoteException
	 */
	public void setPere(SiteItf pere) throws RemoteException;
	
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
}

