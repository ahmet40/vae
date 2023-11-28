package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Encherir;
import src.Main;

public class SQLEncherir {
    
    private SQLEncherir(){}

    public static List<Encherir> chargerEncherir(){
        List<Encherir> listeEncherir = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM ENCHERIR ORDER BY idVe"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idUt = resultSet.getInt("idUt");
                int idVe = resultSet.getInt("idVe");
                String dateHeure = resultSet.getString("dateHeure");
                long montant = resultSet.getLong("montant");
                listeEncherir.add(new Encherir(idUt,idVe,dateHeure,montant));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeEncherir;
    }

    public static void insererEnrechissement(Main main, Encherir encherir){

        try{
            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "INSERT INTO ENCHERIR (idUt,idVe,dateHeure,montant) values (?,?,?,?)"
            );
            ps.setInt(1, encherir.getIdUt());
            ps.setInt(2, encherir.getIdVe());
            ps.setString(3, encherir.getDateHeure());
            ps.setLong(4, encherir.getMontant());
            ps.executeUpdate();
            ps.close();
            main.getEncherissements().add(encherir);
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }


}
