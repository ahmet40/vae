package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Utilisateur;
import src.Main;

public class SQLUser {

    private SQLUser(){}
    
    public static boolean userConnection(String pseudo, String password){
        Utilisateur utilisateur = Main.getInstance().getUtilisateur(pseudo, password);
        if(utilisateur != null) {
            Main.getInstance().setPersonneConnecte(utilisateur);
            SQLUser.changeActiveUt(pseudo, password, 'O');
        }
        return utilisateur != null;
    }

    public static String admOrUser(Main main,String pseudo,String passowrd){
        return main.getUtilisateur(pseudo, passowrd).getRole();
    }

    public static Boolean userInscription(String pseudo, String mail, String password){
        if(pseudo.length() == 0 || mail.length() == 0 || password.length() == 0) return null;
        if(pseudo.contains(" ") || mail.contains(" ") || password.contains(" ")) return null;
        try{
            Main main = Main.getInstance();
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT pseudoUt,emailUT FROM UTILISATEUR where pseudoUt=? or emailUt=?"
            );
            preparedStatement.setString(1, pseudo);
            preparedStatement.setString(2, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                preparedStatement = main.getSqlConnect().prepareStatement(
                    "INSERT INTO UTILISATEUR (idUt,pseudoUt,emailUT,mdpUt,activeUt,idRole) values (?,?,?,?,?,?)"
                    );
                int maxId = maxUserId();
                preparedStatement.setInt(1, maxId);
                preparedStatement.setString(2, pseudo);
                preparedStatement.setString(3, mail);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, "O");
                preparedStatement.setInt(6,2);
                preparedStatement.executeUpdate();
                Utilisateur utilisateur = new Utilisateur(maxId, pseudo, mail, password, 'O', "Utilisateur");
                main.addUtilisateur(utilisateur);
                main.setPersonneConnecte(utilisateur);
                return true;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return false;
    }
    
    private static int maxUserId(){
        try{
            final PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
            "SELECT max(idUt) FROM UTILISATEUR"
            );
            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("max(idUt)")+1;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return 1;
    }

    public static List<Utilisateur> chargerUtilisateurs(){
        List<Utilisateur> listeUser = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM UTILISATEUR NATURAL JOIN ROLE ORDER BY idUt"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("idUt");
                String pseudo=resultSet.getString("pseudoUt");
                String email = resultSet.getString("emailUT");
                String mdp = resultSet.getString("mdpUt");
                char active = resultSet.getString("activeUt").charAt(0);
                String role = resultSet.getString("nomRole");
                listeUser.add(new Utilisateur(id,pseudo,email,mdp,active,role));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeUser;
    }

    public static List<Utilisateur> rechercheParText(Main main,String text){
        List<Utilisateur> listeUser = new ArrayList<>();
        for (Utilisateur utilisateur : main.getUtilisateurs()) {
            if(utilisateur.getPseudoUt().startsWith(text) || utilisateur.getEmailUt().startsWith(text)) listeUser.add(utilisateur);
        }
        return listeUser;
    }

    public static void deleteUserEncherir(Main main, Utilisateur u){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "DELETE FROM ENCHERIR WHERE idUt=?"
            );
            
            preparedStatement.setInt(1, u.getIdUt());
            int lignesSupprimees = preparedStatement.executeUpdate();
            
            if (lignesSupprimees > 0) {
                System.out.println("L'utilisateur a été supprimé avec succès.");
                for(int i = 0; i < lignesSupprimees; ++i){
                    main.getEncherissements().removeIf( v -> v.getIdUt() == u.getIdUt() );
                }
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé.");
            }
            
            // Fermer la connexion et les ressources
            preparedStatement.close();
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        
    }

    public static void deleteUser(Main main, Utilisateur u){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "DELETE FROM UTILISATEUR WHERE pseudoUt=? and emailUT =?"
            );
            
            preparedStatement.setString(1, u.getPseudoUt());
            preparedStatement.setString(2, u.getEmailUt());
            int lignesSupprimees = preparedStatement.executeUpdate();
            
            if (lignesSupprimees > 0) {
                System.out.println("L'utilisateur a été supprimé avec succès.");
                for(int i = 0; i < lignesSupprimees; ++i){
                    main.getUtilisateurs().removeIf( u2 -> u2.equals(u) );
                }
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé.");
            }
            
            // Fermer la connexion et les ressources
            preparedStatement.close();
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        
    }


    public static void changeActiveUt(String pseudo, String password, char active){
        try{
            Main main = Main.getInstance();
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT pseudoUt,emailUT,mdpUT FROM UTILISATEUR WHERE pseudoUt=?"
            );
            preparedStatement.setString(1, pseudo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                preparedStatement = main.getSqlConnect().prepareStatement(
                    "UPDATE UTILISATEUR SET activeut=? WHERE pseudoUt=?"
                );
                preparedStatement.setString(1, active+"");
                preparedStatement.setString(2, pseudo);
                preparedStatement.executeUpdate();
                main.getUtilisateur(pseudo, password).setActiveUt(active);
                preparedStatement.close();
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    
    }


}
