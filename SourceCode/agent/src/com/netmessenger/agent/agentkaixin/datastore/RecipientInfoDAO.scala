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


class RecipientInfoDAO(conn : Connection) extends IRecipientInfoDAO{
	
	override def isExist( recipientInfo:RecipientInfo):Boolean = {
		
		try {
			val pre = conn.prepareStatement("select * from " + RecipientInfoDBMaintance.TABLENAME + " where homepage = ?");
			pre.setString(1, recipientInfo.homePage);
			val rs = pre.executeQuery();
			val exist = rs.next();
			rs.close();
			if(exist) return true;
			return false;
		} catch {
		  case e: Exception => throw new RuntimeException(e);
		}
	}
	
	
	override def add( recipientInfo : RecipientInfo) : Unit = {

		try {
			val prep = conn.prepareStatement("insert into " + RecipientInfoDBMaintance.TABLENAME + "(name, age, job, gender, homepage) values(?,?,?,?,?);");
			prep.setString(1, recipientInfo.name);
			prep.setString(2, recipientInfo.age.toString());
			prep.setString(3, recipientInfo.job.toString());
			prep.setString(4, recipientInfo.gender.toString());
			prep.setString(5, recipientInfo.homePage);
			prep.execute();
			prep.close();
		} catch {
		  case e: Exception => throw new RuntimeException(e);
		}

	}

	override def countRecipients() : Int = {
		try {
		  val stat = conn.createStatement();
			val rs = stat.executeQuery("select count(*) from " + RecipientInfoDBMaintance.TABLENAME);
			rs.next();
			val count = rs.getInt(1);
			rs.close();
			return count;
		} catch {
		  case e: Exception => throw new RuntimeException(e);
		}
	}
	
	override def goThroughAll(func:RecipientInfo => Unit):Int = {
		try{
		  val stat = conn.createStatement();
			val rs = stat.executeQuery("select * from " + RecipientInfoDBMaintance.TABLENAME);
			val list = new LinkedList[RecipientInfo]();
			var i = 0;
			while(rs.next())
			{
				var ri = new RecipientInfo();
				
				ri.name = rs.getString("name");
				ri.age = RecipientAge.parse(rs.getString("age"));
				ri.job = RecipientJob.parse(rs.getString("job"));
				ri.gender = RecipientGender.parse(rs.getString("gender"));
				ri.homePage = rs.getString("homepage");
				
				func(ri);
				i = i+1;
			}
			rs.close();
			return i;
		} catch {
		  case e: Exception => throw new RuntimeException(e);
		}
	}
	
	override def  save() {
		//do nothing
	}

	override def  close(){
		try {
			conn.close();
		} catch {
		  case e: Exception => throw new RuntimeException(e);
		}
	}
	


}
