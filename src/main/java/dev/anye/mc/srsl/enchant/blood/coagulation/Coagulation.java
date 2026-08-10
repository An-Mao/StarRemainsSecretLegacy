package dev.anye.mc.srsl.enchant.blood.coagulation;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.blood.BloodEnchant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Coagulation extends BloodEnchant {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.B_COAGULATION);
    public Coagulation() {
        super();
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
