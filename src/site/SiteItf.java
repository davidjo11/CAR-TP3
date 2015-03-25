package site;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote{

	public void propager() throws RemoteException;
}
