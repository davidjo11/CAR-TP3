package site.utils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerGenerator {

	/*
	 * Paramètres OBLIGATOIRE (ligne de commandes):
	 * 	- le nombre de noeuds
	 * 	- le type de la structure : graphe ou arbre
	 * 	- les numéros des ports (séparés par des virgules et sans espaces)
	 * 
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			//Récupération du nombre de serveur.
			int n = Integer.parseInt(args[0]);

			//Récupération du type de réseau
			String type = "arbre";

			//Récupération des numéros de ports
			String[] portString = args[2].split(",");

			if(portString.length < n){
				Tools.usageServerGenerator("Le nombre de ports n'est pas suffisant: "+ portString.length +".");
				return;
			}
			//Traitement des ports.
			int[] ports = new int[portString.length];
			for(int i=0;i<n;i++){
				ports[i] = Integer.parseInt(portString[i]);
				if(ports[i] == 1099 || ports[i] < 1024){
					Tools.usageServerGenerator("Il n'est pas autorisé d'utiliser le port n° "+ ports[i] +".");
					return;
				}
			}

			if(args[1].equals("-g")){
				type = "graphe";
			}
			else if(!args[1].equals("-a")){
				Tools.usageServerGenerator("Type de la structure inconnnu:"+ args[1] +".");
				return;
			}

			String content = "";

			PrintWriter pw;
			pw = new PrintWriter("../terminaux.sh");
			int aux = 0;
			List<Integer> lp = new ArrayList<Integer>();
			for(int i = 0;i<n; i++){
				content += "xterm "
						+ "-geometry \"50x50+0+"+(40*i)+"\" "
						+ "-title \"Serveur "+ (i+1) +" (port "+ports[i]+")\" "
						+ "-e java -classpath ../lib:. site."+ type +".serveur.Serveur "+ ports[i] +" \"Site "+ (i+1) +"\" ";

				for(int j=0;j<ports.length;j++)
					if(i != j)
						lp.add(ports[j]);

				if(i != 0)
					//Pour un graphe, on sépare les ports par les virgules.
					if(type.equals("graphe")){
						//On prend un nombre de voisins au hasard.
						aux = new Random().nextInt(lp.size());
						for(int j=0;j<aux;j++){
							int aux2 = new Random().nextInt(lp.size());
							if(j!=0)
								content += "," + lp.get(aux2);
							else content += lp.get(aux2);
							lp.remove(aux2);
						}
					}
					//Pour un arbre, on met un port ou rien.
					else {
						aux = new Random().nextInt(lp.size());
						content += lp.get(aux);
					}

				content	+= " &\n";

				content += "sleep 0.05;\n";
			}

			pw.print(content);

			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Tools.usageServerGenerator();
			e.printStackTrace();
		}

	}

}
