package dev.anye.mc.srsl.enchant.zero.item.alone;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class Alone extends ZeroItemE {
    public static final String ENCHANTMENT_KEY_ALONE = "alone";
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_ALONE);
    private final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_ALONE,"quota");
    public Alone() {
        super();
    }
    @Override
    public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
        if (EnchantHelper.getEnchantCompound(enchantedItem).size() == 1){
            float amount = 0;
            CompoundTag nbt = EnchantHelper.getAllData(enchantedItem);
            CompoundTag aloneData = nbt.getCompound("alone.data").orElse(new CompoundTag());
            amount = aloneData.getInt(ENCHANTMENT_KEY_ALONE).orElse(0);

            Collection<AttributeModifier> att = ItemHelper.getAttributeModifiers(enchantedItem, EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
            amount = (float) (_Math.log(amount, (int) quota) * AttributeHelper.getAttributeModifierValue(att));
            return amount;
        }
        return super.getDamageBonus(level, mobType, enchantedItem);
    }
}
