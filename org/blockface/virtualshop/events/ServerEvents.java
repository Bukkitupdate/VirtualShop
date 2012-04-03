package org.blockface.virtualshop.events;

import org.blockface.virtualshop.managers.EconomyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class ServerEvents implements Listener
{
	@EventHandler
  public void onPluginEnable(PluginEnableEvent event)
  {
    EconomyManager.Initialize(event.getPlugin());
  }
}