package dev.anye.mc.srsl.enchantment.blood.coagulation;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class CoagulationEvent {
    private static final int cTime = 1200;
    @SubscribeEvent
    public static void onHeal(LivingHealEvent event){
        if (Coagulation.ENABLE) {
            LivingEntity le = event.getEntity();
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            if (dat.getLong("coagulation") > 0 && gt - dat.getLong("coagulation") < cTime) {
                if (le.getLastDamageSource() != null) {
                    le.hurt(le.getLastDamageSource(), event.getAmount());
                } else {
                    le.hurt(le.damageSources().fellOutOfWorld(), event.getAmount());
                }
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event){
        if (Coagulation.ENABLE) {
            LivingEntity le = event.getEntity();
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            //System.out.println(gt);
            if (dat.getLong("coagulation") > 0 && gt - dat.getLong("coagulation") < cTime) {
                float oldHealth = dat.getFloat("coagulationHealth");
                float a = le.getHealth() - oldHealth;
                if (a > 0) {
                    a = oldHealth - a * 2;
                    dat.putFloat("coagulationHealth", a);
                    le.setHealth(Math.max(1, a));
                }
            }
        }
    }
}
