package org.blockface.virtualshop.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager
{
  private static FileConfiguration config;

  public static void Initialize(Plugin plugin)
  {
    config = plugin.getConfig();
    config.getDefaults();
    BroadcastOffers();
    UsingMySQL();
    MySQLUserName();
    MySQLHost();
    MySQLdatabase();
    MySQLport();
    MySQLPassword();
    getPort();
    plugin.saveConfig();
  }

  public static Boolean BroadcastOffers()
  {
    return Boolean.valueOf(config.getBoolean("broadcast-offers", true));
  }

  public static Integer getPort() {
    return Integer.valueOf(config.getInt("MySQL.port", 3306));
  }

  public static Boolean UsingMySQL()
  {
    return Boolean.valueOf(config.getBoolean("using-MySQL", false));
  }

  public static String MySQLUserName()
  {
    return config.getString("MySQL.username", "root");
  }

  public static String MySQLPassword()
  {
    return config.getString("MySQL.password", "password");
  }

  public static String MySQLHost()
  {
    return config.getString("MySQL.host", "localhost");
  }

  public static String MySQLdatabase()
  {
    return config.getString("MySQL.database", "minecraft");
  }

  public static Integer MySQLport()
  {
    return Integer.valueOf(config.getInt("MySQL.port", 3306));
  }
}