package org.blockface.virtualshop.managers;

import com.LRFLEW.register.payment.Method;
import com.LRFLEW.register.payment.Methods;
import org.blockface.virtualshop.Chatty;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EconomyManager implements Listener
{
  public static void Initialize(Plugin plugin)
  {
    if (!Methods.hasMethod())
    {
      if (Methods.setMethod(plugin.getServer().getPluginManager())) Chatty.LogInfo("Using " + Methods.getMethod().getName());
    }
  }

  public static Method getMethod()
  {
    return Methods.getMethod();
  }
}