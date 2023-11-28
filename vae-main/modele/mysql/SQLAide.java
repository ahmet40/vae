package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Aide;
import src.Main;

public class SQLAide {

    private SQLAide(){}

    public static boolean insererAide(Main main, Aide aide) {
        try {
            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "INSERT INTO AIDE (id, nom, prenom, email, objet, message) values (?,?,?,?,?,?)"
            );
            ps.setInt(1, aide.getId());
            ps.setString(2, aide.getNom());
            ps.setString(3, aide.getPrenom());
            ps.setString(4, aide.getEmail());
            ps.setString(5, aide.getObjet());
            ps.setString(6, aide.getMessage());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            Main.getInstance().afficherErreur(e.getMessage());
            return false;
        }
    }

    public static List<Aide> chargerAllAide(Main main) {
        List<Aide> listeAide = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT * FROM AIDE"
            );
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String objet = rs.getString("objet");
                String message = rs.getString("message");
                Aide aide = new Aide(id,nom, prenom, email, objet, message);
                listeAide.add(aide);
            }
            rs.close();
        } catch (Exception e) {
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeAide;
    }

    public static int maxAideId(Main main){
        try{
            final PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
            "SELECT max(id) FROM AIDE"
            );
            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("max(id)")+1;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return 1;
    }

    public static void supprimerAide(Main main, Aide aide){
        try{
            final PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "DELETE FROM AIDE WHERE id = ?"
            );
            preparedStatement.setInt(1, aide.getId());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }

}
