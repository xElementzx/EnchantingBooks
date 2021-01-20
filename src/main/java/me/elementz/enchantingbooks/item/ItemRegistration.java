package me.elementz.enchantingbooks.item;

import me.elementz.enchantingbooks.EnchantingBooks;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnchantingBooks.ID);

    public static final RegistryObject<Item> NETHERITE_BOOK = ITEMS.register("netherite_book", () ->
            new CustomBookItem(new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS), 150));

    public static final RegistryObject<Item> DIAMOND_BOOK = ITEMS.register("diamond_book", () ->
            new CustomBookItem(new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS), 100));

    public static final RegistryObject<Item> GOLD_BOOK = ITEMS.register("gold_book", () ->
            new CustomBookItem(new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS), 50));

    public static final RegistryObject<Item> IRON_BOOK = ITEMS.register("iron_book", () ->
            new CustomBookItem(new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS), 20));

    public static final RegistryObject<Item> ENCHANTED_NETHERITE_BOOK = ITEMS.register("enchanted_netherite_book", () ->
            new CustomEnchantedBookItem(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ENCHANTED_DIAMOND_BOOK = ITEMS.register("enchanted_diamond_book", () ->
            new CustomEnchantedBookItem(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ENCHANTED_GOLD_BOOK = ITEMS.register("enchanted_gold_book", () ->
            new CustomEnchantedBookItem(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ENCHANTED_IRON_BOOK = ITEMS.register("enchanted_iron_book", () ->
            new CustomEnchantedBookItem(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON)));

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
}