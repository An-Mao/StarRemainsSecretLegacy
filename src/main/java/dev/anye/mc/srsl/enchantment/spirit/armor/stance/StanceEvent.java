package dev.anye.mc.srsl.enchantment.spirit.armor.stance;

import dev.anye.mc.cores.entity.player.PlayerInvasionSlotCDT;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class StanceEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class SE{
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            if (Stance.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                ItemStack item =  serverPlayer.getSlot(PlayerInvasionSlotCDT.CHEST_SLOT).get();
                if (item.getEnchantmentLevel(NekoEnchantments.oa_stance) > 0){
                    if (event.getSource().getEntity() != null) {
                        double dis = serverPlayer.getEyePosition().distanceTo(event.getSource().getEntity().getEyePosition());
                        if (dis > getPlayerAtkRange(serverPlayer)){
                            event.setAmount(event.getAmount() * 0.8F);
                        }else {
                            event.setAmount(event.getAmount() * 1.7F);
                        }
                    }
                }
            }
        }
        public static double getPlayerAtkRange(ServerPlayer serverPlayer){
            Attribute atkAtt = ForgeMod.ENTITY_REACH.get();
            double atkRange = serverPlayer.getAttributeBaseValue(atkAtt);
            atkRange += serverPlayer.getAttributeValue(atkAtt);

            return atkRange;
        }
    }
}
