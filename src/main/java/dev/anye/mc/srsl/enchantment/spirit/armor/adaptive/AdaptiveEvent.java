package dev.anye.mc.srsl.enchantment.spirit.armor.adaptive;

import dev.anye.mc.cores.entity.player.PlayerInvasionSlotCDT;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.cap.cooldown.PCDPro;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AdaptiveEvent {
    private static final float damageLimit = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_ADAPTIVE,"damageLimit");
    private static final float minDamage = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_ADAPTIVE,"minDamage");
    private static final int span = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_ADAPTIVE,"span");
    private static final int cooldown = (int)  EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.E_ADAPTIVE,"cooldown");
    private static final String INDEX_ITEM = "AdaptiveArmorCD";
    private static final String INDEX_TIME = "AdaptiveArmorTime";
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class AAE{
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            if (Adaptive.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){

                if (serverPlayer.getSlot(PlayerInvasionSlotCDT.CHEST_SLOT).get().getEnchantmentLevel(NekoEnchantments.oa_adaptive) > 0 ){
                    serverPlayer.getCapability(PCDPro.P_I_CD).ifPresent(playerCD -> {
                        if (playerCD.isData(INDEX_TIME)) {
                            serverPlayer.heal(event.getAmount());
                            event.setCanceled(true);
                        }else if (!playerCD.isData(INDEX_ITEM)){
                            if (event.getSource().getEntity() instanceof ServerPlayer player && player.getUUID() == serverPlayer.getUUID()) {
                                event.setCanceled(true);
                            } else {
                                float a = serverPlayer.getHealth() * damageLimit;
                                if (event.getAmount() > a) {
                                    event.setAmount(Math.max(a, minDamage));
                                }
                            }
                        }
                    });
                }
            }
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (Adaptive.ENABLE && event.getEntity() instanceof ServerPlayer player){
                if (player.getSlot(PlayerInvasionSlotCDT.CHEST_SLOT).get().getEnchantmentLevel(NekoEnchantments.oa_adaptive) > 0) {
                    player.getCapability(PCDPro.P_I_CD).ifPresent(playerCD -> {
                        if (playerCD.isData(INDEX_TIME)){
                            event.setCanceled(true);
                        }else if (!playerCD.isData(INDEX_ITEM)){
                            playerCD.setData(INDEX_ITEM, cooldown);
                            playerCD.setData(INDEX_TIME, span);
                            player.clearFire();
                            player.removeAllEffects();
                            player.setHealth(1.0F);
                            event.setCanceled(true);
                        }
                    });
                }
            }
        }
    }
}
