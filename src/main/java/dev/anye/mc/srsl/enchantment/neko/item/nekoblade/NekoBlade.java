package dev.anye.mc.srsl.enchantment.neko.item.nekoblade;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class NekoBlade extends NekoEI {
    private final float base = EnchantmentsConfig.INSTANCE.getConfig(EnchantmentRegister.NEKO_BLADE).getParameters().get("baseDamage").getAsFloat();
    public NekoBlade() {
        super(Rarity.VERY_RARE);
    }
    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        Collection<AttributeModifier> att =
                enchantedItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        if (!att.isEmpty()){
            double addDamage = 0;
            for (AttributeModifier al : att) {
                if (al.getOperation() == AttributeModifier.Operation.ADDITION) {
                    addDamage += al.getAmount();
                }
            }
            float x = enchantedItem.getDamageValue();
            float y = enchantedItem.getMaxDamage();
            return (float) (addDamage * (base + x / y));
        }
        return 0;
    }
}
