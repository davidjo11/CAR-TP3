package site;

public class Transfert extends Thread{

	private SiteImpl pere, fils;
	
	public Transfert(SiteImpl pere, SiteImpl fils){
		this.pere = pere;
		this.fils = fils;
	}
	
	public void run(){
		try{
			this.fils.setData(this.pere.getData());
			System.out.println(this.pere.getId() + " vers son fils "+this.fils.getId()+" de "+new String(this.pere.getData())+".");
			this.fils.propager();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
