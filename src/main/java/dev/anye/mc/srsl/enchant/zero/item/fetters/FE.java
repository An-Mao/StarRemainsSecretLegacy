package dev.anye.mc.srsl.enchant.zero.item.fetters;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class FE {
    @SubscribeEvent
    public static void onTick(EntityTickEvent.Pre event){
        if (Fetters.ENABLE && event.getEntity() instanceof LivingEntity le) {
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            //System.out.println(gt);
            if (dat.getLong("Fetters").orElse(0l) > 0 && gt - dat.getLong("Fetters").orElse(0l) < 1200) {
                le.teleportTo(dat.getDouble("Fx").orElse(0d), dat.getDouble("Fy").orElse(0d), dat.getDouble("Fz").orElse(0d));
            }
        }
    }
}
