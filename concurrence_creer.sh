xterm -geometry "50x50+0+0" -title "Serveur 1 (port 4001)" -e "cd bin;java -classpath ../lib:. site.graphe.serveur.Serveur 4001 \"Site 1\" " &
sleep 0.05;
xterm -geometry "50x50+0+40" -title "Serveur 2 (port 4002)" -e "cd bin;java -classpath ../lib:. site.graphe.serveur.Serveur 4002 \"Site 2\" 4001" &
sleep 0.05;
xterm -geometry "50x50+0+40" -title "Serveur 3 (port 4003)" -e "cd bin;java -classpath ../lib:. site.graphe.serveur.Serveur 4003 \"Site 3\" 4002" &
sleep 0.05;
xterm -geometry "50x50+0+40" -title "Serveur 4 (port 4004)" -e "cd bin;java -classpath ../lib:. site.graphe.serveur.Serveur 4004 \"Site 4\" 4001,4003" &
sleep 0.05;