package dev.anye.mc.srsl.enchant.spirit.armor.stance;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

public class StanceEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class SE{
        @SubscribeEvent
        public static void onHurt(LivingDamageEvent.Pre event){
            if (Stance.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                ItemStack item =  serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
                if (EnchantHelper.hasEnchant(item, Enchants.oa_stance)){
                    if (event.getSource().getEntity() != null) {
                        double dis = serverPlayer.getEyePosition().distanceTo(event.getSource().getEntity().getEyePosition());
                        if (dis > 3){
                            event.setNewDamage(event.getNewDamage() * 0.8F);
                        }else {
                            event.setNewDamage(event.getNewDamage() * 1.7F);
                        }
                    }
                }
            }
        }
        /*
        public static double getPlayerAtkRange(ServerPlayer serverPlayer){
            Attribute atkAtt = ForgeMod.ENTITY_REACH.get();
            double atkRange = serverPlayer.getAttributeBaseValue(Attributes.RE);
            atkRange += serverPlayer.getAttributeValue(atkAtt);

            return atkRange;
        }

         */
    }
}
