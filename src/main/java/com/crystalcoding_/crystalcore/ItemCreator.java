package com.crystalcoding_.crystalcore;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemCreator {
    public static ItemStack createItem(Material mat) {
        return new ItemStack(mat);
    }

    public static ItemStack createItem(Material mat, String name) {
        ItemStack item = createItem(mat);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Core.color(name));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material mat, String name, ArrayList<String> lore) {
        ItemStack item = createItem(mat, name);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack getHelmetPlaceHolder() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fThis player is not wearing a helmet!"));
        return ItemCreator.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bHelmet Placeholder", lore);
    }

    public static ItemStack getChestplatePlaceHolder() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fThis player is not wearing a chestplate!"));
        return ItemCreator.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bChestplate Placeholder", lore);
    }

    public static ItemStack getLeggingsPlaceHolder() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fThis player is not wearing any leggings!"));
        return ItemCreator.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bLeggings Placeholder", lore);
    }

    public static ItemStack getBootsPlaceHolder() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fThis player is not wearing any boots!"));
        return ItemCreator.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bBoots Placeholder", lore);
    }

    public static ItemStack getOffHandPlaceHolder() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fThis player is not holding anything in their off-hand!"));
        return ItemCreator.createItem(Material.YELLOW_STAINED_GLASS_PANE, "&bOff-Hand Placeholder", lore);
    }
}
