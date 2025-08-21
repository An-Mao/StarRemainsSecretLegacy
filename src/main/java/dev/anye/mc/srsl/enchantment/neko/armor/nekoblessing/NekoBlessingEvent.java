package dev.anye.mc.srsl.enchantment.neko.armor.nekoblessing;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NekoBlessingEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NBE{
        private static final float add = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_BLESSING,"add");
        private static final float dec = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_BLESSING,"dec");
        @SubscribeEvent
        public static void onBaby(BabyEntitySpawnEvent event){
            if (NekoBlessing.ENABLE) {
                if (event.getChild() != null && !event.getChild().level().isClientSide && event.getChild() instanceof Cat) {
                    if (event.getCausedByPlayer() instanceof ServerPlayer player) {
                        Iterable<ItemStack> slotlist = player.getArmorSlots();
                        for (ItemStack slot : slotlist) {
                            int lvl = slot.getEnchantmentLevel(NekoEnchantments.na_blessing);
                            if (lvl > 0) {
                                if (slot.getTag() != null) {
                                    int blessing = slot.getTag().getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING);
                                    slot.getTag().putInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING, (int) (blessing + add));
                                }
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (NekoBlessing.ENABLE) {
                if (!event.getEntity().level().isClientSide && event.getEntity() instanceof Cat) {
                    if (event.getSource().getEntity() instanceof ServerPlayer player) {
                        Iterable<ItemStack> slotlist = player.getArmorSlots();
                        for (ItemStack slot : slotlist) {
                            int lvl = slot.getEnchantmentLevel(NekoEnchantments.na_blessing);
                            if (lvl > 0) {
                                if (slot.getTag() != null) {
                                    int blessing = slot.getTag().getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING);
                                    slot.getTag().putInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING, (int) (blessing - dec));
                                }
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            if (NekoBlessing.ENABLE) {
                if (!event.getEntity().level().isClientSide) {
                    if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                        Iterable<ItemStack> slotlist = serverPlayer.getArmorSlots();
                        float amount = event.getAmount();
                        for (ItemStack slot : slotlist) {
                            int lvl = slot.getEnchantmentLevel(NekoEnchantments.na_blessing);
                            if (lvl > 0) {
                                if (slot.getTag() != null) {
                                    int blessing = slot.getTag().getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING);
                                    if (blessing >= 1) {
                                        slot.getTag().putInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING, blessing - 1);
                                        amount *= 0.9F - (float) _Math.log2Floor(blessing) / 100.0f;
                                    }
                                }
                            }
                        }
                        event.setAmount(amount);
                    }
                }
            }
        }
    }
}
