package controleurs;

import src.Enchere;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ChargerFichier  implements EventHandler<MouseEvent> {
    
    private Enchere appli;     
    /**
     * @param p vue du jeu
     */
    public ChargerFichier(Enchere appli) {
        this.appli = appli;
        
    }      /**
     * L'action consiste à ouvrir le gestionnaire de fichier pour que vous puissiez changer de fichier de mots
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(MouseEvent actionEvent) {
        this.appli.ouvrirGestionnaireFichiers();
    }
    
}

