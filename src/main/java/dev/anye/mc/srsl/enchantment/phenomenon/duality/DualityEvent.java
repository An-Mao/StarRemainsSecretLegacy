package dev.anye.mc.srsl.enchantment.phenomenon.duality;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DualityEvent {
    public static final int maxDuality = 40;
    public static final int minDuality = -40;
    public static int ticks = 0;
    public static HashMap<UUID,Boolean> playerData = new HashMap<>();
    @SubscribeEvent
    public static void onBaby(BabyEntitySpawnEvent event){
        if (Duality.ENABLE && event.getChild() != null && event.getCausedByPlayer() instanceof ServerPlayer player){
            ItemStack item = player.getMainHandItem();
            if (item.getEnchantmentLevel(NekoEnchantments.duality) > 0){
                if (item.getTag() != null) {
                    int d = item.getTag().getInt("duality");
                    item.getTag().putInt("duality",Math.min(maxDuality,d+1));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event){
        if (Duality.ENABLE && event.getSource().getEntity() instanceof ServerPlayer player){
            ItemStack item = player.getMainHandItem();
            if (item.getEnchantmentLevel(NekoEnchantments.duality) > 0){
                if (item.getTag() != null) {
                    int d = item.getTag().getInt("duality");
                    item.getTag().putInt("duality",Math.max(minDuality,d-1));
                }
            }
        }
    }

    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer){
            ItemStack item = serverPlayer.getMainHandItem();
            if (item.getEnchantmentLevel(NekoEnchantments.duality) > 0){
                for (ItemEntity drop : event.getDrops()) {
                    ItemStack di = drop.getItem();
                    di.setCount(di.getCount() * 2);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (Duality.ENABLE) {
            ticks++;
            if (event.player instanceof ServerPlayer serverPlayer && event.phase == TickEvent.Phase.END && ticks > 600) {
                ticks = 0;
                ItemStack item = serverPlayer.getMainHandItem();
                if (item.getEnchantmentLevel(NekoEnchantments.duality) > 0) {
                    boolean sub = playerData.getOrDefault(serverPlayer.getUUID(), true);
                    if (item.getTag() != null) {
                        int d = item.getTag().getInt("duality");
                        if (sub) {
                            d++;
                        } else {
                            d--;
                        }
                        if (d >= maxDuality || d <= minDuality) {
                            playerData.put(serverPlayer.getUUID(), !sub);
                        }
                        item.getTag().putInt("duality", d);
                    }
                }
            }
        }
    }
}
