package dev.anye.mc.srsl.enchant.neko.armor.nekosoul;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NekoSoulEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NSE{
        @SubscribeEvent
        public static void onAttack(LivingIncomingDamageEvent event){
            if(NekoSoul.ENABLE && !event.getEntity().level().isClientSide){
                if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
                    Iterable<ItemStack> slotlist = serverPlayer.getArmorSlots();
                    for (ItemStack slot : slotlist){
                        if (EnchantHelper.hasEnchant(slot, Enchants.na_soul)) {
                            CompoundTag nbt = EnchantHelper.getEnchantData(slot).getTagCopy();
                            CompoundTag soulData = nbt.getCompound("soul.data");
                            int soul = soulData.getInt(NekoSoul.ENCHANTMENT_KEY_SOUL);
                            soulData.putInt(NekoSoul.ENCHANTMENT_KEY_SOUL, soul + 1);
                            nbt.put("soul.data", soulData);
                            EnchantHelper.setEnchantData(slot, nbt);
                        }
                    }
                }
            }
        }
    }
}
