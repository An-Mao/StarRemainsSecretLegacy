package dev.anye.mc.srsl.enchantment.spirit.sword.moye;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import dev.anye.mc.srsl.enchantment.spirit.sword.SSE;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class MoYe extends SSE {
    private final float probability = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_MO_YE,"probability");
    public MoYe() {
        super(Enchantment.Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide) {
            if (pAttacker.getOffhandItem().getEnchantmentLevel(NekoEnchantments.oi_gan_jiang) > 0) {
                if (_Math.RD.getRandomNumber(1, 100) <= probability) {
                    pTarget.kill();
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
