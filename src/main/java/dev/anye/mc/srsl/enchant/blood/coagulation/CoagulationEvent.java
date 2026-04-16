package dev.anye.mc.srsl.enchant.blood.coagulation;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class CoagulationEvent {
    private static final int cTime = 1200;
    @SubscribeEvent
    public static void onHeal(LivingHealEvent event){
        if (Coagulation.ENABLE) {
            LivingEntity le = event.getEntity();
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            if (dat.getLong("coagulation").orElse(0L) > 0 && gt - dat.getLong("coagulation").orElse(0l) < cTime) {
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
    public static void onTick(EntityTickEvent.Pre event){
        if (Coagulation.ENABLE && event.getEntity() instanceof LivingEntity le) {
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            //System.out.println(gt);
            if (dat.getLong("coagulation").orElse(0l) > 0 && gt - dat.getLong("coagulation").orElse(0l) < cTime) {
                float oldHealth = dat.getFloat("coagulationHealth").orElse(0f);
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
