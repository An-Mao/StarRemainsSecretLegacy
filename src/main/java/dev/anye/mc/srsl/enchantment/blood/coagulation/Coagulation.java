package dev.anye.mc.srsl.enchantment.blood.coagulation;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.blood.BloodEnchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Coagulation extends BloodEnchantment {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.B_COAGULATION);
    public Coagulation() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        super.doPostAttack(pAttacker, pTarget, pLevel);
        if (pTarget instanceof LivingEntity livingEntity){
            CompoundTag dat = livingEntity.getPersistentData();
            long gt = livingEntity.level().getGameTime();
            /*
            if (gt - dat.getInt("coagulation") < 1200){
                return;
            }
             */
            dat.putLong("coagulation",gt);
            dat.putFloat("coagulationHealth",livingEntity.getHealth());
        }
    }
}
