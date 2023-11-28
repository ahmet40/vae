package modele.caches;

public class Utilisateur{
    
    private int idUt;
    private String pseudoUt;
    private String emailUt;
    private String mdpUt;
    private char activeUt;
    private String role;

    public Utilisateur(int idUd, String pseudoUt, String emailUt, String mdpUt, char activeUt, String role){
        this.idUt = idUd;
        this.pseudoUt = pseudoUt;
        this.emailUt = emailUt;
        this.mdpUt = mdpUt;
        this.activeUt = activeUt;
        this.role = role;
    }

    public int getIdUt() {
        return this.idUt;
    }

    public String getPseudoUt() {
        return this.pseudoUt;
    }

    public String getEmailUt() {
        return this.emailUt;
    }

    public String getMdpUt() {
        return this.mdpUt;
    }

    public char getActiveUt() {
        return this.activeUt;
    }

    public String getRole() {
        return this.role;
    }

    public void setPseudoUt(String pseudoUt) {
        this.pseudoUt = pseudoUt;
    }

    public void setEmailUt(String emailUt) {
        this.emailUt = emailUt;
    }
    
    public void setMdpUt(String mdpUt) {
        this.mdpUt = mdpUt;
    }

    public void setActiveUt(char activeUt) {
        this.activeUt = activeUt;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}