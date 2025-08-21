package dev.anye.mc.srsl.core;

import net.minecraft.world.entity.LivingEntity;

public class Cooldown {
    public static CoolDownData getCooldownData(LivingEntity livingEntity) {
        return livingEntity.getData(DataReg.CoolDown.get());
    }
}
