package dev.anye.mc.srsl.enchantment;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class EnchantmentCore extends Enchantment {
    private final ChatFormatting nameColor;
    protected EnchantmentCore(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        this(ChatFormatting.GRAY,pRarity, pCategory, pApplicableSlots);
    }
    public EnchantmentCore(ChatFormatting chatFormatting , Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots){
        super(pRarity, pCategory, pApplicableSlots);
        this.nameColor = chatFormatting;
    }
    @Override
    public int getMinCost(int pLevel) {
        return 30;
    }

    @NotNull
    @Override
    public  Component getFullname(int pLevel) {
        MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
        mutablecomponent.withStyle(nameColor);

        if (pLevel != 1 || this.getMaxLevel() != 1) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + pLevel));
        }
        return mutablecomponent;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
    @Override
    public boolean isDiscoverable() {
        return super.isDiscoverable();
    }
}
