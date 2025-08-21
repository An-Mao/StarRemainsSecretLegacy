package dev.anye.mc.srsl.enchantment.neko.armor.nekosoul;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NekoSoulEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NSE{
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event){
            if(NekoSoul.ENABLE && !event.getEntity().level().isClientSide){
                if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
                    Iterable<ItemStack> slotlist = serverPlayer.getArmorSlots();
                    for (ItemStack slot : slotlist){
                        int lvl = slot.getEnchantmentLevel(NekoEnchantments.na_soul);
                        if (lvl > 0){
                            if (slot.getTag() != null) {
                                int soul = slot.getTag().getInt(NekoSoul.ENCHANTMENT_KEY_SOUL);
                                slot.getTag().putInt(NekoSoul.ENCHANTMENT_KEY_SOUL, soul+1);
                            }
                        }
                    }
                }
            }
        }
    }
}
