package site.graphe;

/**
 * 
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class Transfert extends Thread{

	// Le site qui doit recevoir le message et celui qui l'envoi
	private SiteItf envoyeur;
	// Le message a envoyer
	private byte[] le_message;
	
	/**
	 * Constructeur
	 * @param data le message a envoyer.
	 * @param pere le site source
	 * @param fils le site destination
	 */
	public Transfert(byte[] data,SiteItf pere){
		this.envoyeur = pere;
		this.le_message = data;

	}
	
	/**
	 * Lancement du transfert.
	 */
	public void run(){
		
		try{
			this.envoyeur.envoyerMessage(this.le_message);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
