package dev.anye.mc.srsl.enchantment.zero.item.fetters;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class FE {
    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event){
        if (Fetters.ENABLE) {
            LivingEntity le = event.getEntity();
            CompoundTag dat = le.getPersistentData();
            long gt = le.level().getGameTime();
            //System.out.println(gt);
            if (dat.getLong("Fetters") > 0 && gt - dat.getLong("Fetters") < 1200) {
                le.teleportTo(dat.getDouble("Fx"), dat.getDouble("Fy"), dat.getDouble("Fz"));
            }
        }
    }
}
