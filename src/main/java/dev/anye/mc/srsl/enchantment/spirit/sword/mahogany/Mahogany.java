package dev.anye.mc.srsl.enchantment.spirit.sword.mahogany;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.spirit.sword.SSE;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class Mahogany extends SSE {
    private final float damage = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_MAHOGANY,"damage");
    public Mahogany() {
        super(Enchantment.Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide){
            if (pTarget instanceof Zombie zombie) {
                zombie.hurt(pAttacker.damageSources().fellOutOfWorld(),damage);
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
