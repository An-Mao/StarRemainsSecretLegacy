package dev.anye.mc.srsl.enchantment.phenomenon;

import dev.anye.mc.cores.component.ComponentStyle;
import dev.anye.mc.srsl.enchantment.EnchantmentCore;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class PhenomenonEnchantment extends EnchantmentCore {
    protected PhenomenonEnchantment( EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(Rarity.VERY_RARE, pCategory, pApplicableSlots);
    }
    @Override
    public @NotNull Component getFullname(int pLevel) {
        return ComponentStyle.Flash(Component.translatable(this.getDescriptionId()).getString(),System.currentTimeMillis()/50);
        //return _AM_Color.RainbowTextColor(Component.translatable(this.getDescriptionId()).getString(),System.currentTimeMillis()/50);
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
