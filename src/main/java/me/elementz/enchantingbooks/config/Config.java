package me.elementz.enchantingbooks.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_BOOKS = "books";
    public static final String CATEGORY_BLOCKS = "blocks";
    public static final ForgeConfigSpec.Builder COMMON = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue NETHERITE_BOOK_ENCHANTABILITY;
    public static ForgeConfigSpec.IntValue DIAMOND_BOOK_ENCHANTABILITY;
    public static ForgeConfigSpec.IntValue GOLDEN_BOOK_ENCHANTABILITY;
    public static ForgeConfigSpec.IntValue IRON_BOOK_ENCHANTABILITY;
    public static ForgeConfigSpec.DoubleValue NETHERITE_BOOKSHELF_POWER;
    public static ForgeConfigSpec.DoubleValue DIAMOND_BOOKSHELF_POWER;
    public static ForgeConfigSpec.DoubleValue GOLD_BOOKSHELF_POWER;
    public static ForgeConfigSpec.DoubleValue IRON_BOOKSHELF_POWER;

    static {
        COMMON.comment("Book Enchantabilities").push(CATEGORY_BOOKS);
        NETHERITE_BOOK_ENCHANTABILITY = COMMON.comment("Enchantability for the Netherite Book")
                .defineInRange("netheriteBookEnchantability", 150, 1, Integer.MAX_VALUE);
        DIAMOND_BOOK_ENCHANTABILITY = COMMON.comment("Enchantability for the Diamond Book")
                .defineInRange("diamondBookEnchantability", 100, 1, Integer.MAX_VALUE);
        GOLDEN_BOOK_ENCHANTABILITY = COMMON.comment("Enchantability for the Golden Book")
                .defineInRange("goldenBookEnchantability", 50, 1, Integer.MAX_VALUE);
        IRON_BOOK_ENCHANTABILITY = COMMON.comment("Enchantability for the Iron Book")
                .defineInRange("ironBookEnchantability", 20, 1, Integer.MAX_VALUE);
        COMMON.pop();
        COMMON.comment("Block Enchanting Power").push(CATEGORY_BLOCKS);
        NETHERITE_BOOKSHELF_POWER = COMMON.comment("Enchantment Power for the Netherite Bookshelf")
                .defineInRange("netheriteBookshelfPower", 5.0, 1.0, Integer.MAX_VALUE);
        DIAMOND_BOOKSHELF_POWER = COMMON.comment("Enchantment Power for the Diamond Bookshelf")
                .defineInRange("diamondBookshelfPower", 4.0, 1.0, Integer.MAX_VALUE);
        GOLD_BOOKSHELF_POWER = COMMON.comment("Enchantment Power for the Gold Bookshelf")
                .defineInRange("goldBookshelfPower", 3.0, 1.0, Integer.MAX_VALUE);
        IRON_BOOKSHELF_POWER = COMMON.comment("Enchantment Power for the Iron Bookshelf")
                .defineInRange("ironBookshelfPower", 2.0, 1.0, Integer.MAX_VALUE);
        COMMON.pop();
        COMMON_CONFIG = COMMON.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

}
