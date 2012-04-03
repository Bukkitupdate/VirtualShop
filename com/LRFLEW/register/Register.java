package com.LRFLEW.register;

import com.LRFLEW.register.payment.Methods;
import java.util.logging.Level;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Register extends JavaPlugin
{
  public void onDisable()
  {
    Methods.reset();

    PluginDescriptionFile pdfFile = getDescription();
    System.out.println(pdfFile.getName() + " is dissabled and methods have been reset!");
  }

  public void onEnable()
  {
    Methods.setVersion(getDescription().getVersion());

    Methods.setMethod(getServer().getPluginManager());

    PluginDescriptionFile pdfFile = getDescription();
    if (Methods.getMethod() == null) getServer().getLogger().log(Level.WARNING, "[Register] No Meathod Found.  Plugins may not work");

    System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled and avaiable for hooking!");
  }
}