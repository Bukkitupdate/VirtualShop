package org.blockface.virtualshop;

import java.util.logging.Logger;
import org.blockface.virtualshop.managers.EconomyManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.objects.Transaction;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Chatty
{
  private static String prefix;
  private static Plugin plugin;
  private static Logger logger;

  public static void Initialize(Plugin p)
  {
    logger = Logger.getLogger("minecraft");
    plugin = p;
    prefix = "[Shop] " + ChatColor.WHITE;
    LogInfo(plugin.getDescription().getName() + " is loading.");
  }

  public static void LogInfo(String message)
  {
    logger.info(message);
  }

  public static void SendError(CommandSender sender, String message)
  {
    sender.sendMessage(ChatColor.RED + prefix + message);
  }

  public static void SendSuccess(CommandSender sender, String message)
  {
    sender.sendMessage(ChatColor.DARK_GREEN + prefix + message);
  }

  public static Boolean SendSuccess(String sender, String message) {
    Player player = plugin.getServer().getPlayer(sender);
    if (player == null) return Boolean.valueOf(false);
    SendSuccess(player, message);
    return Boolean.valueOf(true);
  }

  public static void SendGlobal(String message)
  {
    plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + prefix + message);
  }

  public static Logger getLogger() {
    return logger;
  }

  public static String getPrefix() {
    return prefix;
  }

  public static void WrongItem(CommandSender sender, String item)
  {
    SendError(sender, "What is " + item + "?");
  }

  public static void DenyConsole(CommandSender sender)
  {
    SendError(sender, "You must be in-game to do this.");
  }

  public static void NumberFormat(CommandSender sender)
  {
    SendError(sender, "That is not a proper number.");
  }

  public static String FormatSeller(String seller)
  {
    return ChatColor.RED + seller + ChatColor.WHITE;
  }

  public static String FormatAmount(Integer amount)
  {
    return ChatColor.GOLD + amount.toString() + ChatColor.WHITE;
  }

  public static String FormatItem(String item)
  {
    return ChatColor.BLUE + item.toLowerCase() + ChatColor.WHITE;
  }

  public static String FormatPrice(double price)
  {
    return ChatColor.YELLOW + EconomyManager.getMethod().format(price) + ChatColor.WHITE;
  }

  public static String FormatBuyer(String buyer)
  {
    return ChatColor.AQUA + buyer.toString() + ChatColor.WHITE;
  }

  public static void NoPermissions(CommandSender sender)
  {
    SendError(sender, "You do not have permission to do this");
  }

  public static void BroadcastOffer(Offer o) {
    SendGlobal(FormatOffer(o));
  }

  public static String FormatOffer(Offer o)
  {
    return FormatSeller(o.seller) + ": " + FormatAmount(Integer.valueOf(o.item.getAmount())) + " " + FormatItem(ItemDb.reverseLookup(o.item)) + " for " + FormatPrice(o.price) + " each.";
  }

  public static String FormatTransaction(Transaction t)
  {
    return FormatSeller(t.seller) + " --> " + FormatBuyer(t.buyer) + ": " + FormatAmount(Integer.valueOf(t.item.getAmount())) + " " + FormatItem(ItemDb.reverseLookup(t.item)) + " for " + FormatPrice(t.cost);
  }
}