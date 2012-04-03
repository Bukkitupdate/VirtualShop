package com.LRFLEW.register.payment.methods;

import com.LRFLEW.register.payment.Method;
import cosine.boseconomy.BOSEconomy;
import org.bukkit.plugin.Plugin;

public class BOSE6
  implements Method
{
  private BOSEconomy BOSEconomy;

  public BOSEconomy getPlugin()
  {
    return this.BOSEconomy;
  }

  public String getName() {
    return "BOSEconomy";
  }

  public String getVersion() {
    return "0.6.2";
  }

  public int fractionalDigits() {
    return 0;
  }

  public String format(double amount) {
    String currency = this.BOSEconomy.getMoneyNamePlural();
    if (amount == 1.0D) currency = this.BOSEconomy.getMoneyName();
    return amount + " " + currency;
  }

  public boolean hasBanks() {
    return true;
  }

  public boolean hasBank(String bank) {
    return this.BOSEconomy.bankExists(bank);
  }

  public boolean hasAccount(String name) {
    return this.BOSEconomy.playerRegistered(name, false);
  }

  public boolean hasBankAccount(String bank, String name) {
    return (this.BOSEconomy.isBankOwner(bank, name)) || (this.BOSEconomy.isBankMember(bank, name));
  }

  public Method.MethodAccount getAccount(String name) {
    if (!hasAccount(name)) return null;
    return new BOSEAccount(name, this.BOSEconomy);
  }

  public Method.MethodBankAccount getBankAccount(String bank, String name) {
    if (!hasBankAccount(bank, name)) return null;
    return new BOSEBankAccount(bank, this.BOSEconomy);
  }

  public boolean isCompatible(Plugin plugin) {
    return (plugin.getDescription().getName().equalsIgnoreCase("boseconomy")) && ((plugin instanceof BOSEconomy)) && (plugin.getDescription().getVersion().equals("0.6.2"));
  }

  public void setPlugin(Plugin plugin) {
    this.BOSEconomy = ((BOSEconomy)plugin);
  }

  public class BOSEBankAccount
    implements Method.MethodBankAccount
  {
    private final String bank;
    private final BOSEconomy BOSEconomy;

    public BOSEBankAccount(String bank, BOSEconomy bOSEconomy)
    {
      this.bank = bank;
      this.BOSEconomy = bOSEconomy;
    }

    public String getBankName() {
      return this.bank;
    }

    public int getBankId() {
      return -1;
    }

    public double balance() {
      return this.BOSEconomy.getBankMoney(this.bank);
    }

    public boolean set(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      return this.BOSEconomy.setBankMoney(this.bank, IntAmount, true);
    }

    public boolean add(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setBankMoney(this.bank, balance + IntAmount, false);
    }

    public boolean subtract(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setBankMoney(this.bank, balance - IntAmount, false);
    }

    public boolean multiply(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setBankMoney(this.bank, balance * IntAmount, false);
    }

    public boolean divide(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setBankMoney(this.bank, balance / IntAmount, false);
    }

    public boolean hasEnough(double amount) {
      return balance() >= amount;
    }

    public boolean hasOver(double amount) {
      return balance() > amount;
    }

    public boolean hasUnder(double amount) {
      return balance() < amount;
    }

    public boolean isNegative() {
      return balance() < 0.0D;
    }

    public boolean remove() {
      return this.BOSEconomy.removeBank(this.bank);
    }
  }

  public class BOSEAccount
    implements Method.MethodAccount
  {
    private final String name;
    private final BOSEconomy BOSEconomy;

    public BOSEAccount(String name, BOSEconomy bOSEconomy)
    {
      this.name = name;
      this.BOSEconomy = bOSEconomy;
    }

    public double balance() {
      return this.BOSEconomy.getPlayerMoney(this.name);
    }

    public boolean set(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      return this.BOSEconomy.setPlayerMoney(this.name, IntAmount, false);
    }

    public boolean add(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      return this.BOSEconomy.addPlayerMoney(this.name, IntAmount, false);
    }

    public boolean subtract(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setPlayerMoney(this.name, balance - IntAmount, false);
    }

    public boolean multiply(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setPlayerMoney(this.name, balance * IntAmount, false);
    }

    public boolean divide(double amount) {
      int IntAmount = (int)Math.ceil(amount);
      int balance = (int)balance();
      return this.BOSEconomy.setPlayerMoney(this.name, balance / IntAmount, false);
    }

    public boolean hasEnough(double amount) {
      return balance() >= amount;
    }

    public boolean hasOver(double amount) {
      return balance() > amount;
    }

    public boolean hasUnder(double amount) {
      return balance() < amount;
    }

    public boolean isNegative() {
      return balance() < 0.0D;
    }

    public boolean remove() {
      return false;
    }
  }
}