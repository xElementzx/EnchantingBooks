package me.elementz.enchantingbooks.blocks;

import me.elementz.enchantingbooks.EnchantingBooks;
import me.elementz.enchantingbooks.item.ItemRegistration;
import me.elementz.enchantingbooks.util.CustomItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnchantingBooks.ID);

    //Create Block
    public static final RegistryObject<Block> NETHERITE_BOOKSHELF = BLOCKS.register("netherite_bookshelf", () ->
            new Block(Block.Properties.create(Material.WOOD)));
    //Create Block Item
    public static final RegistryObject<Item> NETHERITE_BOOKSHELF_ITEM = ItemRegistration.ITEMS.register("netherite_bookshelf", () ->
            new BlockItem(NETHERITE_BOOKSHELF.get(), new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS)));

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
