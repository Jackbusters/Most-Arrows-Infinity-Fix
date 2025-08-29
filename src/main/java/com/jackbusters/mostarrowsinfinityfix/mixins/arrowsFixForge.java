package com.jackbusters.mostarrowsinfinityfix.mixins;

import com.jackbusters.mostarrowsinfinityfix.MostArrowsInfinityFix;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * <h1>Forge Infinity Check</h1>
 * <p>This class injects a condition into the HEAD of {@link ArrowItem#isInfinite(ItemStack, ItemStack, LivingEntity)}
 * checking if an Item Stack is enchanted with Infinity.</p>
 * <p>If it is, the function is made to return true where the config allows.</p>
 */
@Mixin(ArrowItem.class)
public class arrowsFixForge {
    @Inject(at = @At("HEAD"), method = "isInfinite", cancellable = true, remap=false)
    public void isInfinite(ItemStack projectile, ItemStack bow, LivingEntity owner, CallbackInfoReturnable<Boolean> cir) {
        Holder<Enchantment> INFINITY = owner.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY);
        cir.setReturnValue(EnchantmentHelper.getItemEnchantmentLevel(INFINITY, bow) > 0 && MostArrowsInfinityFix.shouldHaveInfinity(projectile));
    }
}
