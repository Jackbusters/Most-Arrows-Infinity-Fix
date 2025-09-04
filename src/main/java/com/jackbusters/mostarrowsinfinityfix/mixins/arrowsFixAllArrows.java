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
 * A mixin to the vanilla method for checking how many arrows should be used.
 * Makes return value 0 if configured conditions are met.
 */
@Mixin(EnchantmentHelper.class)
public class arrowsFixAllArrows {
    @Inject(method = "processAmmoUse", at = @At("HEAD"), cancellable = true)
    private static void hasInfiniteInjection(ServerLevel pLevel, ItemStack pWeapon, ItemStack pAmmo, int pCount, CallbackInfoReturnable<Integer> cir){
        Holder<Enchantment> INFINITY = pLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY);
        if(EnchantmentHelper.getItemEnchantmentLevel(INFINITY, pWeapon) > 0 && MostArrowsInfinityFix.shouldHaveInfinity(pAmmo))
            cir.setReturnValue(0);
        else cir.setReturnValue(1);
    }
}
