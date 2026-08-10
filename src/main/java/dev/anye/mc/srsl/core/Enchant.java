package dev.anye.mc.srsl.core;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Enchant extends EnchantSlotCheck implements EnchantBase {
    private final ChatFormatting nameColor;
    protected Enchant() {
        this(ChatFormatting.GRAY);
    }
    public Enchant(ChatFormatting chatFormatting){
        this.nameColor = chatFormatting;
    }
    public Identifier getRegistryName() {
        return EnchantReg.getResourceLocation(this);
    }
    public String getDescriptionId() {
        return "enchant." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath();
    }
    @NotNull
    @Override
    public  Component getFullname(int pLevel) {
        MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
        mutablecomponent.withStyle(nameColor);

        if (pLevel > 1) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + pLevel));
        }
        return mutablecomponent;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getCost(int level) {
        return 30;
    }

    @Override
    public int getWeight() {
        return 100;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return false;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {

    }

    @Override
    public float getDamageBonus(int level, Entity target, ItemStack enchantedItem) {
        return 0;
    }






}
