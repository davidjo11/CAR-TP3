package site;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteImpl extends UnicastRemoteObject implements SiteItf{

	//voir rmi registry
	private List<SiteItf> children;
	
	private String id;
	
	private SiteItf pere;
	
	private byte[] data;
	
	public SiteImpl() throws RemoteException{
		this.children = new ArrayList<SiteItf>();
	}
	
	public SiteImpl(String id) throws RemoteException{
		this.children = new ArrayList<SiteItf>();
		this.id = id;
	}
	
	public void ajouterFils(SiteItf fils) throws RemoteException{
		this.children.add(fils);
	}
	
	public void setPere(SiteItf pere) throws RemoteException{
		this.pere = pere;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId()throws RemoteException{
		return this.id;
	}
	
	public byte[] getData() throws RemoteException{
		return this.data;
	}
	
	public void setData(byte[] data){
		this.data = data;
	}
	
	public void propager() throws RemoteException{
		try{
			int i = 0;
			Transfert[] tFils = new Transfert[this.children.size()];
			
			Iterator<SiteItf> it = this.children.iterator();
			
			while(it.hasNext())
				tFils[i++] = new Transfert(this, it.next());
			
			for(i=0; i<this.children.size();i++){
				tFils[i].start();
			}
			
			for(i=0; i<this.children.size();i++){
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
			/*SiteImpl root = new SiteImpl();
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
			root.propager();*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
