package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerLevel;
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
    private static final boolean srsl$enable = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_INDESTRUCTIBLE);
    @Shadow public abstract ItemStack getItem();

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void srsl$hurt$preventDestroy(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (srsl$enable && EnchantHelper.hasEnchant(getItem(), Enchants.zi_indestructible)){
            cir.setReturnValue(false);
        }
    }
}
