package dev.anye.mc.srsl.enchant.phenomenon.theworld;

import dev.anye.mc.cores.am.data.entity.TheWorld;
import dev.anye.mc.cores.am.register.DataRegister;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class TheWorldEvent {
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event){
        if (TheWorldEnchant.ENABLE) {
            Entity entity = event.getEntity();
            Entity sEntity = event.getSource().getEntity();
            if ( sEntity == null) {
                return;
            }
            if (entity instanceof ServerPlayer player && !(sEntity instanceof ServerPlayer)) {

                if (EnchantHelper.getEnchantLevel(Enchants.the_world, player) > 0) {
                    setTheWorldTime(sEntity);
                }
            } else if (sEntity instanceof ServerPlayer serverPlayer) {
                if (EnchantHelper.hasEnchant(serverPlayer.getMainHandItem(), Enchants.the_world)) {
                    setTheWorldTime(entity);
                }
            }
        }
    }
    public static void setTheWorldTime(Entity e){
        e.setData(DataRegister.THE_WORLD,new TheWorld(TheWorldEnchant.time));
    }
}
