package com.thisisnzed.factionranking.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(final Material material, final short sh) {
        this.itemStack = new ItemStack(material, 1, sh);
    }

    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    public ItemBuilder setColor(final Color color) {
        final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        this.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemBuilder setGlow(final boolean glow) {
        if (glow) {
            this.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1);
            this.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            final ItemMeta itemMeta = this.getItemMeta();
            for (final Enchantment enchantment : itemMeta.getEnchants().keySet())
                itemMeta.removeEnchant(enchantment);
        }
        return this;
    }

    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.spigot().setUnbreakable(unbreakable);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setBannerColor(final DyeColor color) {
        final BannerMeta bannerMeta = (BannerMeta) this.itemStack.getItemMeta();
        bannerMeta.setBaseColor(color);
        this.setItemMeta(bannerMeta);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setItemMeta(final ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setHead(final String owner) {
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        this.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setDisplayName(final String displayName) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(displayName);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder setLore(final ArrayList<String> lore) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(final String lore) {
        final ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(loreList);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(final ItemFlag itemFlag) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
