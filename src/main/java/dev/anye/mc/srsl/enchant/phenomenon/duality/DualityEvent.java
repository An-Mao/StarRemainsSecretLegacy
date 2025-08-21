package dev.anye.mc.srsl.enchant.phenomenon.duality;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.enchanting.GetEnchantmentLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.UUID;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class DualityEvent {
    public static final int maxDuality = 40;
    public static final int minDuality = -40;
    public static int ticks = 0;
    public static HashMap<UUID,Boolean> playerData = new HashMap<>();
    @SubscribeEvent
    public static void onBaby(BabyEntitySpawnEvent event){
        if (Duality.ENABLE && event.getChild() != null && event.getCausedByPlayer() instanceof ServerPlayer player){
            ItemStack item = player.getMainHandItem();
            if (EnchantHelper.hasEnchant(item, Enchants.duality)){
                CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
                CompoundTag dualityData = nbt.getCompound("duality.data");
                dualityData.putInt("duality",dualityData.getInt("duality")+1);
                nbt.put("duality.data",dualityData);
                EnchantHelper.setEnchantData(item,nbt);
            }
        }
    }
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event){
        if (Duality.ENABLE && event.getSource().getEntity() instanceof ServerPlayer player){
            ItemStack item = player.getMainHandItem();
            if (EnchantHelper.hasEnchant(item, Enchants.duality)){
                CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
                CompoundTag dualityData = nbt.getCompound("duality.data");
                dualityData.putInt("duality",Math.max(minDuality,dualityData.getInt("duality")-1));
                nbt.put("duality.data",dualityData);
                EnchantHelper.setEnchantData(item,nbt);
            }
        }
    }

    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
            ItemStack item = serverPlayer.getMainHandItem();
            if (EnchantHelper.hasEnchant(item, Enchants.duality)){
                for (ItemEntity drop : event.getDrops()) {
                    ItemStack di = drop.getItem();
                    di.setCount(di.getCount() * 2);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (Duality.ENABLE) {
            ticks++;
            if (event.getEntity() instanceof ServerPlayer serverPlayer && ticks > 600) {
                ticks = 0;
                ItemStack item = serverPlayer.getMainHandItem();
                if (EnchantHelper.hasEnchant(item, Enchants.duality)) {
                    boolean sub = playerData.getOrDefault(serverPlayer.getUUID(), true);
                    CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
                    CompoundTag dualityData = nbt.getCompound("duality.data");
                    int d = dualityData.getInt("duality");
                    if (sub) {
                        d++;
                    } else {
                        d--;
                    }
                    if (d >= maxDuality || d <= minDuality) {
                        playerData.put(serverPlayer.getUUID(), !sub);
                    }
                    dualityData.putInt("duality", d);
                    nbt.put("duality.data", dualityData);
                    EnchantHelper.setEnchantData(item, nbt);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLooting(GetEnchantmentLevelEvent event){
        if (Duality.ENABLE) {
            event.getHolder(Enchantments.LOOTING).ifPresent(enchantmentReference -> {
                Holder<Enchantment> loot = enchantmentReference.getDelegate();
                int looting = event.getEnchantments().getLevel(loot);
                ItemStack item = event.getStack();
                CompoundTag nbt = EnchantHelper.getAllData(item);
                CompoundTag dualityData = nbt.getCompound("duality.data");
                int a = dualityData.getInt("duality");
                if (a > 0) {
                    a /= 5;
                    looting += a;
                }
                event.getEnchantments().set(loot,looting);
            });

        }
    }


}
