package org.blockface.virtualshop.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class InventoryManager
{
  private final Inventory _inv;
  private final Player _player;
  private final Location _loc;

  public InventoryManager(Inventory inv)
  {
    this._inv = inv;
    this._player = null;
    this._loc = null;
  }

  public InventoryManager(Player player) {
    this._inv = player.getInventory();
    this._player = player;
    this._loc = null;
  }

  public InventoryManager(Inventory inv, Player player) {
    this._inv = inv;
    this._player = player;
    this._loc = null;
  }

  public InventoryManager(Inventory inv, Location loc) {
    this._inv = inv;
    this._player = null;
    this._loc = loc;
  }

  public InventoryManager(Player player, Location loc) {
    this._inv = player.getInventory();
    this._player = null;
    this._loc = loc;
  }

  public static byte handleData(MaterialData data) {
    try {
      if (data != null) return data.getData(); return 0; } catch (Exception ex) {
    }
    return 0;
  }

  public static byte handleData(short data)
  {
    try {
      return (byte)data; } catch (Exception ex) {
    }
    return 0;
  }

  public ItemStack quantify(ItemStack type)
  {
    return quantify(type, true, false);
  }

  public ItemStack quantify(ItemStack type, boolean meta) {
    return quantify(type, meta, false);
  }

  public ItemStack quantify(ItemStack type, boolean meta, boolean durability) {
    int amount = 0;
    Map.Entry which;
    for (Iterator i$ = this._inv.all(type.getType()).entrySet().iterator(); i$.hasNext(); amount += ((ItemStack)which.getValue()).getAmount()) { which = (Map.Entry)i$.next(); if (type.getType().getMaxDurability() <= 0 ? (!meta) && (handleData(type.getData()) == handleData(((ItemStack)which.getValue()).getDurability())) : (durability) && (type.getDurability() != ((ItemStack)which.getValue()).getDurability())); } return new ItemStack(type.getType(), amount, type.getDurability(), Byte.valueOf(handleData(type.getData())));
  }

  public boolean contains(ItemStack stack) {
    return contains(stack, true, false);
  }

  public boolean contains(ItemStack stack, boolean meta) {
    return contains(stack, true, false);
  }

  public boolean contains(ItemStack stack, boolean meta, boolean durability) {
    return ((stack.getAmount() > 0) && (quantify(stack, meta, durability).getAmount() >= stack.getAmount())) || ((stack.getAmount() == 0) && (quantify(stack, meta, durability).getAmount() >= 1));
  }

  public ItemStack addItem(ItemStack stack) {
    int size = this._inv.getSize();

    int amount = stack.getAmount();
    int max = stack.getType().getMaxStackSize();

    if (max < 1) max = 64;

    for (int i = 0; i < size; i++) {
      ItemStack slot = this._inv.getItem(i);

      int amt = slot.getAmount();

      if ((amt < max) && ((amt < 1) || ((stack.getTypeId() == slot.getTypeId()) && (handleData(stack.getData()) == handleData(slot.getDurability()))))) {
        this._inv.setItem(i, new ItemStack(stack.getType(), amount + amt > max ? max : amount + amt, stack.getDurability(), Byte.valueOf(handleData(stack.getData()))));

        amount -= (max - amt < 0 ? 0 : max - amt);
      }

      if (amount <= 0)
        break;
    }
    for (i=0; (amount <= 0) || ((this._player == null) && (this._loc == null)); amount > 0; ) 
    { 
    	(this._player != null ? this._player.getLocation() : this._loc).getWorld().dropItemNaturally(this._player != null ? this._player.getLocation() : this._loc, new ItemStack(stack.getType(), amount > max ? max : amount, stack.getDurability(), Byte.valueOf(handleData(stack.getData())))); 
    	amount -= max; 
    	continue; 
    	return new ItemStack(stack.getType(), amount, stack.getDurability(), Byte.valueOf(handleData(stack.getData())));
    }
    return new ItemStack(stack.getType(), 0, stack.getDurability(), Byte.valueOf(handleData(stack.getData())));
  }

  public ItemStack remove(ItemStack stack) {
    return remove(stack, true);
  }

  public ItemStack remove(ItemStack stack, boolean meta) {
    return remove(stack, true, false);
  }

  public ItemStack remove(ItemStack stack, boolean meta, boolean durability) {
    int amount = stack.getAmount();

    for (Map.Entry which : this._inv.all(stack.getType()).entrySet()) {
      if (stack.getType().getMaxDurability() <= 0 ? (meta) || (handleData(stack.getData()) != handleData(((ItemStack)which.getValue()).getDurability())) : (!durability) || (stack.getDurability() == ((ItemStack)which.getValue()).getDurability())) {
        if (amount >= ((ItemStack)which.getValue()).getAmount()) {
          amount -= ((ItemStack)which.getValue()).getAmount();

          this._inv.clear(((Integer)which.getKey()).intValue());
        } else {
          if (amount <= 0)
            break;
          ItemStack rep = (ItemStack)which.getValue();

          rep.setAmount(rep.getAmount() - amount);

          this._inv.setItem(((Integer)which.getKey()).intValue(), rep);

          amount = 0;
        }
      }
    }

    return new ItemStack(stack.getType(), stack.getAmount() - amount, stack.getDurability(), Byte.valueOf(handleData(stack.getData())));
  }
}