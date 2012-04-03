package org.blockface.virtualshop.commands;

import java.util.List;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Transaction;
import org.blockface.virtualshop.util.Numbers;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Sales
{
  public static void Execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("virtualshop.sales"))
    {
      Chatty.NoPermissions(sender);
      return;
    }
    int start = 1;

    List<Transaction> transactions = DatabaseManager.GetTransactions();
    if (args.length > 0) start = Numbers.ParseInteger(args[0]).intValue();
    if (start < 0)
    {
      String search = args[0];
      if (args.length > 1) start = Numbers.ParseInteger(args[1]).intValue();
      if (start < 0) start = 1;
      start = (start - 1) * 9;
      transactions = DatabaseManager.GetTransactions(search);
    } else {
      start = (start - 1) * 9;
    }
    int page = start / 9 + 1;
    int pages = transactions.size() / 9 + 1;
    if (page > pages)
    {
      start = 0;
      page = 1;
    }
    sender.sendMessage(ChatColor.DARK_GRAY + "---------------" + ChatColor.GRAY + "Page (" + ChatColor.RED + page + ChatColor.GRAY + " of " + ChatColor.RED + pages + ChatColor.GRAY + ")" + ChatColor.DARK_GRAY + "---------------");
    int count = 0;
    for (Transaction t : transactions)
    {
      if (count == start + 9) break;
      if (count >= start)
      {
        sender.sendMessage(Chatty.FormatTransaction(t));
      }
      count++;
    }
  }
}