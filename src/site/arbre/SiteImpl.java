package site.arbre;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import site.utils.TransfertException;

/**
 * Classe de création de Site (pour un arbre).
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
@SuppressWarnings("serial")
public class SiteImpl extends UnicastRemoteObject implements SiteItf{

	private static final String ID_DEFAULT = "Site by Default";
	
	private List<SiteItf> children;
	
	private String id;
	
	@SuppressWarnings("unused")
	private SiteItf pere;
	
	private byte[] data;
	
	/**
	 * Constructeur pour un site par défaut.
	 * @throws RemoteException
	 */
	public SiteImpl() throws RemoteException{
		super();
		this.children = new ArrayList<SiteItf>();
		this.id = ID_DEFAULT;
	}
	
	
	/**
	 * Constructeur pour un site avec un identifiant spécifique.
	 * @param id l'identifiant du site à créer
	 * @throws RemoteException
	 */
	public SiteImpl(String id) throws RemoteException{
		super();
		this.children = new ArrayList<SiteItf>();
		this.id = id;
	}
	
	/**
	 * Ajoute un fils au site actuel.
	 * @param fils le site a connecter au site actuel
	 */
	public void ajouterFils(SiteItf fils) throws RemoteException{
		this.children.add(fils);
	}
	
	/**
	 * Fixe le père du site actuel.
	 * @param message
	 * @throws RemoteException
	 */
	public void setPere(SiteItf pere) throws RemoteException{
		this.pere = pere;
	}
	
	
	/**
	 * Retourne l'identifiant du site actuel.
	 * @throws RemoteException
	 */
	public String getId()throws RemoteException{
		return this.id;
	}
	
	
	/**
	 * Retourne le message reçu par le site actuel.
	 * @throws RemoteException
	 */
	public byte[] getData() throws RemoteException{
		return this.data;
	}
	
	
	/**
	 * Modifie le message reçu par le site actuel.
	 * @throws RemoteException
	 */
	public void setData(byte[] data) throws RemoteException {
		this.data = data;
	}
	
	
	/**
	 * Propage le message du site vers ses fils.
	 * @throws RemoteException
	 * @throws TransfertException
	 */
	public void propager() throws RemoteException, TransfertException{
		int i = 0;
		Transfert[] tFils = new Transfert[this.children.size()];
			
		Iterator<SiteItf> it = this.children.iterator();
			
		while(it.hasNext())
			tFils[i++] = new Transfert(this, it.next());
			
		for(i=0; i<this.children.size();i++){
			tFils[i].start();
		}
			
		for(i=0; i<this.children.size();i++){
			try {
				tFils[i].join();
			} catch (InterruptedException e) {
				throw new TransfertException();
			}
		}
		System.out.println("Propagation vers les fils terminée.");
		
	}
	
}
