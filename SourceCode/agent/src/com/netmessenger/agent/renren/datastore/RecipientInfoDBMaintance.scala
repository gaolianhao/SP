package com.netmessenger.agent.renren.datastore;

import java.sql.Connection;
import java.sql.Statement;

import com.netmessenger.database.DBConnection;

object RecipientInfoDBMaintance {

  val TABLENAME = "renren";
}

class RecipientInfoDBMaintance(conn: Connection) {

  val TABLENAME = "renren";

  def createTable(): Unit = {
    try {
      val state = conn.createStatement();
      var tableStr =
        "Create Table " + TABLENAME + "(name nvarchar(50)," + "age nvarchar(50)," + "gender nvarchar(10)," + "job nvarchar(50)," + "homepage nvarchar(300) NOT NULL UNIQUE);";
      
      state.executeUpdate(tableStr);
      val createIndex = "CREATE UNIQUE INDEX index_renren_homepage ON "+TABLENAME+" (homepage);"
      state.executeUpdate(createIndex);
      
    } catch {
      case e: Exception => throw new RuntimeException(e);
    }
  }

  def clearTable(): Unit = {
    try {
      val state = conn.createStatement();
      var tableStr =
        "Drop Table " + TABLENAME + ";";

      state.executeUpdate(tableStr);
    } catch {
      case e: Exception => {};
    }

  }

}
