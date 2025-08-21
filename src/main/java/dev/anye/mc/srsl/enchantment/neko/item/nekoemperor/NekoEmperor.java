package dev.anye.mc.srsl.enchantment.neko.item.nekoemperor;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;

public class NekoEmperor extends NekoEI {
    public static final String ENCHANTMENT_KEY_KILL = "kill";
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_EMPEROR);
    private final float quota = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_EMPEROR,"quota");
    public NekoEmperor() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        float kill = 1;
        if (enchantedItem.getTag() != null) {
            kill += enchantedItem.getTag().getFloat(ENCHANTMENT_KEY_KILL) / quota;
        }
        return kill;
    }
}
