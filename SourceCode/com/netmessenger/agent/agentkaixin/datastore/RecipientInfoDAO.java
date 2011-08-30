package com.netmessenger.agent.agentkaixin.datastore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;


public class RecipientInfoDAO implements IRecipientInfoDAO{
	
	private Connection conn;
	private Statement stat;
	public static String TABLENAME = "kaixin_recipient";
	public RecipientInfoDAO(Connection conn) throws Exception{
			this.conn = conn;
			stat = conn.createStatement();
	}
	
	@Override
	public boolean isExist(RecipientInfo recipientInfo) {
		
		try {
			PreparedStatement pre = conn.prepareStatement("select * from " + TABLENAME + " where homepage = ?");
			pre.setString(1, recipientInfo.getHomePage());
			ResultSet rs = pre.executeQuery();
			Boolean exist = rs.next();
			rs.close();
			if(exist) return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void add(RecipientInfo recipientInfo) {

		try {
			PreparedStatement prep = conn.prepareStatement("insert into " + TABLENAME + "(name, age, job, gender, homepage) values(?,?,?,?,?);");
			prep.setString(1, recipientInfo.getName());
			prep.setString(2, recipientInfo.getAge().toString());
			prep.setString(3, recipientInfo.getJob().toString());
			prep.setString(4, recipientInfo.getGender().toString());
			prep.setString(5, recipientInfo.getHomePage());
			prep.execute();
			prep.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public int countRecipients() {
		try {
			ResultSet rs = stat.executeQuery("select count(*) from " + TABLENAME);
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<RecipientInfo> findAll(){
		try{
			ResultSet rs = stat.executeQuery("select * from " + TABLENAME);
			LinkedList<RecipientInfo> list = new LinkedList<RecipientInfo>();
			
			while(rs.next())
			{
				RecipientInfo ri = new RecipientInfo();
				
				ri.setName(rs.getString("name"));
				ri.setAge(RecipientAge.parse(rs.getString("age")));
				ri.setJob(RecipientJob.parse(rs.getString("job")));
				ri.setGender(RecipientGender.parse(rs.getString("gender")));
				ri.setHomePage(rs.getString("homepage"));
				
				list.add(ri);
			}
			rs.close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void save() {
		//do nothing
	}

	@Override
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	


}
