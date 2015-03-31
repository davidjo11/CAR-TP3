package site;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote{

	public void propager() throws RemoteException;
	
	public void setData(byte[] message) throws RemoteException;
	
	public void ajouterFils(SiteItf fils) throws RemoteException;
	
	public void setPere(SiteItf pere) throws RemoteException;
	
	public byte[] getData() throws RemoteException;
	
	public String getId() throws RemoteException;
}

