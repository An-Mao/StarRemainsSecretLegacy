package dev.anye.mc.srsl.enchantment.spirit.armor.redlotus;

import dev.anye.mc.cores.entity.player.PlayerInvasionSlotCDT;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RedLotusEvent {
    private static final MobEffectInstance EvilCreature = new MobEffectInstance(Effects.EVIL_CREATURE.get(),1,20);
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class RLE{
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            if (RedLotus.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                if (serverPlayer.getSlot(PlayerInvasionSlotCDT.CHEST_SLOT).get().getEnchantmentLevel(NekoEnchantments.oa_red_lotus) > 0){
                    if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                        livingEntity.hurt(serverPlayer.damageSources().fellOutOfWorld(),event.getAmount()* 0.5f);
                        livingEntity.addEffect(EvilCreature);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDamage(LivingDamageEvent event){
            if (RedLotus.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
                ItemStack item = serverPlayer.getSlot(PlayerInvasionSlotCDT.CHEST_SLOT).get();
                if (item.getEnchantmentLevel(NekoEnchantments.oa_red_lotus) > 0){
                    if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                        if (livingEntity.hasEffect(Effects.EVIL_CREATURE.get())){
                            item.setDamageValue(Math.min(item.getDamageValue()+1,item.getMaxDamage()));
                        }
                    }
                }
            }
        }
    }
}
