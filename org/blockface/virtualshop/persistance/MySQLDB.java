package org.blockface.virtualshop.persistance;

import java.sql.ResultSet;
import lib.PatPeter.SQLibrary.MySQL;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.ConfigManager;

public class MySQLDB
  implements Database
{
  private MySQL db;

  public void Load()
    throws Exception
  {
    Chatty.LogInfo("Using MySQL.");
    this.db = new MySQL(Chatty.getLogger(), Chatty.getPrefix(), ConfigManager.MySQLHost(), ConfigManager.getPort().toString(), ConfigManager.MySQLdatabase(), ConfigManager.MySQLUserName(), ConfigManager.MySQLPassword());
    this.db.open();
    if (this.db.checkConnection())
    {
      Chatty.LogInfo("Successfully connected to MySQL Database");
      CheckTables();
      return;
    }
    Chatty.LogInfo("Could not connect to MySQL Database. Check settings.");
  }

  public ResultSet Query(String query) {
    try {
      return this.db.query(query);
    } catch (Exception e) {
      reconnect();
    }return Query(query);
  }

  public void Unload()
  {
    this.db.close();
  }

  private void reconnect()
  {
    try {
      this.db.open();
    } catch (Exception e) {
      Chatty.LogInfo("Your database has gone offline, please switch to SQLite for stability.");
    }
  }

  private void CheckTables()
    throws Exception
  {
    if (!this.db.checkTable("stock"))
    {
      String query = "create table stock(`id` integer primary key auto_increment,`damage` integer,`seller` varchar(80) not null,`item` integer not null, `price` float not null,`amount` integer not null)";
      this.db.createTable(query);
      Chatty.LogInfo("Created stock table.");
    }
    if (!this.db.checkTable("transactions"))
    {
      String query = "create table transactions(`id` integer primary key auto_increment,`damage` integer not null, `buyer` varchar(80) not null,`seller` varchar(80) not null,`item` integer not null, `cost` float not null,`amount` integer not null)";
      this.db.createTable(query);
      Chatty.LogInfo("Created transaction table.");
    }
  }
}