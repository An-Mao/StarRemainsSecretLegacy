package dev.anye.mc.srsl.enchant.neko.item.nekoking;


import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.neko$king.NekoKingConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;


public class NekoKingEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class ForgeEvent {
        @SubscribeEvent
        public static void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
            if (NekoKing.ENABLE && !anvilUpdateEvent.getPlayer().level().isClientSide && EnchantHelper.hasEnchant(anvilUpdateEvent.getLeft(), Enchants.ni_king)){
                //int sn = anvilUpdateEvent.getRight().getCount();
                //if (anvilUpdateEvent.getPlayer().experienceProgress < sn * 100){return;}
                Item ritem = anvilUpdateEvent.getRight().getItem();
                int re = NekoKingConfig.INSTANCE.getRefine(ritem);
                if (re > 0){
                    ItemStack oitem = anvilUpdateEvent.getLeft().copy();
                    int count = anvilUpdateEvent.getRight().getCount();
                    CompoundTag oitemnbt = EnchantHelper.getEnchantData(oitem).getTagCopy();
                    CompoundTag kingData = oitemnbt.getCompound("king.data");
                    kingData.putInt(NekoKing.ENCHANTMENT_KEY_REFINE,kingData.getInt(NekoKing.ENCHANTMENT_KEY_REFINE) + (re * count));
                    oitemnbt.put("king.data", kingData);
                    EnchantHelper.setEnchantData(oitem, oitemnbt);
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
