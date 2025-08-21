package dev.anye.mc.srsl.enchantment.curse.disease;

import dev.anye.mc.cores.effect.EffectHelper;
import dev.anye.mc.srsl.enchantment.curse.CurseEnchantmentCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class CurseDisease extends CurseEnchantmentCore {

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        pAttacker.addEffect(EffectHelper.getDeBuffInstance(100,3));
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, @NotNull Entity pAttacker, int pLevel) {
        pTarget.addEffect(EffectHelper.getDeBuffInstance(100,3));
        super.doPostHurt(pTarget, pAttacker, pLevel);
    }

}
