package site.graphe;

/**
 * Thread de transfert.
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Transfert extends Thread{

	// Le site qui doit recevoir le message et celui qui l'envoi
	private SiteItf recepteur;
	// Le message a envoyer
	private byte[] le_message;
	
	/**
	 * Constructeur
	 * @param data le message a envoyer.
	 * @param pere le site source
	 * @param fils le site destination
	 */
	public Transfert(byte[] data,SiteItf recepteur){
		this.recepteur = recepteur;
		this.le_message = data;

	}
	
	/**
	 * Lancement du transfert.
	 */
	public void run(){
		
		try{
			this.recepteur.envoyerMessage(this.le_message);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
