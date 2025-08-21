package dev.anye.mc.srsl.enchantment.zero.item.alone;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.attribute.AttributeHelper;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.item.ZeroItemE;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class Alone extends ZeroItemE {
    public static final String ENCHANTMENT_KEY_ALONE = "alone";
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.Z_ALONE);
    private final float quota = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.Z_ALONE,"quota");
    public Alone() {
        super(Rarity.VERY_RARE);
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (enchantedItem.getAllEnchantments().size() == 1){
            float amount = 0;
            if (enchantedItem.getTag() != null) {
                amount = enchantedItem.getTag().getInt(ENCHANTMENT_KEY_ALONE);
            }
            Collection<AttributeModifier> att =
                    enchantedItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);

            amount = (float) (_Math.log(amount, (int) quota) * AttributeHelper.getAttributeModifierValue(att));
            return amount;
        }
        return super.getDamageBonus(level, mobType, enchantedItem);
    }
}
