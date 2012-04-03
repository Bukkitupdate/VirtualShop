package org.blockface.virtualshop.persistance;

import java.sql.ResultSet;

public abstract interface Database
{
  public abstract void Load()
    throws Exception;

  public abstract ResultSet Query(String paramString);

  public abstract void Unload();
}