package com.LRFLEW.register.payment.methods;

import com.LRFLEW.register.payment.Method;
import com.iCo6.iConomy;
import com.iCo6.system.Account;
import com.iCo6.system.Accounts;
import com.iCo6.system.Holdings;
import org.bukkit.plugin.Plugin;

public class iCo6
  implements Method
{
  private iConomy iConomy;

  public iConomy getPlugin()
  {
    return this.iConomy;
  }

  public String getName() {
    return "iConomy";
  }

  public String getVersion() {
    return "6";
  }

  public int fractionalDigits() {
    return 2;
  }

  public String format(double amount) {
    return iConomy.format(amount);
  }

  public boolean hasBanks() {
    return false;
  }

  public boolean hasBank(String bank) {
    return false;
  }

  public boolean hasAccount(String name) {
    return new Accounts().exists(name);
  }

  public boolean hasBankAccount(String bank, String name) {
    return false;
  }

  public Method.MethodAccount getAccount(String name) {
    return new iCoAccount(new Accounts().get(name));
  }

  public Method.MethodBankAccount getBankAccount(String bank, String name) {
    return null;
  }

  public boolean isCompatible(Plugin plugin) {
    return (plugin.getDescription().getName().equalsIgnoreCase("iconomy")) && (plugin.getClass().getName().equals("com.iCo6.iConomy")) && ((plugin instanceof iConomy));
  }

  public void setPlugin(Plugin plugin)
  {
    this.iConomy = ((iConomy)plugin);
  }
  public class iCoAccount implements Method.MethodAccount {
    private Account account;
    private Holdings holdings;

    public iCoAccount(Account account) { this.account = account;
      this.holdings = account.getHoldings(); }

    public Account getiCoAccount()
    {
      return this.account;
    }

    public double balance() {
      return this.holdings.getBalance().doubleValue();
    }

    public boolean set(double amount) {
      if (this.holdings == null) return false;
      this.holdings.setBalance(amount);
      return true;
    }

    public boolean add(double amount) {
      if (this.holdings == null) return false;
      this.holdings.add(amount);
      return true;
    }

    public boolean subtract(double amount) {
      if (this.holdings == null) return false;
      this.holdings.subtract(amount);
      return true;
    }

    public boolean multiply(double amount) {
      if (this.holdings == null) return false;
      this.holdings.multiply(amount);
      return true;
    }

    public boolean divide(double amount) {
      if (this.holdings == null) return false;
      this.holdings.divide(amount);
      return true;
    }

    public boolean hasEnough(double amount) {
      return this.holdings.hasEnough(amount);
    }

    public boolean hasOver(double amount) {
      return this.holdings.hasOver(amount);
    }

    public boolean hasUnder(double amount) {
      return this.holdings.hasUnder(amount);
    }

    public boolean isNegative() {
      return this.holdings.isNegative();
    }

    public boolean remove() {
      if (this.account == null) return false;
      this.account.remove();
      return true;
    }
  }
}