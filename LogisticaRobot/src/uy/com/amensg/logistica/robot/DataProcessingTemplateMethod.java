package uy.com.amensg.logistica.robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import uy.com.amensg.logistica.robot.util.Configuration;

public abstract class DataProcessingTemplateMethod {

	protected Connection connection;
	
	public void doProcessing(Collection<String> data) {
		try {
			this.init();
			
			Collection<Object> parsedParameters = this.parseParameters(data);
			
			if (parsedParameters.size() > 0) {
				if (this.checkExistence(parsedParameters)) {
					PreparedStatement preparedStatementUpdate = this.prepareStatementForUpdate(parsedParameters);
					
					this.insert(preparedStatementUpdate);
				} else {
					PreparedStatement preparedStatementInsert = this.prepareStatementForInsert(parsedParameters);
					
					this.update(preparedStatementInsert);
				}
			}
			
			this.end();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void init() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		
		this.connection = DriverManager.getConnection(
			Configuration.getInstance().getProperty("db.connectionURL"), 
			Configuration.getInstance().getProperty("db.connectionUser"), 
			Configuration.getInstance().getProperty("db.connectionPassword")
		);
		
		this.connection.setAutoCommit(false);
	}
	
	protected abstract Collection<Object> parseParameters(Collection<String> data);
	protected abstract boolean checkExistence(Collection<Object> parsedParameters) throws SQLException;
	
	protected Long checkExistenceWithId(Collection<Object> parsedParameters) throws SQLException {
		return null;
	}
	
	protected abstract PreparedStatement prepareStatementForInsert(Collection<Object> parsedParameters) throws SQLException;
	protected abstract PreparedStatement prepareStatementForUpdate(Collection<Object> parsedParameters) throws SQLException;
	
	protected void insert(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.executeUpdate();
		
		this.connection.commit();
	}
	
	protected void update(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.executeUpdate();
		
		this.connection.commit();
	}
	
	protected void end() throws SQLException {
		this.connection.close();
	}
}