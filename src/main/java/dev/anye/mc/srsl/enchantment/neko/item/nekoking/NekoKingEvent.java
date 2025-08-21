package dev.anye.mc.srsl.enchantment.neko.item.nekoking;


import dev.anye.mc.cores.entity.player.PlayerHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.neko$king.NekoKingConfig;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class NekoKingEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class ForgeEvent {
        @SubscribeEvent
        public static void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
            if (NekoKing.ENABLE && !anvilUpdateEvent.getPlayer().level().isClientSide && anvilUpdateEvent.getLeft().getEnchantmentLevel(NekoEnchantments.ni_king) > 0){
                //int sn = anvilUpdateEvent.getRight().getCount();
                //if (anvilUpdateEvent.getPlayer().experienceProgress < sn * 100){return;}
                Item ritem = anvilUpdateEvent.getRight().getItem();
                int re = NekoKingConfig.INSTANCE.getRefine(ritem);
                if (re > 0){
                    ItemStack oitem = anvilUpdateEvent.getLeft().copy();
                    int count = anvilUpdateEvent.getRight().getCount();
                    CompoundTag oitemnbt = oitem.getTag();
                    if (oitemnbt != null) {
                        oitemnbt.putInt(NekoKing.ENCHANTMENT_KEY_REFINE,oitemnbt.getInt(NekoKing.ENCHANTMENT_KEY_REFINE) + (re * count));
                    }
                    oitem.setTag(oitemnbt);
                    int exp = NekoKingConfig.INSTANCE.getExp(ritem) * count;
                    exp = PlayerHelper.getLevelFromExperience(exp);
                    anvilUpdateEvent.setOutput(oitem);
                    anvilUpdateEvent.setCost(exp);
                    anvilUpdateEvent.setMaterialCost(count);
                }
            }
        }
    }
}
