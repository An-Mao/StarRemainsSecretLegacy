package dev.anye.mc.srsl.enchant.spirit.armor.natural;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.CoolDownData;
import dev.anye.mc.srsl.core.Cooldown;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class NaturalEvent {
    private static final String KEY_CD = "NaturalArmorCD";
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static   class  NAE{
        @SubscribeEvent
        public static void onEquipmentChange (LivingEquipmentChangeEvent event){
            if (Natural.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                if (event.getSlot() == EquipmentSlot.CHEST){
                    ItemStack item = event.getFrom();
                    if (EnchantHelper.hasEnchant(item, Enchants.oa_natural)) {
                        serverPlayer.getAttribute(Attributes.MAX_HEALTH)
                                .setBaseValue(serverPlayer.getMaxHealth() - 10);
                    }
                    item = event.getTo();
                    if (EnchantHelper.hasEnchant(item, Enchants.oa_natural)) {
                        serverPlayer.getAttribute(Attributes.MAX_HEALTH)
                                .setBaseValue(serverPlayer.getMaxHealth() + 10);
                    }
                    if (serverPlayer.getHealth() > serverPlayer.getMaxHealth()) {
                        serverPlayer.setHealth(serverPlayer.getMaxHealth());
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onServerTick (PlayerTickEvent.Pre event){
            if (Natural.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                    if (EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_natural)) {
                        CoolDownData coolDownData = Cooldown.getCooldownData(serverPlayer);
                        if (!coolDownData.isCoolDown(KEY_CD)) {
                            coolDownData.setCooldown(KEY_CD, 60);
                            serverPlayer.heal(serverPlayer.getMaxHealth() * 0.1f);
                        }
                    }

            }
        }
    }
}
