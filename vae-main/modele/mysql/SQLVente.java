package modele.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modele.caches.Objet;
import modele.caches.Vente;
import src.Main;
import src.TempsRestant;
import src.TrierParTempsRestant;

public class SQLVente {

    private SQLVente(){}
    
    public static List<String> annoncesBientotExpirees(){
        List<String> annonceBientotExpiree = new ArrayList<>();

        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
            "SELECT prixMin, CONCAT(FLOOR(TIMESTAMPDIFF(HOUR, NOW(), finVe) / 24), ':', LPAD(MOD(TIMESTAMPDIFF(HOUR, NOW(), finVe), 24), 2, '0'), ':', LPAD(MOD(TIMESTAMPDIFF(MINUTE, NOW(), finVe), 60), 2, '0'), ':', LPAD(MOD(TIMESTAMPDIFF(SECOND, NOW(), finVe), 60), 2, '0')) AS finve FROM VENTE WHERE idSt = ? AND debutVe <= NOW() and finVe >= NOW()"
            );
            preparedStatement.setInt(1, 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                annonceBientotExpiree.add( 
                    resultSet.getInt("prixmin")+":"+
                    resultSet.getString("finve")
                );
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }

        return annonceBientotExpiree;

    }

    public static List<Vente> chargerVentes(){
        List<Vente> listeVente = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM VENTE ORDER BY idVe"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idVe = resultSet.getInt("idVe");
                long prixBase = resultSet.getLong("prixBase");
                long prixMin = resultSet.getLong("prixMin");
                String debutVe = resultSet.getString("debutVe");
                String finVe = resultSet.getString("finVe");
                int idOb = resultSet.getInt("idOb");
                byte idSt = resultSet.getByte("idSt");
                listeVente.add(new Vente(idVe, prixBase, prixMin, debutVe, finVe, idOb, idSt));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeVente;
    }

    public static List<Vente> rechercheVenteParObjet(Main main, Objet ob){
        List<Vente> listeVente = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "SELECT * FROM VENTE WHERE idOb=?"
            );
            preparedStatement.setInt(1, ob.getIdOb());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idVe = resultSet.getInt("idVe");
                long prixBase = resultSet.getLong("prixBase");
                long prixMin = resultSet.getLong("prixMin");
                String debutVe = resultSet.getString("debutVe");
                String finVe = resultSet.getString("finVe");
                Objet o=SQLObjet.rechercheParIdObjet(main, ob.getIdOb());
                byte idSt = resultSet.getByte("idSt");
                listeVente.add(new Vente(idVe, prixBase, prixMin, debutVe, finVe, o.getIdOb(), idSt));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listeVente;
    }


    public static List<TempsRestant> ventesBientotExpiree(List<Vente> ventes){

        List<TempsRestant> listeVentes = new ArrayList<>();

        LocalDateTime currentDateTime;
        long dateRestant;
        long seconds;
        long minutes;
        long hours;

        DateTimeFormatter formatter;
        LocalDateTime debutVe;
        LocalDateTime finVe;
        
        for(Vente vente : ventes){

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            debutVe = LocalDateTime.parse(vente.getDebutVe(), formatter);
            finVe = LocalDateTime.parse(vente.getFinVe(), formatter);
            currentDateTime = LocalDateTime.now();

            if(debutVe.isAfter(finVe) || debutVe.isAfter(currentDateTime) || finVe.isBefore(currentDateTime)) continue;
            
            dateRestant = Duration.between(currentDateTime, finVe).toMillis();
            
            try {
                seconds = dateRestant / 1000;
                minutes = seconds / 60;
                hours = minutes / 60;
                seconds %= 60;
                minutes %= 60;

                listeVentes.add(new TempsRestant(vente.getIdVe(), String.format("%02d:%02d:%02d", hours, minutes, seconds)));
            } catch (Exception e) {
                Main.getInstance().afficherErreur(e.getMessage());
            }

        }

        Collections.sort(listeVentes, new TrierParTempsRestant());

        return listeVentes;

    }

    public static String getTempsRestantVente(Vente vente){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime debutVe = LocalDateTime.parse(vente.getDebutVe(), formatter);
        LocalDateTime finVe = LocalDateTime.parse(vente.getFinVe(), formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        if(debutVe.isAfter(finVe) || debutVe.isAfter(currentDateTime) || finVe.isBefore(currentDateTime)) return null;
            
        long dateRestant = Duration.between(currentDateTime, finVe).toMillis();
            
        try {
            long seconds = dateRestant / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            seconds %= 60;
            minutes %= 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } catch (Exception e) {
            Main.getInstance().afficherErreur(e.getMessage());
        }

        return null;
    }

    public static boolean mettreEnVente(Main main,Vente v){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "INSERT INTO VENTE (idVe,prixBase,prixMin,debutVe,finVe,idSt,idOb) VALUES (?,?,?,?,?,?,?)"
            );
            preparedStatement.setInt(1, maxVenteId(main));
            preparedStatement.setLong(2, v.getPrixBase());
            preparedStatement.setLong(3, v.getPrixMin());
            preparedStatement.setString(4, v.getDebutVe());
            preparedStatement.setString(5, v.getFinVe());
            preparedStatement.setInt(6, v.getIdSt());
            preparedStatement.setInt(7, v.getIdOb());
            preparedStatement.executeUpdate();
            main.getVentes().add(v);
            return true;

        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return false;
    }
    
    public static int maxVenteId(Main main){
        try{
            final PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
            "SELECT max(idVe) FROM VENTE"
            );
            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("max(idVe)")+1;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return 1;
    }

    public static void supprimerVente(Main main, Vente v){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "DELETE FROM VENTE WHERE idVe=?"
            );
            preparedStatement.setInt(1, v.getIdVe());
            preparedStatement.executeUpdate();
            main.getVentes().remove(v);
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }
    public static void supprimerEnchere(Main main, Vente v){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                "DELETE FROM ENCHERIR WHERE idVe=?"
            );
            preparedStatement.setInt(1, v.getIdVe());
            preparedStatement.executeUpdate();
            main.getEncherissements().remove(main.getEncherissement(v.getIdVe()));
            main.getNomVente().remove(main.getObjet(v.getIdOb()).getNomOb());
        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }


}
