package site.graphe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteImpl extends UnicastRemoteObject implements SiteItf{

	//voir rmi registry
	private List<SiteItf> voisins;

	private String id;

	private boolean visited;

	private byte[] data;

	public SiteImpl() throws RemoteException{
		this.voisins = new ArrayList<SiteItf>();
		this.visited = false;
	}

	public SiteImpl(String id) throws RemoteException{
		this.voisins = new ArrayList<SiteItf>();
		this.visited = false;
		this.id = id;
	}

	public void ajouterVoisin(SiteItf voisin) throws RemoteException{
		this.voisins.add(voisin);
		voisin.ajouterVoisin((SiteItf) this);
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

	public boolean isVisited() throws RemoteException{
		return this.visited;
	}

	public void propager() throws RemoteException{
		try{
			this.visited = true;

			int i = 0;
			Transfert[] tFils = new Transfert[this.voisins.size()];

			Iterator<SiteItf> it = this.voisins.iterator();

			while(it.hasNext()){
				SiteItf s = it.next();
				tFils[i++] = (!s.isVisited()) ? new Transfert(this, s) : null;
			}

			for(i=0; i<this.voisins.size();i++){
				if(tFils[i] != null)
					tFils[i].start();
			}

			for(i=0; i<this.voisins.size();i++){
				if(tFils[i] != null)
					tFils[i].join();
			}
			
			System.out.println("Propagation vers les voisins terminée.");
			
		}catch(NullPointerException e){
			System.out.println(this.id +" n'a aucun voisin.");
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
