package dev.anye.mc.srsl.enchant.neko.item.nekoking;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.neko.item.NekoEI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NekoKing extends NekoEI {
    public static final String ENCHANTMENT_KEY_REFINE = "refine";
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_KING);
    private final float quota = EnchantsConfig.INSTANCE.getConfig(EnchantReg.NEKO_KING).getParameters().get("quota").getAsFloat();
    public NekoKing() {
        super();
    }

    @Override
    public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
        float refine = 1;
        CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
        CompoundTag em = nbt.getCompound("king.data").orElse(new CompoundTag());
        refine += em.getFloat(ENCHANTMENT_KEY_REFINE).orElse(0f) / quota;
        return refine;
    }
}
