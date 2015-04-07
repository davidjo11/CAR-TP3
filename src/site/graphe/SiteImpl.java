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
	//Liste de message contenant l'ensemble des données reçues.
	private List<String> dataList;


	/**
	 * Constructeur pour un site par défaut.
	 * @throws RemoteException
	 */
	public SiteImpl() throws RemoteException{
		super();
		this.voisins = new ArrayList<SiteItf>();
		this.dataList = new ArrayList<String>();
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
		this.dataList = new ArrayList<String>();
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
	 * Ajoute le message reçu par le site actuel à la liste de données.
	 */
	public void setData(byte[] data) throws RemoteException{
		this.dataList.add(new String(data));
	}
	
	/**
	 * Retourne la liste des données reçue par le site.
	 */
	public List<String> getDatas() throws RemoteException{
		return this.dataList;
	}

	/**
	 * Transmet un message à un site.
	 * @param donnees le message a envoyer
	 * @throws TransfertException 
	 */
	public void envoyerMessage(byte[] data) throws RemoteException, TransfertException {
		synchronized(this) {
			// Le message est transferé uniquement s'il n'a pas déjà été envoyé
			if(this.dataList.contains(new String(data))) {
				return;
			}
			// on garde en mémoire les données
			this.dataList.add(new String(data));
		}
		System.out.println(this.id + " a reçu le message : "+ new String(data));
			
		// On propage le message aux voisins
		this.propagerMessageAuxVoisins(data);
			
		System.out.println("Fin des envois aux voisins.");

	}
	
	/**
	 * Propage le message donné en paramètre aux voisins du site actuel.
	 * @param donnees le message a transferer
	 * @throws TransfertException 
	 */
	public void propagerMessageAuxVoisins(byte[] data) throws RemoteException, TransfertException{
		List<Transfert> transferts = new ArrayList<Transfert>();
		
		// Création et lancement des threads de transfert.
		for (int i=0; i < this.voisins.size(); i++) {
			System.out.println("Envoi de "+(new String(data))+" vers "+this.voisins.get(i).getId());
			Transfert transf = new Transfert(data,this.voisins.get(i));
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
	public void reset(byte[] data) throws RemoteException {
		// On réinitialise le site.
		String a_enlever = new String(data);
		synchronized(this) {
			this.dataList.remove(a_enlever);
		// On réinitialise les autres sites voisins.
		for (SiteItf site : this.voisins) {
			if (site.getDatas().contains(a_enlever))
				site.reset(data);
		}
		}
	}


}
