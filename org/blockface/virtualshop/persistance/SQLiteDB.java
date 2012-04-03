package org.blockface.virtualshop.persistance;

import java.sql.ResultSet;
import lib.PatPeter.SQLibrary.SQLite;
import org.blockface.virtualshop.Chatty;

public class SQLiteDB
  implements Database
{
  private SQLite db;

  public void Load()
    throws Exception
  {
    this.db = new SQLite(Chatty.getLogger(), Chatty.getPrefix(), "VirtualShop", "plugins/VirtualShop/");
    this.db.open();
    if (!this.db.checkConnection())
    {
      Chatty.LogInfo("FlatFile creation failed!");
      throw new Exception("FlatFile creation failed.");
    }
    Chatty.LogInfo("Using flat files.");
    CheckTables();
  }

  private void CheckTables()
  {
    if (!this.db.checkTable("stock"))
    {
      String query = "create table stock('id' integer primary key,'damage' integer,'seller' varchar(80) not null,'item' integer not null, 'price' float not null,'amount' integer not null)";
      this.db.createTable(query);
      Chatty.LogInfo("Created stock table.");
    }
    if (!this.db.checkTable("transactions"))
    {
      String query = "create table transactions('id' integer primary key,'damage' integer not null,'buyer' varchar(80) not null,'seller' varchar(80) not null,'item' integer not null, 'cost' float not null,'amount' integer not null)";
      this.db.createTable(query);
      Chatty.LogInfo("Created transaction table.");
    }
  }

  public ResultSet Query(String query)
  {
    return this.db.query(query);
  }

  public void Unload() {
    this.db.close();
  }
}