package dev.anye.mc.srsl.enchant.neko.item.nekoemperor;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.neko.item.NekoEI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NekoEmperor extends NekoEI {
    public static final String ENCHANTMENT_KEY_KILL = "kill";
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_EMPEROR);
    public static final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.NEKO_EMPEROR,"quota");
    public NekoEmperor() {
        super();
    }

    @Override
    public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
        double kill = 1;
        CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
        CompoundTag em = nbt.getCompound("emperor.data").orElse(new CompoundTag());
        kill += em.getDouble(ENCHANTMENT_KEY_KILL).orElse(0d);
        /*
        em.putFloat(ENCHANTMENT_KEY_KILL, kill);
        nbt.put("emperor.data", em);
        EnchantHelper.setEnchantData(enchantedItem, nbt);

         */
        return (float) kill;
    }
}
