package org.blockface.virtualshop.commands;

import java.util.List;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.Numbers;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Stock
{
  public static void Execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("virtualshop.stock"))
    {
      Chatty.NoPermissions(sender);
      return;
    }
    int start = 1;

    List<Offer> offers = DatabaseManager.GetBestPrices();
    if (args.length > 0) start = Numbers.ParseInteger(args[0]).intValue();
    if (start < 0)
    {
      String seller = args[0];
      if (args.length > 1) start = Numbers.ParseInteger(args[1]).intValue();
      if (start < 0) start = 1;
      start = (start - 1) * 9;
      offers = DatabaseManager.SearchBySeller(seller);
    } else {
      start = (start - 1) * 9;
    }
    int page = start / 9 + 1;
    int pages = offers.size() / 9 + 1;
    if (page > pages)
    {
      start = 0;
      page = 1;
    }
    sender.sendMessage(ChatColor.DARK_GRAY + "---------------" + ChatColor.GRAY + "Page (" + ChatColor.RED + page + ChatColor.GRAY + " of " + ChatColor.RED + pages + ChatColor.GRAY + ")" + ChatColor.DARK_GRAY + "---------------");
    int count = 0;
    for (Offer o : offers)
    {
      if (count == start + 9) break;
      if (count >= start)
      {
        sender.sendMessage(Chatty.FormatOffer(o));
      }
      count++;
    }
  }
}