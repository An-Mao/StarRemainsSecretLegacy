package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.core.EnchantHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnchantmentHelper.class, remap = false)
public class EnchantmentHelperMixin {
	private EnchantmentHelperMixin() {
	}

	@Inject(method = "modifyDamage", at = @At("RETURN"), cancellable = true)
	private static void srsl$modifyDamage$enchant(ServerLevel serverLevel, ItemStack itemStack, Entity victim, DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
		float[] d = {0};
		EnchantHelper.getEnchants(itemStack).forEach((ench, level) -> d[0] += ench.getDamageBonus(level, victim, itemStack));
		cir.setReturnValue(cir.getReturnValue() + d[0]);
	}
}
