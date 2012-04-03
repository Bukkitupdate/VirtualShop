package org.blockface.virtualshop.managers;

import java.util.List;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.objects.Transaction;
import org.blockface.virtualshop.persistance.Database;
import org.blockface.virtualshop.persistance.MySQLDB;
import org.blockface.virtualshop.persistance.SQLiteDB;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DatabaseManager
{
  private static Database database;

  public static void Initialize()
  {
    if (ConfigManager.UsingMySQL().booleanValue()) LoadMySQL(); else
      LoadSQLite();
  }

  private static void LoadSQLite() {
    database = new SQLiteDB();
    try {
      database.Load();
    } catch (Exception e) {
      Chatty.LogInfo("Fatal error.");
    }
  }

  private static void LoadMySQL() {
    database = new MySQLDB();
    try {
      database.Load();
    } catch (Exception e) {
      LoadSQLite();
    }
  }

  public static void Close() {
    database.Unload();
  }

  public static void AddOffer(Offer offer)
  {
    String query = "insert into stock(seller,item,amount,price,damage) values('" + offer.seller + "'," + offer.item.getType().getId() + "," + offer.item.getAmount() + "," + offer.price + "," + offer.item.getDurability() + ")";
    database.Query(query);
  }

  public static List<Offer> GetItemOffers(ItemStack item)
  {
    String query = "select * from stock where item=" + item.getTypeId() + " and damage=" + item.getDurability() + " order by price asc";
    return Offer.ListOffers(database.Query(query));
  }

  public static List<Offer> GetSellerOffers(String player, ItemStack item)
  {
    String query = "select * from stock where seller = '" + player + "' and item =" + item.getTypeId() + " and damage=" + item.getDurability();
    return Offer.ListOffers(database.Query(query));
  }

  public static void RemoveSellerOffers(Player player, ItemStack item)
  {
    String query = "delete from stock where seller = '" + player.getName() + "' and item =" + item.getTypeId() + " and damage = " + item.getDurability();
    database.Query(query);
  }

  public static void DeleteItem(int id)
  {
    String query = "delete from stock where id=" + id;
    database.Query(query);
  }

  public static void UpdateQuantity(int id, int quantity)
  {
    String query = "update stock set amount=" + quantity + " where id=" + id;
    database.Query(query);
  }

  public static void LogTransaction(Transaction transaction)
  {
    String query = "insert into transactions(seller,buyer,item,amount,cost,damage) values('" + transaction.seller + "','" + transaction.buyer + "'," + transaction.item.getTypeId() + "," + transaction.item.getAmount() + "," + transaction.cost + "," + transaction.item.getDurability() + ")";
    database.Query(query);
  }

  public static List<Offer> GetBestPrices()
  {
    String query = "select f.* from (select item,min(price) as minprice from stock group by item) as x inner join stock as f on f.item = x.item and f.price = x.minprice";
    return Offer.ListOffers(database.Query(query));
  }

  public static List<Offer> SearchBySeller(String seller)
  {
    return Offer.ListOffers(database.Query("select * from stock where seller like '%" + seller + "%'"));
  }

  public static List<Transaction> GetTransactions()
  {
    return Transaction.ListTransactions(database.Query("select * from transactions order by id desc"));
  }

  public static List<Transaction> GetTransactions(String search)
  {
    return Transaction.ListTransactions(database.Query("select * from transactions where seller like '%" + search + "%' OR buyer like '%" + search + "%' order by id"));
  }

  public static List<Offer> GetPrices(ItemStack item)
  {
    String query = "select * from stock where item=" + item.getTypeId() + " AND damage=" + item.getDurability() + " order by price asc limit 0,10";
    return Offer.ListOffers(database.Query(query));
  }
}