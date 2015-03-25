package site;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SiteImpl extends UnicastRemoteObject implements SiteItf{

	private static int cpt = 1;
	
	private int id;
	
	private SiteImpl pere, fils[];
	
	private byte[] data;
	
	public SiteImpl() throws RemoteException{}
	
	public void setFils(SiteImpl[] fils){
		this.fils = fils;
	}
	
	public void setPere(SiteImpl pere){
		this.pere = pere;
	}
	
	public void setId(){
		this.id = cpt++;
	}
	
	public int getId(){
		return this.id;
	}
	
	public byte[] getData(){
		return this.data;
	}
	
	public void setData(byte[] data){
		this.data = data;
	}
	
	public void propager() throws RemoteException{
		try{
			Transfert[] tFils = new Transfert[this.fils.length];
			
			for(int i=0; i<this.fils.length;i++){
				tFils[i] = new Transfert(this, this.fils[i]);
			}
			
			for(int i=0; i<this.fils.length;i++){
				tFils[i].start();
			}
			
			for(int i=0; i<this.fils.length;i++){
				tFils[i].join();
			}
			System.out.println("Propagation vers les fils terminée.");
		}catch(NullPointerException e){
			System.out.println(this.id +" n'a aucun fils.");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		try{
			SiteImpl root = new SiteImpl();
			root.setId();
			
			SiteImpl s2 = new SiteImpl();
			s2.setId();
			
			SiteImpl s3 = new SiteImpl();
			s3.setId();
			
			SiteImpl s4 = new SiteImpl();
			s4.setId();
			
			SiteImpl s5 = new SiteImpl();
			s5.setId();
			
			SiteImpl s6 = new SiteImpl();
			s6.setId();
			
			s5.setFils(new SiteImpl[]{s6});
			
			s2.setFils(new SiteImpl[]{s3, s4});
			
			root.setFils(new SiteImpl[]{s2, s5});
			
			root.setData("le fameux message".getBytes());
			root.propager();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
