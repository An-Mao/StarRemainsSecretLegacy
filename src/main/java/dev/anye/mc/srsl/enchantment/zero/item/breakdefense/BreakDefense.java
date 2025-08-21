package dev.anye.mc.srsl.enchantment.zero.item.breakdefense;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BreakDefense extends ZeroItemE {
    private final float probability = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.Z_BREAK_DEFENSE,"probability");
    private static final MobEffectInstance BreakDefense = new MobEffectInstance(Effects.BREAK_DEFENSE.get(),1,200);
    public BreakDefense() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (pAttacker instanceof ServerPlayer){
            if (pTarget instanceof LivingEntity livingEntity) {
                if (_Math.RD.getIntRandomNumber(1,100) < pLevel * probability) {
                    livingEntity.addEffect(BreakDefense);
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
