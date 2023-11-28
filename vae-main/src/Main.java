package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import controleurs.MesEncheresControleur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.mysql.SQLCategorie;
import modele.mysql.SQLConnect;
import modele.mysql.SQLEncherir;
import modele.mysql.SQLObjet;
import modele.mysql.SQLPhoto;
import modele.mysql.SQLUser;
import modele.mysql.SQLVente;
import modele.caches.Categorie;
import modele.caches.Encherir;
import modele.caches.Objet;
import modele.caches.Photo;
import modele.caches.Utilisateur;
import modele.caches.Vente;

public class Main extends Application{

    private static Main INSTANCE;
    public static Main getInstance(){ return INSTANCE; }

    private BorderPane panelCentral;
    private SQLConnect sqlConnect;

    private Accueil accueil;
    private NavigationBar navigationBar;
    private Inscription inscription;
    private Connexion connexion;
    private NousContacter nousContacter;
    private Enchere enchere;
    private NavigationBarAdmin navigationBarAdmin;
    private PageLesUtilisateurs pageLesUtilisateurs;
    private Annonce annonce;
    private MesEncheres mesEncheres;
    private EncherirObjet encherirObjet;
    private AccueilAdmin accueilAdmin;
    private PageLesRapports pageLesRapports;

    private List<Utilisateur> utilisateurs;
    private List<Vente> ventes;
    private List<Objet> objets;
    private List<Photo> photos;
    private List<Encherir> encherissement;
    private List<Categorie> categories;
    private List<String> nomVente;

    private Utilisateur personneConnecte;

    @Override
    public void start(Stage stage) throws Exception {
        
        BorderPane pane = new BorderPane(this.panelCentral);
        Scene scene = new Scene(pane, 1200, 750);
        stage.setScene(scene);
        stage.setTitle("Vente aux enchères");
        stage.setResizable(false);
        stage.show();

    }

    @Override
    public void init() throws Exception {

        INSTANCE = this;

        try {
            this.sqlConnect = new SQLConnect();
            File file = new File("./LoginBDD.yml");
            String serverName = "";
            String bdName = "";
            String userLogin = "";
            String userPassword = "";

            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(line.contains("serverName:")) serverName = line.split(":")[1].replace(" ", "");
                    else if(line.contains("bdName:")) bdName = line.split(":")[1].replace(" ", "");
                    else if(line.contains("userLogin:")) userLogin = line.split(":")[1].replace(" ", "");
                    else if(line.contains("userPassword:")) userPassword = line.split(":")[1].replace(" ", "");
                }

                this.sqlConnect.connect(serverName, bdName, userLogin, userPassword);

                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException ignored){
            System.out.println("Driver MySQL non trouvé !");
            System.exit(1);
        }catch (Exception ignored){
            System.out.println("Impossibilité à l'application de se connecter à la base de donnée !");
            System.exit(1);
        }

        this.utilisateurs = SQLUser.chargerUtilisateurs();
        this.ventes = SQLVente.chargerVentes();
        this.photos = SQLPhoto.chargerPhotos();
        this.encherissement = SQLEncherir.chargerEncherir();
        this.objets = SQLObjet.chargerObjets();
        this.categories = SQLCategorie.chargerCategorie();
        this.nomVente = new ArrayList<>();
        for (Vente vente : this.ventes) {
            this.nomVente.add(this.getObjet(vente.getIdOb()).getNomOb());
        }

        this.personneConnecte = null;

        SQLVente.ventesBientotExpiree(this.ventes);

        this.accueil = new Accueil();
        this.navigationBar = new NavigationBar();
        this.inscription = new Inscription();
        this.connexion = new Connexion();
        this.nousContacter = new NousContacter(this);
        this.enchere = new Enchere();
        this.navigationBarAdmin = new NavigationBarAdmin(this);
        this.pageLesUtilisateurs = new PageLesUtilisateurs(this);
        this.annonce = new Annonce();
        this.mesEncheres = new MesEncheres();
        this.encherirObjet = new EncherirObjet();
        this.accueilAdmin = new AccueilAdmin();
        this.pageLesRapports = new PageLesRapports(this);

        this.panelCentral = new BorderPane();
        this.panelCentral.setTop(this.navigationBar);
        this.panelCentral.setCenter(this.accueil);
        
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(this.personneConnecte != null){
            SQLUser.changeActiveUt(personneConnecte.getPseudoUt(), personneConnecte.getMdpUt(), 'N');
        }
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    public void mettreAuCentre(Pane pane){
        controleurs.NousContacter.connecte = false;
        MesEncheresControleur.connecte = false;
        this.panelCentral.setCenter(pane);
    }

    public Accueil getAccueil() {
        return this.accueil;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }

    public Inscription getInscription() {
        return this.inscription;
    }

    public Connexion getConnexion() {
        return this.connexion;
    }

    public SQLConnect getSqlConnect() {
        return this.sqlConnect;
    }

    public List<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }

    public List<Vente> getVentes() {
        return this.ventes;
    }

    public List<Objet> getObjets() {
        return this.objets;
    }

    public List<Photo> getPhotos() {
        return this.photos;
    }

    public List<Photo> getPhotos(int idOb) {
        List<Photo> listePhoto = new ArrayList<>();
        for (Photo photo : this.photos) {
            if(photo.getIdOb() == idOb) listePhoto.add(photo);
        }
        return listePhoto;
    }

    public List<Encherir> getEncherissements() {
        return this.encherissement;
    }

    public Encherir getEncherissement(int idVe){
        for (Encherir encherissement : this.encherissement) {
            if(encherissement.getIdVe() == idVe) return encherissement;
        }
        return null;
    }

    public Vente getVente(int idVe){
        for (Vente vente : this.ventes) {
            if(vente.getIdVe() == idVe) return vente;
        }
        return null;
    }

    public Vente getVente(String nomObjet){
        for (Vente vente : this.ventes) {
            for (Objet objet : this.objets) {
                if(vente.getIdOb() == objet.getIdOb() && objet.getNomOb().equals(nomObjet)) return vente; 
            }
        }
        return null;
    }

    public Objet getObjet(int idOb){
        for (Objet objet : this.objets) {
            if(objet.getIdOb() == idOb) return objet;
        }
        return null;
    }

    public boolean pseudoValide(String pseudo){
        for (Utilisateur utilisateur : this.utilisateurs) {
            if(utilisateur.getPseudoUt().equals(pseudo)) return false;
        }
        return true;
    }

    public Enchere getEnchere() {
        return this.enchere;
    }

    public boolean emailValide(String email){
        for (Utilisateur utilisateur : this.utilisateurs) {
            if(utilisateur.getEmailUt().equals(email)) return false;
        }
        return true;
    }

    public Photo getPhoto(int idOb){
        for (Photo photo : this.photos) {
            if(photo.getIdOb() == idOb) return photo;
        }
        return null;
    }

    public PageLesUtilisateurs getPageLesUtilisateurs() {
        return this.pageLesUtilisateurs;
    }

    public Encherir getMaxMontant(int idVe){
        long montant = -1;
        Encherir encherir = null;
        for (Encherir encherissement : this.encherissement) {
            if(encherissement.getIdVe() == idVe){
                if(encherir == null || montant < encherissement.getMontant()){
                    montant = encherissement.getMontant();
                    encherir = encherissement;
                }
            }
        }
        return encherir;
    }

    public Annonce getAnnonce() {
        return this.annonce;
    }

    public Utilisateur getUtilisateur(String pseudo, String password){
        for (Utilisateur utilisateur : this.utilisateurs) {
            if(utilisateur.getPseudoUt().equals(pseudo) && utilisateur.getMdpUt().equals(password)) return utilisateur;
        }
        return null;
    }

    public void addUtilisateur(Utilisateur utilisateur){
        this.utilisateurs.add(utilisateur);
    }

    public NousContacter getNousContacter() {
        return this.nousContacter;
    }

    public void popUpSurDuChoix(Vente vente, Objet objet){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette enchère ?");
        alert.setTitle("Attention !");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.isPresent() && optional.get().equals(ButtonType.OK)){
            Main main = Main.getInstance();
            SQLVente.supprimerEnchere(main, vente);
            SQLVente.supprimerVente(main, vente);
            SQLPhoto.supprimerLesPhotos(main, objet.getIdOb());
            SQLObjet.supprimerObjet(main, objet);
            Platform.runLater(() -> {
                main.getAccueil().majAffichage();
                main.getAnnonce().majAffichage();
                main.getAccueilAdmin().majAffichage();
                main.getAccueil().isAdmin();
                main.getAnnonce().isAdmin();
                main.getAccueilAdmin().isAdmin();
                main.getMesEncheres().set();
            });
        }
    }

    public void userIsConnected(String pseudo, String password){
        Utilisateur utilisateur = this.getUtilisateur(pseudo, password);
        utilisateur.setActiveUt('O');
        this.personneConnecte = utilisateur;
    }

    public MesEncheres getMesEncheres() {
        return this.mesEncheres;
    }

    public boolean isConnected(){
        return this.personneConnecte != null;
    }

    public AccueilAdmin getAccueilAdmin() {
        return this.accueilAdmin;
    }

    public List<Categorie> getCategories() {
        return this.categories;
    }

    public Categorie getCategorie(String nom){
        for(Categorie categorie : this.categories){
            if(categorie.getNomCat().equals(nom)) return categorie;
        }
        return null;
    }

    public List<Vente> getVentePasse(int idUt){
        List<Vente> listeVente = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime finVe;
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Vente vente : this.ventes) {
            finVe = LocalDateTime.parse(vente.getFinVe(), formatter);
            if(this.getObjet(vente.getIdOb()).getIdUt() == idUt && finVe.isBefore(currentDateTime)) listeVente.add(vente);
        }
        return listeVente;
    }

    public List<Vente> getVenteEnCours(int idUt){
        List<Vente> listeVente = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime debutVe;
        LocalDateTime finVe;
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Vente vente : this.ventes) {
            debutVe = LocalDateTime.parse(vente.getDebutVe(), formatter);
            finVe = LocalDateTime.parse(vente.getFinVe(), formatter);
            if(this.getObjet(vente.getIdOb()).getIdUt() == idUt && debutVe.isBefore(currentDateTime) && 
            finVe.isAfter(currentDateTime)) listeVente.add(vente);
        }
        return listeVente;
    }

    public Utilisateur getUtilisateure(int idUt){
        for (Utilisateur utilisateur : this.utilisateurs) {
            if(utilisateur.getIdUt() == idUt) return utilisateur;
        }
        return null;
    }

    public List<Vente> getVenteEncheri(int idUt){
        List<Vente> listeVente = new ArrayList<>();
        for (Vente vente : this.ventes) {
            for(Encherir encherissement : this.encherissement){
                if(encherissement.getIdUt() == idUt && encherissement.getIdVe() == vente.getIdVe()){
                    listeVente.add(vente);
                }
            }
        }
        return listeVente;
    }

    public List<String> getNomVente() {
        return this.nomVente;
    }

    public EncherirObjet getEncherirObjet() {
        return this.encherirObjet;
    }

    public Utilisateur getUtilisateur(int idOb){
        for (Objet objet : this.objets) {
            if(objet.getIdOb() == idOb){
                for(Utilisateur utilisateur : this.utilisateurs){
                    if(objet.getIdUt() == utilisateur.getIdUt()) return utilisateur;
                }
            }
        }
        return null;
    }

    public NavigationBarAdmin getNavigationBarAdmin() {
        return this.navigationBarAdmin;
    }

    public PageLesRapports getPageLesRapports() {
        return this.pageLesRapports;
    }

    public BorderPane getPanelCentral() {
        return this.panelCentral;
    }

    public Utilisateur getPersonneConnecte() {
        return this.personneConnecte;
    }

    public void setPersonneConnecte(Utilisateur personneConnecte) {
        this.personneConnecte = personneConnecte;
    }

    public void afficherPopUpQuitter(){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment quitter l'application ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void pageAdmin(){
        this.panelCentral.setTop(this.navigationBarAdmin);
        this.panelCentral.setCenter(this.accueilAdmin);
    }


    public void afficherPopUpDeconnexion(){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment vous déconnecter ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            SQLUser.changeActiveUt(this.personneConnecte.getPseudoUt(), this.personneConnecte.getMdpUt(), 'N');
            this.personneConnecte = null;
            this.navigationBar.removeUserConnexion();
            if(this.panelCentral.getTop().equals(this.navigationBarAdmin)){
                this.navigationBarAdmin.removeLastChildren();
                this.panelCentral.setTop(this.navigationBar);
            }
            this.panelCentral.setCenter(this.accueil);
        }
    }

    public void afficherErreur(String erreur){
        Alert alert = new Alert(AlertType.ERROR, "Une erreur est survenu : " + erreur);
        alert.showAndWait();
    }

}