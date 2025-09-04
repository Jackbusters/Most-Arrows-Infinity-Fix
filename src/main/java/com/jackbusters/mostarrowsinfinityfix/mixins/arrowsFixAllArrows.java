package com.jackbusters.mostarrowsinfinityfix.mixins;

import com.jackbusters.mostarrowsinfinityfix.MostArrowsInfinityFix;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 <h1>Vanilla Infinity Check</h1>
 <p>The {@link EnchantmentHelper#processAmmoUse(ServerLevel, ItemStack, ItemStack, int)}
 function returns the amount of ammo (arrows) a projectile weapon should use when fired. </p>
 <p>
 It runs the checks for the Infinity enchantment on ammo, and returns 0 if the ammo type should not be used.
 </p>

 <p>This mixin injects to the head of that function, adds a condition simply checking if the weapon has infinity, and returns 0 if it does.</p>
 */
@Mixin(EnchantmentHelper.class)
public class arrowsFixAllArrows {
    @Inject(method = "processAmmoUse", at = @At("HEAD"), cancellable = true)
    private static void hasInfiniteInjection(ServerLevel pLevel, ItemStack pWeapon, ItemStack pAmmo, int pCount, CallbackInfoReturnable<Integer> cir){
        Holder<Enchantment> INFINITY = pLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY);
        if(EnchantmentHelper.getTagEnchantmentLevel(INFINITY, pWeapon) > 0 && MostArrowsInfinityFix.shouldHaveInfinity(pAmmo))
            cir.setReturnValue(0);
        else cir.setReturnValue(1);
    }
}
