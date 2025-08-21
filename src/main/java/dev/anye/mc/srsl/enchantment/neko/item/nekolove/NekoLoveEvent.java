package dev.anye.mc.srsl.enchantment.neko.item.nekolove;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

public class NekoLoveEvent {
    /*
    @Mod.EventBusSubscriber(modid = NekoEnchantment.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class NLES{
        private static List<Player> playerList = new ArrayList<>();

        public static List<Player> getPlayerList() {
            MinecraftForge.EVENT_BUS.register(NLES.class);
            return playerList;
        }

        @SubscribeEvent
        public static void onTick(TickEvent.ServerTickEvent event){
            if (event.phase == TickEvent.Phase.END){
                playerList.clear();
                for (Player player: //) {
                    playerList.add(player);
                }
                MinecraftForge.EVENT_BUS.unregister(NLES.class);
            }
        }
    }

     */
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NLE{
        @SubscribeEvent
        public static void onSpawn(EntityJoinLevelEvent event){
            if (NekoLove.ENABLE && event.getEntity() instanceof ItemEntity item && !item.level().isClientSide){
                ItemStack oitem = item.getItem();
                if (oitem.getEnchantmentLevel(NekoEnchantments.ni_love) > 0){
                    if (oitem.getTag() != null) {
                        if (oitem.getTag().hasUUID(NekoLove.ENCHANTMENT_KEY_LOVE)) {
                            UUID uuid = oitem.getTag().getUUID(NekoLove.ENCHANTMENT_KEY_LOVE);
                            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                            if (server != null) {
                                ServerPlayer serverPlayer = server.getPlayerList().getPlayer(uuid);
                                if (serverPlayer != null) {
                                    Inventory playerInventory = serverPlayer.getInventory();
                                    NonNullList<ItemStack> items = playerInventory.items;
                                    boolean isIn = false;
                                    for (ItemStack itemStack:items){
                                        if (itemStack.isEmpty()){
                                            isIn = true;
                                            serverPlayer.getInventory().placeItemBackInInventory(oitem);
                                            event.setCanceled(true);
                                            break;
                                        }
                                    }
                                    if (!isIn){
                                        ItemStack handItem = serverPlayer.getMainHandItem();
                                        if (handItem.getEnchantmentLevel(NekoEnchantments.ni_love) > 0){
                                            handItem = serverPlayer.getOffhandItem();
                                            if (handItem.getEnchantmentLevel(NekoEnchantments.ni_love) > 0) {
                                                item.teleportTo(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                                            }else {
                                                Direction playerFacing = serverPlayer.getDirection();
                                                BlockPos playerPos = serverPlayer.blockPosition();
                                                BlockPos dropPos = playerPos.relative(playerFacing,2);
                                                ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), dropPos.getX() + 0.5, dropPos.getY(), dropPos.getZ() + 0.5, handItem.copy());
                                                itemEntity.setDeltaMovement(0, 0.1, 0);
                                                serverPlayer.level().addFreshEntity(itemEntity);
                                                serverPlayer.setItemInHand(InteractionHand.OFF_HAND,oitem);
                                                event.setCanceled(true);
                                            }
                                        }else {
                                            Direction playerFacing = serverPlayer.getDirection();
                                            BlockPos playerPos = serverPlayer.blockPosition();
                                            BlockPos dropPos = playerPos.relative(playerFacing,2);
                                            ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), dropPos.getX() + 0.5, dropPos.getY(), dropPos.getZ() + 0.5, handItem.copy());
                                            itemEntity.setDeltaMovement(0, 0.1, 0);
                                            serverPlayer.level().addFreshEntity(itemEntity);
                                            serverPlayer.setItemInHand(InteractionHand.MAIN_HAND,oitem);
                                            //serverPlayer.getInventory().placeItemBackInInventory(oitem);
                                            event.setCanceled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
            if (NekoLove.ENABLE && !anvilUpdateEvent.getPlayer().level().isClientSide && anvilUpdateEvent.getLeft().getEnchantmentLevel(NekoEnchantments.ni_love) > 0 ){
                if (anvilUpdateEvent.getLeft().getTag() != null && anvilUpdateEvent.getLeft().getTag().hasUUID(NekoLove.ENCHANTMENT_KEY_LOVE)){
                    return;
                }
                Item ritem = anvilUpdateEvent.getRight().getItem();
                if (ritem == Items.TROPICAL_FISH){
                    ItemStack oitem = anvilUpdateEvent.getLeft().copy();
                    CompoundTag oitemnbt = oitem.getTag();
                    if (oitemnbt != null) {
                        oitemnbt.putUUID(NekoLove.ENCHANTMENT_KEY_LOVE,anvilUpdateEvent.getPlayer().getUUID());
                    }
                    oitem.setTag(oitemnbt);
                    anvilUpdateEvent.setOutput(oitem);
                    anvilUpdateEvent.setCost(1);
                    anvilUpdateEvent.setMaterialCost(1);
                }
            }
        }
        @SubscribeEvent
        public static void onItemUse(LivingEntityUseItemEvent.Start event){
            if (NekoLove.ENABLE && !event.getEntity().level().isClientSide){
                LivingEntity e = event.getEntity();
                ItemStack oitem = event.getItem();
                if (oitem.getTag() != null && oitem.getTag().hasUUID(NekoLove.ENCHANTMENT_KEY_LOVE)) {
                    UUID uuid = oitem.getTag().getUUID(NekoLove.ENCHANTMENT_KEY_LOVE);
                    if (!e.getUUID().equals(uuid)){
                        e.hurt(e.damageSources().fellOutOfWorld(),e.getMaxHealth() * 0.2F);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onToss(ItemTossEvent event) {
            ItemEntity entity = event.getEntity();
            if (NekoLove.ENABLE && !entity.level().isClientSide){
                ItemStack ditem = entity.getItem();
                if (ditem.getEnchantmentLevel(NekoEnchantments.ni_love) > 0){
                    if (ditem.getTag() != null && ditem.getTag().hasUUID(NekoLove.ENCHANTMENT_KEY_LOVE)) {
                        UUID uuid = ditem.getTag().getUUID(NekoLove.ENCHANTMENT_KEY_LOVE);
                        Player player = event.getPlayer();
                        if (player.getUUID().equals(uuid)) {
                            player.getInventory().placeItemBackInInventory(ditem);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }

        public static void onTick(EntityEvent event){
            if (event.getEntity() instanceof ItemEntity item && !item.getItem().isEmpty()  && !item.level().isClientSide){
                ItemStack oitem = item.getItem();
                if (oitem.getEnchantmentLevel(NekoEnchantments.ni_love) > 0){
                    if (oitem.getTag() != null) {
                        if (oitem.getTag().hasUUID(NekoLove.ENCHANTMENT_KEY_LOVE)) {
                            UUID uuid = oitem.getTag().getUUID(NekoLove.ENCHANTMENT_KEY_LOVE);
                            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                            if (server != null) {
                                ServerPlayer serverPlayer = server.getPlayerList().getPlayer(uuid);
                                if (serverPlayer != null) {
                                    Inventory playerInventory = serverPlayer.getInventory();
                                    NonNullList<ItemStack> items = playerInventory.items;
                                    for (ItemStack itemStack:items){
                                        if (itemStack.isEmpty()){
                                            serverPlayer.getInventory().placeItemBackInInventory(oitem);
                                            item.remove(Entity.RemovalReason.KILLED);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
