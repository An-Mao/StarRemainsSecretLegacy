package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Unique
    private static final boolean nekoEnchantment$enable = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.Z_INDESTRUCTIBLE);
    @Shadow public abstract ItemStack getItem();

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void nekoEnchantment$hurt$preventDestroy(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (nekoEnchantment$enable && getItem().getEnchantmentLevel(NekoEnchantments.zi_indestructible)>0){
            cir.setReturnValue(false);
        }
    }
}
