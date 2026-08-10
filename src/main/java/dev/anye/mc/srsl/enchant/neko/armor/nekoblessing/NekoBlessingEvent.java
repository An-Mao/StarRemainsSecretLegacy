package dev.anye.mc.srsl.enchant.neko.armor.nekoblessing;

import dev.anye.core.math._Math;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantData;
import dev.anye.mc.srsl.core.DataReg;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class NekoBlessingEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NBE{
        private static final int add = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.NEKO_BLESSING,"add");
        private static final int dec = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.NEKO_BLESSING,"dec");
        @SubscribeEvent
        public static void onBaby(BabyEntitySpawnEvent event){
            if (NekoBlessing.ENABLE) {
                if (event.getChild() != null && !event.getChild().level().isClientSide() && event.getChild() instanceof Cat) {
                    if (event.getCausedByPlayer() instanceof ServerPlayer player) {
                        Iterable<ItemStack> slotlist = EnchantHelper.getArmorItemStacks(player);
                        for (ItemStack slot : slotlist) {
                            if (EnchantHelper.hasEnchant(slot, Enchants.na_blessing)) {
                                changeBless(slot,add);
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (NekoBlessing.ENABLE) {
                if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Cat) {
                    if (event.getSource().getEntity() instanceof ServerPlayer player) {
                        Iterable<ItemStack> slotlist = EnchantHelper.getArmorItemStacks(player);
                        for (ItemStack slot : slotlist) {
                            if (EnchantHelper.hasEnchant(slot, Enchants.na_blessing)) {
                                changeBless(slot,-dec);
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingDamageEvent.Pre event){
            if (NekoBlessing.ENABLE) {
                if (!event.getEntity().level().isClientSide()) {
                    if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                        Iterable<ItemStack> slotlist = EnchantHelper.getArmorItemStacks(serverPlayer);
                        float amount = event.getNewDamage();
                        for (ItemStack slot : slotlist) {

                            int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_blessing);
                            if (lvl > 0) {
                                EnchantData enchantData = EnchantHelper.getEnchantData(slot);
                                CompoundTag nbt = enchantData.getTagCopy();
                                CompoundTag blessData = nbt.getCompound("bless.data").orElse(new CompoundTag());
                                int blessing = blessData.getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING).orElse(0);
                                if (blessing >= 1) {
                                    blessData.putInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING, blessing - 1);
                                    nbt.put("bless.data", blessData);
                                    enchantData = new EnchantData(nbt);
                                    slot.set(DataReg.Enchant_Data.get(), enchantData);
                                    amount *= 0.9F - (float) _Math.log2Floor(blessing) / 100.0f;
                                }
                            }
                        }
                        event.setNewDamage(amount);
                    }
                }
            }
        }
    }
    public static int changeBless(ItemStack itemStack, int value) {
        EnchantData enchantData = EnchantHelper.getEnchantData(itemStack);
        CompoundTag nbt = enchantData.getTagCopy();
        CompoundTag blessData = nbt.getCompound("bless.data").orElse(new CompoundTag());
        int blessing = blessData.getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING).orElse(0);
        blessing += value;
        blessData.putInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING, blessing);
        nbt.put("bless.data", blessData);
        enchantData = new EnchantData(nbt);
        itemStack.set(DataReg.Enchant_Data.get(), enchantData);
        return blessing;
    }
}
