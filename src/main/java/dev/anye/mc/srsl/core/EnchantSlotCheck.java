package dev.anye.mc.srsl.core;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.*;
import org.slf4j.Logger;

public class EnchantSlotCheck {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static boolean hasTag(ItemStack stack, String tag) {
        return stack.getTags().anyMatch(n -> n.location().getPath().equals(tag));
    }
    public static boolean hasAnyTags(ItemStack stack, String... tags) {
        for (String tag : tags) {
            if (hasTag(stack, tag)) return true;
        }
        return false;
    }

    public static boolean isWeapon(Item stack) {
         return stack instanceof SwordItem;
    }
    public static boolean isWeapon(ItemStack stack) {
        return hasTag(stack, "enchantable/sword") || isWeapon(stack.getItem());
    }
    public static boolean isArmor(Item stack) {
        return stack instanceof ArmorItem;
    }
    public static boolean isArmor(ItemStack stack) {
        return hasTag(stack, "enchantable/armor") || isArmor(stack.getItem());
    }
    public static boolean isDigger(Item stack) {
        return stack instanceof DiggerItem;
    }
    public static boolean isDigger(ItemStack stack) {
        return hasTag(stack, "enchantable/mining_loot") || isDigger(stack.getItem());
    }
    public static boolean isFishingRod(Item stack) {
        return stack instanceof FishingRodItem;
    }
    public static boolean isFishingRod(ItemStack stack) {
        LOGGER.debug(stack.getTags().toList().toString());
        return hasTag(stack, "enchantable/fishing") || isFishingRod(stack.getItem());
    }
    public static boolean isHoe(Item stack) {
        return stack instanceof HoeItem;
    }
    public static boolean isHoe(ItemStack stack) {
        return isHoe(stack.getItem());
    }
    public static boolean isPickaxe(Item stack) {
        return stack instanceof PickaxeItem;
    }
    public static boolean isPickaxe(ItemStack stack) {
        return isPickaxe(stack.getItem());
    }
    public static boolean isShovel(Item stack) {
        return stack instanceof ShovelItem;
    }
    public static boolean isShovel(ItemStack stack) {
        return isShovel(stack.getItem());
    }
    public static boolean isBow(Item stack) {
        return stack instanceof BowItem;
    }
    public static boolean isBow(ItemStack stack) {
        return hasTag(stack, "enchantable/bow") || isBow(stack.getItem());
    }
    public static boolean isHead(ItemStack item){
        return isArmor(item) && hasTag(item,"enchantable/head_armor");
    }
    public static boolean isChest(ItemStack item){
        return isArmor(item)  && hasTag(item,"enchantable/chest_armor");
    }
    public static boolean isLegs(ItemStack item){
        return isArmor(item)  && hasTag(item,"enchantable/leg_armor");
    }
    public static boolean isFeet(ItemStack item){
        return isArmor(item) && hasTag(item,"enchantable/foot_armor");
    }

}
