package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Objet;
import src.Main;

public class SQLArchivage {
    
    private SQLArchivage(){}

    public static List<Objet> archivageSur2ans(){
        List<Objet> liste = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM OBJET NATURAL JOIN VENTE NATURAL JOIN STATUT where nomSt='Validée' and finVe <= DATE_SUB(CURDATE(), INTERVAL 2 YEAR);"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idOb = resultSet.getInt("idOb");
                String nomOb = resultSet.getString("nomOb");
                String descriptionOb = resultSet.getString("descriptionOb");
                int idUt = resultSet.getInt("idUt");
                liste.add(new Objet(idOb,nomOb,descriptionOb,SQLCategorie.rechercheParId(Main.getInstance(), resultSet.getInt("idCat")),idUt));
            }
            resultSet.close();
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return liste;
    }


}
