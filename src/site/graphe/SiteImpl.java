package site.graphe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
@SuppressWarnings("serial")
public class SiteImpl extends UnicastRemoteObject implements SiteItf{

	private static final String ID_DEFAULT = "Site by Default";
	
	// Sites reliés au site actuel
	private List<SiteItf> voisins;
	// ID du site actuel
	private String id;
	// True : le site a déja reçu le message, False sinon
	private boolean visited;
	// Message reçu
	private byte[] data;

	/**
	 * Constructeur pour un site par défaut.
	 * @throws RemoteException
	 */
	public SiteImpl() throws RemoteException{
		super();
		this.voisins = new ArrayList<SiteItf>();
		this.visited = false;
		this.id = ID_DEFAULT;
	}

	/**
	 * Constructeur pour un site avec un identifiant spécifique.
	 * @param id l'identifiant du site à créer
	 * @throws RemoteException
	 */
	public SiteImpl(String id) throws RemoteException{
		super();
		this.voisins = new ArrayList<SiteItf>();
		this.visited = false;
		this.id = id;
	}

	/**
	 * Ajoute un lien entre le site actuel et un autre site spécifié.
	 * @param voisin le site a connecter au site actuel
	 */
	public void ajouterVoisin(SiteItf voisin) throws RemoteException{
		this.voisins.add(voisin);
		voisin.ajouterVoisin((SiteItf) this);
	}

	/**
	 * Modifie l'identifiant du site actuel.
	 * @param id le nouvel identifiant.
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Retourne l'identifiant du site actuel.
	 */
	public String getId()throws RemoteException{
		return this.id;
	}

	/**
	 * Retourne le message reçu par le site actuel.
	 */
	public byte[] getData() throws RemoteException{
		return this.data;
	}

	/**
	 * Modifie le message reçu par le site actuel.
	 */
	public void setData(byte[] data){
		this.data = data;
	}

	/**
	 * Teste si le site a été visité.
	 */
	public boolean isVisited() throws RemoteException{
		return this.visited;
	}

	
	public void envoyerMessage(byte[] donnees) throws RemoteException {
		synchronized(this) {
		if(!this.visited)
			this.propagerMessageAuxVoisins(donnees);
		}
	}
	
	public void propagerMessageAuxVoisins(byte[] donnees) throws RemoteException{
		List<Transfert> transferts = new ArrayList<Transfert>();
		
		System.out.println(id + " a reçu le message \n\""+ new String(donnees));
		this.visited = true;
		
		
		synchronized(this.voisins) {
			for (int i=0; i < this.voisins.size(); i++) {
				Transfert transf = new Transfert(donnees,this.voisins.get(i));
				transferts.add(transf);
				transf.start();
			}
		}
		
		Iterator<Transfert> it = transferts.iterator();
		
		synchronized(it) {
			while(it.hasNext()) {
				Transfert transf = it.next();
				try {
					transf.join();
				} catch (InterruptedException e) {
					System.out.println("Le thread a été interrompu.");
				}

			}
		}
	}


}
