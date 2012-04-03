package com.LRFLEW.register.payment.methods;

import com.LRFLEW.register.payment.Method;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.plugin.Plugin;

public class EE17
  implements Method
{
  private Essentials Essentials;

  public Essentials getPlugin()
  {
    return this.Essentials;
  }

  public String getName() {
    return "Essentials";
  }

  public String getVersion() {
    return "2.2";
  }

  public int fractionalDigits() {
    return -1;
  }

  public String format(double amount) {
    return Economy.format(amount);
  }

  public boolean hasBanks() {
    return false;
  }

  public boolean hasBank(String bank) {
    return false;
  }

  public boolean hasAccount(String name) {
    return Economy.playerExists(name);
  }

  public boolean hasBankAccount(String bank, String name) {
    return false;
  }

  public Method.MethodAccount getAccount(String name) {
    if (!hasAccount(name)) return null;
    return new EEcoAccount(name);
  }

  public Method.MethodBankAccount getBankAccount(String bank, String name) {
    return null;
  }
  public boolean isCompatible(Plugin plugin) {
    try {
      Class.forName("com.earth2me.essentials.api.Economy"); } catch (Exception e) {
      return false;
    }
    return (plugin.getDescription().getName().equalsIgnoreCase("essentials")) && ((plugin instanceof Essentials));
  }

  public void setPlugin(Plugin plugin) {
    this.Essentials = ((Essentials)plugin);
  }
  public class EEcoAccount implements Method.MethodAccount {
    private String name;

    public EEcoAccount(String name) {
      this.name = name;
    }

    public double balance() {
      Double balance = Double.valueOf(0.0D);
      try
      {
        balance = Double.valueOf(Economy.getMoney(this.name));
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] Failed to grab balance in Essentials Economy: " + ex.getMessage());
      }

      return balance.doubleValue();
    }

    public boolean set(double amount) {
      try {
        Economy.setMoney(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
        return false;
      } catch (NoLoanPermittedException ex) {
        System.out.println("[REGISTER] No loan permitted in Essentials Economy: " + ex.getMessage());
        return false;
      }

      return true;
    }

    public boolean add(double amount) {
      try {
        Economy.add(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
        return false;
      } catch (NoLoanPermittedException ex) {
        System.out.println("[REGISTER] No loan permitted in Essentials Economy: " + ex.getMessage());
        return false;
      }

      return true;
    }

    public boolean subtract(double amount) {
      try {
        Economy.subtract(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
        return false;
      } catch (NoLoanPermittedException ex) {
        System.out.println("[REGISTER] No loan permitted in Essentials Economy: " + ex.getMessage());
        return false;
      }

      return true;
    }

    public boolean multiply(double amount) {
      try {
        Economy.multiply(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
        return false;
      } catch (NoLoanPermittedException ex) {
        System.out.println("[REGISTER] No loan permitted in Essentials Economy: " + ex.getMessage());
        return false;
      }

      return true;
    }

    public boolean divide(double amount) {
      try {
        Economy.divide(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
        return false;
      } catch (NoLoanPermittedException ex) {
        System.out.println("[REGISTER] No loan permitted in Essentials Economy: " + ex.getMessage());
        return false;
      }

      return true;
    }

    public boolean hasEnough(double amount) {
      try {
        return Economy.hasEnough(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
      }

      return false;
    }

    public boolean hasOver(double amount) {
      try {
        return Economy.hasMore(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
      }

      return false;
    }

    public boolean hasUnder(double amount) {
      try {
        return Economy.hasLess(this.name, amount);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
      }

      return false;
    }

    public boolean isNegative() {
      try {
        return Economy.isNegative(this.name);
      } catch (UserDoesNotExistException ex) {
        System.out.println("[REGISTER] User does not exist in Essentials Economy: " + ex.getMessage());
      }

      return false;
    }

    public boolean remove() {
      return false;
    }
  }
}