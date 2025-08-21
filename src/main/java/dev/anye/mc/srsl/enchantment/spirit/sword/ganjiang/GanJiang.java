package dev.anye.mc.srsl.enchantment.spirit.sword.ganjiang;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import dev.anye.mc.srsl.enchantment.spirit.sword.SSE;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class GanJiang extends SSE {
    private final float probability = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_GAN_JIANG,"probability");

    public GanJiang() {
        super(Enchantment.Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide) {
            if (pAttacker.getOffhandItem().getEnchantmentLevel(NekoEnchantments.oi_mo_ye) >0) {
                if (_Math.RD.getRandomNumber(1, 100) <= probability) {
                    pTarget.kill();
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
