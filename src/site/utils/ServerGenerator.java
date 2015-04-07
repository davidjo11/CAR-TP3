package site.utils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
			pw = new PrintWriter("../launchServers.sh");
			int aux = 0;
			//Liste qui contiendra les serveurs déjà créés (on ne peut pas mettre un serveur comme serveur père d'un autre s'il n'a pas encore été créé.
			List<Integer> alreadyCreatedServers = new ArrayList<Integer>();
			Integer[] auxPorts;
			for(int i = 0;i<n; i++){
				content += "xterm "
						+ "-geometry \"50x50+0+"+(40*i)+"\" "
						+ "-title \"Serveur "+ (i+1) +" (port "+ports[i]+")\" "
						+ "-e \"cd bin;java -classpath ../lib:. site."+ type +".serveur.Serveur "+ ports[i] +" \\\"Site "+ (i+1) +"\\\" ";

				//Récupération des serveurs déjà créés.
				auxPorts = new Integer[alreadyCreatedServers.size()];
				auxPorts = alreadyCreatedServers.toArray(auxPorts);

				if(i != 0)
					//Pour un graphe, on sépare les ports par les virgules.
					if(type.equals("graphe")){
						//On prend un nombre de voisins au hasard.
						aux = new Random().nextInt(alreadyCreatedServers.size());
						if(aux == 0){
							aux = 1;
						}
						for(int j=0;j<aux;j++){
							int aux2 = new Random().nextInt(alreadyCreatedServers.size());
							if(j!=0)
								content += "," + alreadyCreatedServers.get(aux2);
							else content += alreadyCreatedServers.get(aux2);
							alreadyCreatedServers.remove(aux2);
						}
						//On replace les serveurs qui plausiblement été retirés de la liste 2 lignes plus haut.
						alreadyCreatedServers = new ArrayList<Integer>(Arrays.asList(auxPorts));
					}
				//Pour un arbre, on met un port ou rien.
					else {
						aux = new Random().nextInt(alreadyCreatedServers.size());
						content += alreadyCreatedServers.get(aux);
					}
				//On ajoute le serveur qui vient d'être créé.
				alreadyCreatedServers.add(ports[i]);
				
				//fin de l'option -e
				content += "\"";
				
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
