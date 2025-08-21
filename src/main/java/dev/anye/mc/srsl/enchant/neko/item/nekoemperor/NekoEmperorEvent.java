package dev.anye.mc.srsl.enchant.neko.item.nekoemperor;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.DataReg;
import dev.anye.mc.srsl.core.EnchantData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NekoEmperorEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NEE{
        public static final BigDecimal q = BigDecimal.valueOf(NekoEmperor.quota);
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent livingDeathEvent){
            if (NekoEmperor.ENABLE && livingDeathEvent.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
                Iterable<ItemStack> handlist = serverPlayer.getHandSlots();
                for (ItemStack itemStack : handlist) {
                    if (EnchantHelper.hasEnchant(itemStack, Enchants.ni_emperor)) {
                        CompoundTag nbt = EnchantHelper.getEnchantData(itemStack).getTagCopy();
                        CompoundTag killData = nbt.getCompound("emperor.data");
                        double kill = killData.getDouble("k1");
                        double kill2 = killData.getDouble("k2");
                        kill+=livingDeathEvent.getEntity().getMaxHealth();
                        if (kill < 0) {
                            kill += Double.MAX_VALUE;
                            kill2 ++;
                        }
                        BigDecimal ks = BigDecimal.valueOf(kill);
                        for (int i = 0; i < kill2; i++) {
                            ks = ks.add(BigDecimal.valueOf(Double.MAX_VALUE));
                        }
                        ks=ks.divide(q,2, RoundingMode.HALF_UP);
                        killData.putDouble("k1", kill);
                        killData.putDouble("k2", kill2);
                        killData.putDouble(NekoEmperor.ENCHANTMENT_KEY_KILL, ks.doubleValue());
                        nbt.put("emperor.data", killData);
                        itemStack.set(DataReg.Enchant_Data.get(), new EnchantData(nbt));
                        EnchantHelper.setEnchantData(itemStack, nbt);
                    }
                }
            }
        }
    }
}
