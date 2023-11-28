package modele.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnect {
    
    private Connection connection;
	private boolean isConnected;
	
    public SQLConnect() throws ClassNotFoundException{
        this.connection = null;
        this.isConnected = false;
		Class.forName("org.mariadb.jdbc.Driver");
	}

	public void connect(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://"+nomServeur+":3306/"+nomBase,nomLogin,motDePasse);
		} catch ( SQLException ex ) {
			System.out.println("Msg : " + ex.getMessage() + ex.getErrorCode());
		}
		this.isConnected = this.connection != null;
	}

	public void close() throws SQLException {
        this.connection.close();
        this.connection = null;
        this.isConnected = false;
	}

    public boolean isConnected() { 
        return this.isConnected;
    }
    
	public Statement createStatement() throws SQLException {
		return this.connection.createStatement();
	}

	public PreparedStatement prepareStatement(String requete) throws SQLException{
		return this.connection.prepareStatement(requete);
	}
}
