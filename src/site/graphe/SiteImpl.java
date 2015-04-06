package site.graphe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import site.utils.TransfertException;


/**
 * Classe de création de Site (pour un graphe).
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
	 * Ajoute un voisin au site actuel.
	 * @param voisin le site a connecter au site actuel
	 */
	public void ajouterVoisin(SiteItf voisin) throws RemoteException{
		this.voisins.add(voisin);
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

	/**
	 * Transmet un message à un site.
	 * @param donnees le message a envoyer
	 * @throws TransfertException 
	 */
	public void envoyerMessage(byte[] data) throws RemoteException, TransfertException {
		// Le message est transferé uniquement s'il n'a pas déjà été envoyé
		if(!this.visited) {
			synchronized(this) {
				// on passe visited a true
				this.visited = true;
				// on garde en mémorie les données
				this.data = data;
			}
			System.out.println(this.id + " a reçu le message : "+ new String(data));
			
			// On propage le message aux voisins
			this.propagerMessageAuxVoisins();
			
			System.out.println("Fin des envois aux voisins.");
			
		}
	}
	
	/**
	 * Propage le message donné en paramètre aux voisins du site actuel.
	 * @param donnees le message a transferer
	 * @throws TransfertException 
	 */
	public void propagerMessageAuxVoisins() throws RemoteException, TransfertException{
		List<Transfert> transferts = new ArrayList<Transfert>();
		
		// Création et lancement des threads de transfert.
		for (int i=0; i < this.voisins.size(); i++) {
			Transfert transf = new Transfert(this.data,this.voisins.get(i));
			transferts.add(transf);
			transf.start();
		}
		
		// On attends la fin des threads.
		for (int i=0; i < transferts.size(); i++) {
			try {
				transferts.get(i).join();
			} catch (InterruptedException e) {
				throw new TransfertException();
			}
		}
		
	}
	
	/**
	 * Réinitialise le graphe afin de le réutiliser.
	 */
	public void reset() throws RemoteException {
		// On réinitialise le site.
		synchronized(this) {
			this.data = null;
			this.visited = false;
		}
		// On réinitialise les autres sites voisins.
		for (SiteItf site : this.voisins) {
			if (site.isVisited())
				site.reset();
		}
	}


}
