package com.jackbusters.mostarrowsinfinityfix;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Mod(MostArrowsInfinityFix.MOD_ID)
public class MostArrowsInfinityFix {
    public static final String MOD_ID = "mostarrowsinfinityfix";
    public static List<ResourceLocation> resourceLocationBlacklist = new ArrayList<>();
    public static List<String> potionAsStringBlacklist = new ArrayList<>();


    public MostArrowsInfinityFix(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::setup);
        modContainer.registerConfig(ModConfig.Type.COMMON, ArrowCommonConfig.SPEC);
    }

    /**
     * Loads the blacklists into memory as ArrayLists upon start up for fast referencing.
     * @param event Forge event.
     */
    public void setup(final FMLCommonSetupEvent event) {
        for (String string : ArrowCommonConfig.infinityBlacklist.get())
            resourceLocationBlacklist.add(ResourceLocation.tryParse(string));
        potionAsStringBlacklist.addAll(ArrowCommonConfig.potionBlacklist.get());
    }

    /**
     * Determines whether item should have infinity based on the blacklists.
     * @param projectile The projectile item to check.
     * @return True if the projectile does not appear in either the item id blacklist or effect blacklist. False otherwise.
     */
    public static boolean shouldHaveInfinity(ItemStack projectile) {
        return !isBlacklistedProjectile(projectile) && !hasBlacklistedEffect(projectile);
    }

    /**
     * Determines whether item's ID is blacklisted.
     * @param projectile The projectile item to check.
     * @return True if passed ItemStack's ID appears in the item ID blacklist. False otherwise.
     */
    public static boolean isBlacklistedProjectile(ItemStack projectile) {
        ResourceLocation itemResourceLocation = BuiltInRegistries.ITEM.getKey(projectile.getItem());
        return resourceLocationBlacklist.contains(itemResourceLocation);
    }

    /**
     * Determines whether item has blacklisted potion effects.
     * @param projectile The projectile item to check.
     * @return True if ItemStack has blacklisted effect data.
     */
    public static boolean hasBlacklistedEffect(ItemStack projectile) {
        DataComponentMap componentMap = projectile.getComponents();
        if (componentMap.get(DataComponents.POTION_CONTENTS) != null) {
            try {
                Potion potionOnProjectile = Objects.requireNonNull(componentMap.get(DataComponents.POTION_CONTENTS)).potion().orElseThrow().value(); // The potion affecting the current projectile.
                return potionAsStringBlacklist.contains(Objects.requireNonNull(BuiltInRegistries.POTION.getKey(potionOnProjectile)).toString()); // Returns true if ResourceLocation of potion effect of projectile matches any on the potion blacklist
            }
            catch(NoSuchElementException exception) {
                return false; // If for some reason a crash would occur, instead just return false. This fixes a crash that occurs when a user tried to shoot the uncraftable tipped arrow.
            }
        }
        return false;
    }
}
