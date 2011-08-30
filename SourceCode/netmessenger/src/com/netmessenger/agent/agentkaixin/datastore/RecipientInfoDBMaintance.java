package com.netmessenger.agent.agentkaixin.datastore;

import java.sql.Connection;
import java.sql.Statement;

import com.netmessenger.database.DBConnection;

public class RecipientInfoDBMaintance {
	
	private Connection conn;
	private Statement state;
	String tableName = RecipientInfoDAO.TABLENAME;
	
	public RecipientInfoDBMaintance(Connection conn){
		try {
			this.conn = conn;
			state = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void createTable(){
		try{
		String tableStr = 
		"Create Table " + tableName 
		+ "("
		+ "name nvarchar(50)," + "age nvarchar(50)," + "gender nvarchar(10),"+ "job nvarchar(50)," + "homepage nvarchar(300)"
		+ ");";
		
		state.executeUpdate(tableStr);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void clearTable(){
		try{
			String tableStr = 
			"Drop Table " + tableName +";"; 
			
			state.executeUpdate(tableStr);
			}catch(Exception e){
				
			}
		
	}
	
	public static void main(String[] args) throws Exception{
		DBConnection con = new DBConnection();
		RecipientInfoDBMaintance main = new RecipientInfoDBMaintance(con.getConnection());
		main.createTable();
		System.out.println("table created");
	}
}
