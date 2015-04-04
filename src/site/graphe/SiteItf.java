package site.graphe;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public interface SiteItf extends Remote{
	
	public void envoyerMessage(byte[] donnees) throws RemoteException;

	public void propagerMessageAuxVoisins(byte[] le_message) throws RemoteException;
	
	public void setData(byte[] message) throws RemoteException;
	
	public void ajouterVoisin(SiteItf voisin) throws RemoteException;
	
	public byte[] getData() throws RemoteException;
	
	public String getId() throws RemoteException;
	
	public boolean isVisited() throws RemoteException;
	
}

