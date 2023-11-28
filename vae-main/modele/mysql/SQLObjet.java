package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.caches.Categorie;
import modele.caches.Objet;
import modele.caches.Utilisateur;
import src.Main;

public class SQLObjet {

    private SQLObjet(){}
    
    public static List<Objet> chargerObjets(){
        List<Objet> listeObjet = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM OBJET ORDER BY idOb;"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idOb = resultSet.getInt("idOb");
                String nomOb = resultSet.getString("nomOb");
                String descriptionOb = resultSet.getString("descriptionOb");
                int idUt = resultSet.getInt("idUt");
                listeObjet.add(new Objet(idOb,nomOb,descriptionOb,SQLCategorie.rechercheParId(Main.getInstance(), 
                resultSet.getInt("idCat")),idUt));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeObjet;
    }

     public static void supprimerObjet(Main main, Objet obj){
        try{
            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "DELETE FROM OBJET WHERE idOb=?;"
            );
            ps.setInt(1, obj.getIdOb());
            ps.executeUpdate();
            ps.close();
            Main.getInstance().getObjets().remove(obj);
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }

    public static List<Objet> rechercheParUtilisateur(Main main, Utilisateur u){
        try{
            System.out.println("Utilisateur : " + u.getIdUt());

            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "SELECT idOb,nomOb,descriptionOb,idCat,idUt FROM OBJET WHERE idUt=?"
            );
            ps.setInt(1, u.getIdUt());
            ResultSet resultSet = ps.executeQuery();
            List<Objet> listeObjet = new ArrayList<>();
            while(resultSet.next()){
                listeObjet.add(new Objet(
                    resultSet.getInt("idOb"),
                    resultSet.getString("nomOb"),
                    resultSet.getString("descriptionOb"),
                    SQLCategorie.rechercheParId(main, resultSet.getInt("idCat")),
                    u.getIdUt()
                ));
            }
            return listeObjet;
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static int intMaxOb(Main main){
        try{
            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "SELECT max(idOb) FROM OBJET"
            );
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) +1;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return 1;
    }

    public static Objet rechercheParIdObjet(Main main, int id){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT * from OBJET WHERE idOb=?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                int idUt=rs.getInt("idUt");
                int idCat=rs.getInt("idCat");
                Categorie c=SQLCategorie.rechercheParId(main,idCat);
                String descriptionOb=rs.getString("descriptionOb");
                String nomOb=rs.getString("nomOb");
                Objet o=new Objet(id,nomOb,descriptionOb,c,idUt);
                preparedStatement.close();
                return o;
            }

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return null;
    }


    public static int insererObjet(Main main, Objet obj){
        int nvNum = intMaxOb(main);
        try{
            PreparedStatement ps = main.getSqlConnect().prepareStatement(
                "INSERT INTO OBJET (idOb,nomOb,descriptionOb,idcat,idUt) values (?,?,?,?,?)"
            );
            ps.setInt(1, nvNum);
            ps.setString(2, obj.getNomOb());
            ps.setString(3, obj.getDescriptionOb());
            ps.setInt(4, obj.getCategorie().getIdCat());
            ps.setInt(5, obj.getIdUt());
            ps.executeUpdate();
            ps.close();
            main.getObjets().add(obj);

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return nvNum;
    }



    
}
