package dev.anye.mc.srsl.enchant.spirit.armor.adaptive;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.core.*;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class AdaptiveEvent {
    private static final float damageLimit = EnchantsConfig.INSTANCE.getValue(EnchantReg.E_ADAPTIVE,"damageLimit");
    private static final float minDamage = EnchantsConfig.INSTANCE.getValue(EnchantReg.E_ADAPTIVE,"minDamage");
    private static final int span = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.E_ADAPTIVE,"span");
    private static final int cooldown = (int)  EnchantsConfig.INSTANCE.getValue(EnchantReg.E_ADAPTIVE,"cooldown");
    private static final String INDEX_ITEM = "AdaptiveArmorCD";
    private static final String INDEX_TIME = "AdaptiveArmorTime";
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class AAE{
        @SubscribeEvent
        public static void onHurt(LivingIncomingDamageEvent event){
            if (Adaptive.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){

                if (EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_adaptive)){
                    CoolDownData coolDownData = Cooldown.getCooldownData(serverPlayer);
                    if (coolDownData.isCoolDown(INDEX_TIME)) {
                        serverPlayer.heal(event.getAmount());
                        event.setCanceled(true);
                    }else {
                        if (event.getSource().getEntity() instanceof ServerPlayer player && player.getUUID() == serverPlayer.getUUID()) {
                            event.setCanceled(true);
                        } else {
                            float a = serverPlayer.getHealth() * damageLimit;
                            if (event.getAmount() > a) {
                                event.setAmount(Math.max(a, minDamage));
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (Adaptive.ENABLE && event.getEntity() instanceof ServerPlayer player){
                if (EnchantHelper.hasEnchant(player.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_adaptive)) {
                    CoolDownData coolDownData = Cooldown.getCooldownData(player);
                    if (coolDownData.isCoolDown(INDEX_TIME)){
                        event.setCanceled(true);
                    }else {
                        coolDownData.setCooldown(INDEX_ITEM, cooldown);
                        coolDownData.setCooldown(INDEX_TIME, span);
                        player.clearFire();
                        player.removeAllEffects();
                        player.setHealth(1.0F);
                        event.setCanceled(true);
                    }

                }
            }
        }
    }
}
