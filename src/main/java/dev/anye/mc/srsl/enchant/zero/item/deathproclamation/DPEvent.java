package dev.anye.mc.srsl.enchant.zero.item.deathproclamation;

import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.srsl.SRSL;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class DPEvent {
    private static final String DEAD_TIP = "cn.srsl.death_pro";
    @SubscribeEvent
    public static void onTick(EntityTickEvent.Pre event){
        if (DeathProclamation.ENABLE && event != null && event.getEntity() instanceof Mob mob){
            int deathTime = mob.deathTime;
            if (deathTime == -1){
                if (mob.getLastDamageSource() == null) {
                    mob.die(mob.damageSources().fellOutOfWorld());
                }else {
                    mob.die(mob.getLastDamageSource());
                }
                event.getEntity().remove(Entity.RemovalReason.KILLED);
            }else if (deathTime < -1){
                //System.out.println(mob.deathTime);
                mob.deathTime ++;
                MutableComponent str = Component.translatable(DEAD_TIP).append(String.valueOf(mob.deathTime));
                mob.setCustomName(ComponentStyle.Flash(str.getString()));
            }
        }
    }
    @SubscribeEvent
    public static void onDead(LivingDeathEvent event){
        if (DeathProclamation.ENABLE && event.getEntity() instanceof Mob mob) {
            if (mob.deathTime < 0) {
                mob.setHealth(mob.getMaxHealth());
                event.setCanceled(true);
            }
        }
    }
}
