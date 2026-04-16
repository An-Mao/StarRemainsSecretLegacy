package dev.anye.mc.srsl.enchant.zero.item.alone;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantData;
import dev.anye.mc.srsl.core.DataReg;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class AloneEvent {
    @SubscribeEvent
    public static void onAttack(LivingDamageEvent.Post event){
        if (Alone.ENABLE && event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
            ItemStack item = serverPlayer.getMainHandItem();
            if (EnchantHelper.hasEnchant(item, Enchants.zi_alone) && EnchantHelper.getEnchantCompound(item).size() == 1){
                int amount = 0;
                EnchantData enchantData = EnchantHelper.getEnchantData(item);
                CompoundTag nbt = EnchantHelper.getAllData(enchantData);
                CompoundTag aloneData = nbt.getCompound("alone.data").orElse(new CompoundTag());
                amount = aloneData.getInt(Alone.ENCHANTMENT_KEY_ALONE).orElse(0);
                aloneData.putInt(Alone.ENCHANTMENT_KEY_ALONE,amount+1);
                nbt.put("alone.data",aloneData);
                enchantData = new EnchantData(nbt);
                item.set(DataReg.Enchant_Data.get(),enchantData);
            }
        }
    }
}
