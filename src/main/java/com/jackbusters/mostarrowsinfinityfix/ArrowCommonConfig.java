package com.jackbusters.mostarrowsinfinityfix;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ArrowCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> infinityBlacklist;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> potionBlacklist;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("MAIF Config");

        infinityBlacklist = BUILDER.comment("Configurable list of arrows which should NOT work with the infinity enchantment.").worldRestart().defineList(
                "infinityBlacklist", List.of(
                        "xtraarrows:flint_torch_arrow", "xtraarrows:iron_torch_arrow", "xtraarrows:golden_torch_arrow", "xtraarrows:diamond_torch_arrow", "xtraarrows:netherite_torch_arrow",
                        "xtraarrows:flint_redstone_torch_arrow", "xtraarrows:iron_redstone_torch_arrow", "xtraarrows:golden_redstone_torch_arrow", "xtraarrows:diamond_redstone_torch_arrow", "xtraarrows:netherite_redstone_torch_arrow",
                        "xtraarrows:flint_soul_torch_arrow", "xtraarrows:iron_soul_torch_arrow", "xtraarrows:golden_soul_torch_arrow", "xtraarrows:diamond_soul_torch_arrow", "xtraarrows:netherite_soul_torch_arrow",
                        "xtraarrows:flint_explosive_arrow", "xtraarrows:iron_explosive_arrow", "xtraarrows:golden_explosive_arrow", "xtraarrows:diamond_explosive_arrow", "xtraarrows:netherite_explosive_arrow",
                        "xtraarrows:flint_lightning_arrow", "xtraarrows:iron_lightning_arrow", "xtraarrows:golden_lightning_arrow", "xtraarrows:diamond_lightning_arrow", "xtraarrows:netherite_lightning_arrow",
                        "xtraarrows:golden_apple_arrow", "xtraarrows:notch_apple_arrow", "xtraarrows:apples_arrow",
                        "xtraarrows:flint_lantern_arrow", "xtraarrows:iron_lantern_arrow", "xtraarrows:golden_lantern_arrow", "xtraarrows:diamond_lantern_arrow", "xtraarrows:netherite_lantern_arrow",
                        "xtraarrows:flint_soul_lantern_arrow", "xtraarrows:iron_soul_lantern_arrow", "xtraarrows:golden_soul_lantern_arrow", "xtraarrows:diamond_soul_lantern_arrow", "xtraarrows:netherite_soul_lantern_arrow",
                        "supplementaries:rope_arrow"),
                li -> li instanceof String);

        potionBlacklist = BUILDER.comment("""
                
                List of tipped arrows which should NOT work with the infinity enchantment. Any Component value applicable to the 'Potion' tag will work.
                To find the correct Component value for a particular arrow, run the command '/data get entity @s SelectedItem' while holding the tipped arrow you wish to blacklist.""").worldRestart().defineList(
                "potionBlacklist", List.of("minecraft:water_breathing", "minecraft:long_water_breathing", "minecraft:regeneration",
                        "minecraft:strong_regeneration", "minecraft:long_regeneration", "minecraft:healing", "minecraft:strong_healing",
                        "minecraft:harming", "minecraft:strong_harming", "minecraft:weakness", "minecraft:long_weakness"), li -> li instanceof String);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
