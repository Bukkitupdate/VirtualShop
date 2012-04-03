package org.blockface.virtualshop.commands;

import java.util.List;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.ItemDb;
import org.blockface.virtualshop.util.Numbers;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class Find
{
  public static void Execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("virtualshop.find"))
    {
      Chatty.NoPermissions(sender);
      return;
    }
    if (args.length < 1)
    {
      Chatty.SendError(sender, "You need to specify the item.");
      return;
    }
    ItemStack item = ItemDb.get(args[0], 0);
    if (item == null)
    {
      Chatty.WrongItem(sender, args[0]);
      return;
    }
    int page = 1;
    List<Offer> offers = DatabaseManager.GetPrices(item);
    if (args.length > 1) page = Numbers.ParseInteger(args[1]).intValue();
    if (offers.size() == 0)
    {
      Chatty.SendError(sender, "No one is selling " + args[0]);
      return;
    }

    int start = (page - 1) * 9;
    int pages = offers.size() / 9 + 1;
    int count = 0;
    if (page > pages)
    {
      start = 0;
      page = 1;
    }
    sender.sendMessage(ChatColor.DARK_GRAY + "---------------" + ChatColor.GRAY + "Page (" + ChatColor.RED + page + ChatColor.GRAY + " of " + ChatColor.RED + pages + ChatColor.GRAY + ")" + ChatColor.DARK_GRAY + "---------------");
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