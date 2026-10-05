package dev.anye.mc.srsl.reg.enchant;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.*;
import org.slf4j.Logger;

public class EnchantSlotCheck {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static boolean hasTag(ItemStack stack, String tag) {
		return stack.tags().anyMatch(n -> n.location().getPath().equals(tag));
	}

	public static boolean hasAnyTags(ItemStack stack, String... tags) {
		for (String tag : tags) {
			if (hasTag(stack, tag)) return true;
		}
		return false;
	}

	public static boolean isWeapon(ItemStack stack) {
		return hasTag(stack, "enchantable/weapon");
	}

	public static boolean isArmor(ItemStack stack) {
		return hasTag(stack, "enchantable/armor");
	}

	public static boolean isDigger(ItemStack stack) {
		return hasTag(stack, "enchantable/mining_loot");
	}

	public static boolean isFishingRod(ItemStack stack) {
		return hasTag(stack, "enchantable/fishing");
	}

	public static boolean isBow(Item stack) {
		return stack instanceof BowItem;
	}

	public static boolean isBow(ItemStack stack) {
		return hasTag(stack, "enchantable/bow") || isBow(stack.getItem());
	}

	public static boolean isHead(ItemStack item) {
		return isArmor(item) && hasTag(item, "enchantable/head_armor");
	}

	public static boolean isChest(ItemStack item) {
		return isArmor(item) && hasTag(item, "enchantable/chest_armor");
	}

	public static boolean isLegs(ItemStack item) {
		return isArmor(item) && hasTag(item, "enchantable/leg_armor");
	}

	public static boolean isFeet(ItemStack item) {
		return isArmor(item) && hasTag(item, "enchantable/foot_armor");
	}

}
