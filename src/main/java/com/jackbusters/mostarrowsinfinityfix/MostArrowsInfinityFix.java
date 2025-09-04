package com.jackbusters.mostarrowsinfinityfix;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MostArrowsInfinityFix implements ModInitializer {
    public static final String MOD_ID = "mostarrowsinfinityfix";
    public static List<ResourceLocation> resourceLocationBlacklist = new ArrayList<>();
    public static List<String> potionAsStringBlacklist = new ArrayList<>();
    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(MostArrowsInfinityFix.MOD_ID, ModConfig.Type.COMMON, ArrowCommonConfig.SPEC);
        for(String string : ArrowCommonConfig.infinityBlacklist.get())
        {
            resourceLocationBlacklist.add(ResourceLocation.tryParse(string));
        }
        potionAsStringBlacklist.addAll(ArrowCommonConfig.potionBlacklist.get());
    }

    /*
    Returns true if an ItemStack is not blacklisted and does not have blacklisted components.
 */
    public static boolean shouldHaveInfinity(ItemStack projectile) {
        return !isBlacklistedProjectile(projectile) && !hasBlacklistedEffect(projectile);
    }

    /*
        Returns true if an ItemStack is directly blacklisted.
     */
    public static boolean isBlacklistedProjectile(ItemStack projectile) {
        ResourceLocation itemResourceLocation = BuiltInRegistries.ITEM.getKey(projectile.getItem());
        return resourceLocationBlacklist.contains(itemResourceLocation);
    }

    /*
        Returns true if an ItemStack has blacklisted components.
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
