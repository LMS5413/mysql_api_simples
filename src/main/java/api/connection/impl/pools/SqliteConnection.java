package api.connection.impl.pools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import api.connection.impl.atributes.ConnectionAtributesFiles;
import api.interfaces.connection.IConnection;
import api.models.enums.ConnectionType;
import api.models.utils.Checkers;

public class SqliteConnection extends ConnectionAtributesFiles implements IConnection<ConnectionAtributesFiles>{
	
	private String name;
	public SqliteConnection(String nameConnection , String nameFile) {
		Checkers.validateStringNotNull(nameConnection, "nameConnection");
		Checkers.validateStringNotNull(nameFile, "nameFile");
		this.name = nameConnection;
		String[] nameWiExtension = nameFile.split("\\.");
		
		if(nameWiExtension.length < 2) {
			this.setNameFile(nameFile+".db");
			return;
		}
		this.setNameFile(nameWiExtension[0]+".db");
	}
	
	@Override
	public String getAtributesConnection() {
	
		return "jdbc:sqlite"+getAtributesConnectionFiles();
	}
	@Override
	public Connection openConnection() {
		
		Connection connection=null;
		
		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager.getConnection(getAtributesConnection());
			
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao abrir conexao com o SQLITE");
			e.printStackTrace();
		}
		return connection;
	}

	@Override
	public void closeConnection(Connection connection) {
		
		if(connection != null) {
			
			try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closeResultSet(ResultSet resultSet) {
	
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Erro ao abrir conexao com o SQLITE");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public ConnectionAtributesFiles getAtributes() {
	
		return this;
	}

	@Override
	public ConnectionType getConnectionType() {
		
		return ConnectionType.SQLITE;
	}

	@Override
	public void syncronize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}