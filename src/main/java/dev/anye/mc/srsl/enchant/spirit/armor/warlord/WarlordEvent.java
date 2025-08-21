package dev.anye.mc.srsl.enchant.spirit.armor.warlord;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.CoolDownData;
import dev.anye.mc.srsl.core.Cooldown;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class WarlordEvent {

    private static final String KEY_CD = "WarlordArmorCD";
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class WE {
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (Warlord.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                ItemStack item =  serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
                if (EnchantHelper.hasEnchant(item, Enchants.oa_warlord)) {
                    CoolDownData coolDownData = Cooldown.getCooldownData(serverPlayer);
                    if (!coolDownData.isCoolDown(KEY_CD)) {
                        //System.out.println("health:" + serverPlayer.getHealth());
                        if (serverPlayer.getHealth() == serverPlayer.getMaxHealth()) {
                            coolDownData.setCooldown(KEY_CD, 2000);
                            serverPlayer.setHealth(3.0f);
                            event.setCanceled(true);
                        }
                    }

                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingDamageEvent.Pre event){
            if (Warlord.ENABLE) {
                if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
                    ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
                    if (EnchantHelper.hasEnchant(item, Enchants.oa_warlord)) {
                        float sp = 2.0F - serverPlayer.getHealth() / serverPlayer.getMaxHealth();
                        event.setNewDamage(event.getNewDamage() * Math.min(sp, 1.75f));
                    }
                }
                if (event.getEntity() instanceof ServerPlayer Player) {
                    ItemStack oItem = Player.getSlot(PlayerHelper.SLOT_CHEST).get();
                    if (EnchantHelper.hasEnchant(oItem, Enchants.oa_warlord)) {
                        event.setNewDamage(event.getNewDamage() * 1.3F);
                    }
                }
            }
        }
    }
}
