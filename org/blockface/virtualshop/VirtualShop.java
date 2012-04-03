package org.blockface.virtualshop;

import java.io.IOException;

import org.blockface.virtualshop.commands.Buy;
import org.blockface.virtualshop.commands.Cancel;
import org.blockface.virtualshop.commands.Find;
import org.blockface.virtualshop.commands.Help;
import org.blockface.virtualshop.commands.Sales;
import org.blockface.virtualshop.commands.Sell;
import org.blockface.virtualshop.commands.Stock;
import org.blockface.virtualshop.events.ServerEvents;
import org.blockface.virtualshop.managers.ConfigManager;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class VirtualShop extends JavaPlugin
{
  public void onDisable()
  {
    DatabaseManager.Close();
  }

  public void onEnable() {
    Chatty.Initialize(this);
    ConfigManager.Initialize(this);
    DatabaseManager.Initialize();
    try {
      ItemDb.load(getDataFolder(), "items.csv");
    } catch (IOException e) {
      getPluginLoader().disablePlugin(this);
      return;
    }
    RegisterEvents();
  }

  private void RegisterEvents()
  {
    getServer().getPluginManager().registerEvents(new ServerEvents(), this);
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (label.equalsIgnoreCase("sell")) Sell.Execute(sender, args);
    if (label.equalsIgnoreCase("buy")) Buy.Execute(sender, args);
    if (label.equalsIgnoreCase("cancel")) Cancel.Execute(sender, args);
    if (label.equalsIgnoreCase("stock")) Stock.Execute(sender, args);
    if (label.equalsIgnoreCase("sales")) Sales.Execute(sender, args);
    if (label.equalsIgnoreCase("find")) Find.Execute(sender, args);
    if (label.equalsIgnoreCase("vs")) Help.Execute(sender);
    return true;
  }
}