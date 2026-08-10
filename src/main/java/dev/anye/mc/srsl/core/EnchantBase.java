package dev.anye.mc.srsl.core;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EnchantBase {
    Component getFullname(int level);

    int getMaxLevel();
    int getCost(int level);
    int getWeight();
    boolean canEnchant(ItemStack stack);

    void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel);

    void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel);
    float getDamageBonus(int level, Entity target, ItemStack enchantedItem);
}
