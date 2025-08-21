package dev.anye.mc.srsl.enchantment.zero.item.alone;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class AloneEvent {
    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event){
        if (Alone.ENABLE && event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
            ItemStack item = serverPlayer.getMainHandItem();
            if (item.getEnchantmentLevel(NekoEnchantments.zi_alone) >0 && item.getAllEnchantments().size() == 1){
                int amount = 0;
                if (item.getTag() != null) {
                    amount = item.getTag().getInt(Alone.ENCHANTMENT_KEY_ALONE);
                }
                item.getTag().putInt(Alone.ENCHANTMENT_KEY_ALONE,amount+1);
            }
        }
    }
}
