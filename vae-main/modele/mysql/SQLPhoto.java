package modele.mysql;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import modele.caches.Objet;
import modele.caches.Photo;
import src.Main;

public class SQLPhoto {

    private SQLPhoto(){}
    
    public static List<Photo> chargerPhotos(){
        List<Photo> listePhoto = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
                "SELECT * FROM PHOTO ORDER BY idOb"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idPh = resultSet.getInt("idPh");
                String titrePh = resultSet.getString("titrePh");
                byte[] imgPh = resultSet.getBytes("imgPh");
                int idOb = resultSet.getInt("idOb");
                listePhoto.add(new Photo(idPh,titrePh,imgPh,idOb));
            }
            resultSet.close();

        }
        catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return listePhoto;
    }

    public static void supprimerLesPhotos(Main main,int idOb){
        try{
            PreparedStatement preparedStatement = main.getSqlConnect().prepareStatement(
                    "DELETE FROM PHOTO WHERE idOb=?;"
            );
            preparedStatement.setInt(1, idOb);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(SQLException e){
            main.afficherErreur(e.getMessage());
        }
    }


    public static void enregistrerImage(String imagePath, Objet objet){
        try{

            byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());

            String sql = "INSERT INTO PHOTO (idPh,titrePh,imgPh,idOb) VALUES (?,?,?,?)";
            PreparedStatement pstmt = Main.getInstance().getSqlConnect().prepareStatement(sql);
            int id = maxPhotoId();
            pstmt.setInt(1, id);
            pstmt.setString(2, id+objet.getNomOb());
            pstmt.setBytes(3, imageBytes);
            pstmt.setInt(4, objet.getIdOb());

            Main.getInstance().getPhotos().add(new Photo(id, id+objet.getNomOb(), imageBytes, objet.getIdOb()));

            // Exécuter la requête d'insertion
            pstmt.executeUpdate();

            // Fermer la connexion et libérer les ressources
            pstmt.close();

            System.out.println("L'image a été enregistrée dans la base de données avec succès.");
        }catch(SQLException | IOException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
    }

    private static int maxPhotoId(){
        try{
            final PreparedStatement preparedStatement = Main.getInstance().getSqlConnect().prepareStatement(
            "SELECT max(idPh) FROM PHOTO"
            );
            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("max(idPh)")+1;
            }
        }catch(SQLException e){
            Main.getInstance().afficherErreur(e.getMessage());
        }
        return 1;
    }

    public static Image avoirImage(Photo photo){
        String outputFilePath = "./images/caches/"+photo.getTitrePh()+".jpg";

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(photo.getImgPh());
            FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Main.getInstance().afficherErreur(e.getMessage());
        }

        // Convertir WritableImage en Image
        Image image = null;

        while (image == null) {
            try {
                image = new Image(outputFilePath);
            } catch (Exception ignored) {}
        }

        new File(image.getUrl()).delete();

        return image;
    }

    public static Image test() throws SQLException, IOException {
        PreparedStatement statement = Main.getInstance().getSqlConnect().prepareStatement("SELECT * FROM PHOTO WHERE idOb=1");
        ResultSet resultSet2 = statement.executeQuery();
        if (resultSet2.next()) {
            String outputFilePath = "./images/caches/" + resultSet2.getInt("idPh") + resultSet2.getString("titrePh") + ".jpg";
            InputStream inputStream = resultSet2.getBinaryStream("imgph");
            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(imageBytes);
            }

            // Convertir WritableImage en Image
            Image image = null;

            while(image == null){
                try{
                    image = new Image(outputFilePath);
                }catch(Exception ignored){}
            }

            return image;
        }
        return null;
    }

}
