package me.elementz.enchantingbooks.blocks;

import me.elementz.enchantingbooks.EnchantingBooks;
import me.elementz.enchantingbooks.config.Config;
import me.elementz.enchantingbooks.item.ItemRegistration;
import me.elementz.enchantingbooks.util.CustomItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnchantingBooks.ID);

    public static final RegistryObject<Block> NETHERITE_BOOKSHELF = BLOCKS.register("netherite_bookshelf", () ->
            new CustomBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(20.0F, 1200.0F).sound(SoundType.NETHERITE), Config.NETHERITE_BOOKSHELF_POWER.get().floatValue()));
    public static final RegistryObject<Item> NETHERITE_BOOKSHELF_ITEM = ItemRegistration.ITEMS.register("netherite_bookshelf", () ->
            new BlockItem(NETHERITE_BOOKSHELF.get(), new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS)));

    public static final RegistryObject<Block> DIAMOND_BOOKSHELF = BLOCKS.register("diamond_bookshelf", () ->
            new CustomBlock(Block.Properties.create(Material.IRON, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), Config.DIAMOND_BOOKSHELF_POWER.get().floatValue()));
    public static final RegistryObject<Item> DIAMOND_BOOKSHELF_ITEM = ItemRegistration.ITEMS.register("diamond_bookshelf", () ->
            new BlockItem(DIAMOND_BOOKSHELF.get(), new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS)));

    public static final RegistryObject<Block> GOLDEN_BOOKSHELF = BLOCKS.register("golden_bookshelf", () ->
            new CustomBlock(Block.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(3.0F, 6.0F).sound(SoundType.METAL), Config.GOLD_BOOKSHELF_POWER.get().floatValue()));
    public static final RegistryObject<Item> GOLDEN_BOOKSHELF_ITEM = ItemRegistration.ITEMS.register("golden_bookshelf", () ->
            new BlockItem(GOLDEN_BOOKSHELF.get(), new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS)));

    public static final RegistryObject<Block> IRON_BOOKSHELF = BLOCKS.register("iron_bookshelf", () ->
            new CustomBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), Config.IRON_BOOKSHELF_POWER.get().floatValue()));
    public static final RegistryObject<Item> IRON_BOOKSHELF_ITEM = ItemRegistration.ITEMS.register("iron_bookshelf", () ->
            new BlockItem(IRON_BOOKSHELF.get(), new Item.Properties().group(CustomItemGroup.ENCHANTING_BOOKS)));

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
