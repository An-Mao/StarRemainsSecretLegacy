package dev.anye.mc.srsl.enchant.spirit.armor.redlotus;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class RedLotusEvent {
    private static final MobEffectInstance EvilCreature = new MobEffectInstance(Effects.EVIL_CREATURE.getDelegate(),1,20);
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class RLE{
        @SubscribeEvent
        public static void onHurt(LivingDamageEvent.Pre event){
            if (RedLotus.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                if (EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_red_lotus)){
                    if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                        livingEntity.hurt(serverPlayer.damageSources().fellOutOfWorld(),event.getNewDamage()* 0.5f);
                        livingEntity.addEffect(EvilCreature);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDamage(LivingDamageEvent.Post event){
            if (RedLotus.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
                if (EnchantHelper.hasEnchant(item, Enchants.oa_red_lotus)){
                    if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                        if (livingEntity.hasEffect(Effects.EVIL_CREATURE.getDelegate())){
                            item.setDamageValue(Math.min(item.getDamageValue()+1,item.getMaxDamage()));
                        }
                    }
                }
            }
        }
    }
}
