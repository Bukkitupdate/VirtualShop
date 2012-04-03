package com.LRFLEW.register.payment.methods;

import com.LRFLEW.register.payment.Method;
import me.ashtheking.currency.Currency;
import me.ashtheking.currency.CurrencyList;
import org.bukkit.plugin.Plugin;

public class MCUR
  implements Method
{
  private Currency currencyList;

  public Object getPlugin()
  {
    return this.currencyList;
  }

  public String getName() {
    return "MultiCurrency";
  }

  public String getVersion() {
    return "0.09";
  }

  public int fractionalDigits() {
    return -1;
  }

  public String format(double amount) {
    return amount + " Currency";
  }

  public boolean hasBanks() {
    return false;
  }

  public boolean hasBank(String bank) {
    return false;
  }

  public boolean hasAccount(String name) {
    return true;
  }

  public boolean hasBankAccount(String bank, String name) {
    return false;
  }

  public Method.MethodAccount getAccount(String name) {
    return new MCurrencyAccount(name);
  }

  public Method.MethodBankAccount getBankAccount(String bank, String name) {
    return null;
  }

  public boolean isCompatible(Plugin plugin) {
    return ((plugin.getDescription().getName().equalsIgnoreCase("Currency")) || (plugin.getDescription().getName().equalsIgnoreCase("MultiCurrency"))) && ((plugin instanceof Currency));
  }

  public void setPlugin(Plugin plugin)
  {
    this.currencyList = ((Currency)plugin);
  }
  public class MCurrencyAccount implements Method.MethodAccount {
    private String name;

    public MCurrencyAccount(String name) {
      this.name = name;
    }

    public double balance() {
      return CurrencyList.getValue((String)CurrencyList.maxCurrency(this.name)[0], this.name);
    }

    public boolean set(double amount) {
      CurrencyList.setValue((String)CurrencyList.maxCurrency(this.name)[0], this.name, amount);
      return true;
    }

    public boolean add(double amount) {
      return CurrencyList.add(this.name, amount);
    }

    public boolean subtract(double amount) {
      return CurrencyList.subtract(this.name, amount);
    }

    public boolean multiply(double amount) {
      return CurrencyList.multiply(this.name, amount);
    }

    public boolean divide(double amount) {
      return CurrencyList.divide(this.name, amount);
    }

    public boolean hasEnough(double amount) {
      return CurrencyList.hasEnough(this.name, amount);
    }

    public boolean hasOver(double amount) {
      return CurrencyList.hasOver(this.name, amount);
    }

    public boolean hasUnder(double amount) {
      return CurrencyList.hasUnder(this.name, amount);
    }

    public boolean isNegative() {
      return CurrencyList.isNegative(this.name);
    }

    public boolean remove() {
      return CurrencyList.remove(this.name);
    }
  }
}