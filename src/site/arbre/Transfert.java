package site.arbre;

public class Transfert extends Thread{

	private SiteItf pere, fils;
	
	public Transfert(SiteItf pere, SiteItf fils){
		this.pere = pere;
		this.fils = fils;
	}
	
	public void run(){
		try{
			this.fils.setData(this.pere.getData());
			System.out.println("Transfert de " + this.pere.getId() + " vers son fils: "+this.fils.getId()+" du message:\n"+new String(this.pere.getData())+".");
			this.fils.propager();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
