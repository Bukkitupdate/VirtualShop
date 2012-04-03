package org.blockface.virtualshop.commands;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.InventoryManager;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cancel
{
  public static void Execute(CommandSender sender, String[] args)
  {
    if (!(sender instanceof Player))
    {
      Chatty.DenyConsole(sender);
      return;
    }
    if (!sender.hasPermission("virtualshop.cancel"))
    {
      Chatty.NoPermissions(sender);
      return;
    }
    if (args.length < 1)
    {
      Chatty.SendError(sender, "You must specify an item.");
      return;
    }
    ItemStack item = ItemDb.get(args[0], 0);
    if (item == null)
    {
      Chatty.WrongItem(sender, args[0]);
      return;
    }
    Player player = (Player)sender;
    int total = 0;
    for (Offer o : DatabaseManager.GetSellerOffers(player.getName(), item))
    {
      total += o.item.getAmount();
    }
    if (total == 0)
    {
      Chatty.SendError(sender, "You do not have any " + args[0] + " for sale.");
      return;
    }
    item.setAmount(total);
    new InventoryManager(player).addItem(item);
    DatabaseManager.RemoveSellerOffers(player, item);
    Chatty.SendSuccess(sender, "Removed " + Chatty.FormatAmount(Integer.valueOf(total)) + " " + Chatty.FormatItem(args[0]));
  }
}