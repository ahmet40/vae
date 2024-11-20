# Origine du projet

Ce projet consiste a crée une application de ventes aux encheres capable de concurrencer Ebay dans son DAS. Elle sera coder en java et connecter à la base de donnée locale à l'IUT en jdbc. Pour avoir l'appli focntionnel à la maison il faut changer les valeur du fichier le fichier login.yml et la fichier de connexion.

L'application propose le fait de se connecter en tant que admin ou client. 
    Pour le client : 
        Creation de compte, connexion, encherire sur des objet, acceder a leur description, vendre des objet, encherire sur des objet ...
    Pour l'admin :
        Connexion, voir les client et pouvoir les supprimer, crée un fichier d'archive pour les objet de plus de 2 ans'

# Membre du projet
    DEMARET Sullivan
    BRISSET Leo
    GARDELLE Anthony
    BABA Ahmet
    HANI Selim

# Lancement
 Pour lancer l'application il faut executer le fichier Main.java
    Si il ne se lance pas : problème de fichier javafx
        Il faut changer le vmargs dans le fichier lanch.json qui est dans le .vscode en mettant la route des ficher javafx
        et mettre à jour le setting.json 
