package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Categorie;
import src.Main;

public class SQLCategorie {
    
    private SQLCategorie(){}

    public static List<Categorie> chargerCategorie(){
        List<Categorie> listeCat = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM CATEGORIE ORDER BY idCat"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("idCat");
                String cat=resultSet.getString("nomCat");
                listeCat.add(new Categorie(id,cat));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeCat;
    }

    public static Categorie rechercheParId(Main main, int id){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT * from CATEGORIE WHERE idCat=?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                String nomCat=rs.getString("nomCat");
                Categorie c=new Categorie(id,nomCat);
                preparedStatement.close();
                return c;
            }

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return null;
    }

    public static Categorie rechercheParNom(Main main, String nom){
        return main.getCategorie(nom);
    }

}
