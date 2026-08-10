package dev.anye.mc.srsl.enchant.spirit.sword.mahogany;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.spirit.sword.SSE;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;

import org.jetbrains.annotations.NotNull;

public class Mahogany extends SSE {
    private final float damage = EnchantsConfig.INSTANCE.getValue(EnchantReg.E_MAHOGANY,"damage");
    public Mahogany() {
        super();
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide()){
            if (pTarget instanceof Zombie zombie) {
                zombie.hurt(pAttacker.damageSources().fellOutOfWorld(),damage);
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
