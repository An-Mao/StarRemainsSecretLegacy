package dev.anye.mc.srsl.core;

import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnchantHelper {
	public static @NotNull EnchantData getEnchantData(ItemInstance stack) {
		return stack.getOrDefault(DataReg.Enchant_Data.get(), EnchantData.EMPTY);
	}

	public static int getEnchantLevel(ItemStack stack, Enchant enchant) {
		return getEnchantLevel(stack, EnchantReg.getResourceLocation(enchant));
	}

	public static int getEnchantLevel(ItemStack stack, Identifier resourceLocation) {
		return getEnchantCompound(getEnchantData(stack)).getInt(resourceLocation.toString()).orElse(0);
	}

	public static boolean hasEnchant(ItemStack stack, Enchant enchant) {
		return hasEnchant(stack, EnchantReg.getResourceLocation(enchant));
	}

	public static boolean hasEnchant(ItemStack stack, Identifier resourceLocation) {
		return getEnchantLevel(stack, resourceLocation) > 0;
	}

	public static void setEnchantData(ItemStack itemStack, CompoundTag nbt) {
		EnchantData enchantData = new EnchantData(nbt);
		itemStack.set(DataReg.Enchant_Data.get(), enchantData);
	}

	public static List<ItemStack> getArmorItemStacks(LivingEntity livingEntity) {
		return getSlotsItems(livingEntity, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
	}

	public static List<ItemStack> getSlotsItems(LivingEntity livingEntity, EquipmentSlot... equipmentSlots) {
		List<ItemStack> itemStacks = new ArrayList<>();
		for (EquipmentSlot slot : equipmentSlots) {
			if (slot != null) {
				itemStacks.add(livingEntity.getItemBySlot(slot));
			}
		}
		return itemStacks;
	}

	public static int getEnchantLevel(Enchant enchant, LivingEntity livingEntity) {
		Iterable<ItemStack> iterable = getArmorItemStacks(livingEntity);
		int i = 0;
		for (ItemStack itemstack : iterable) {
			int j = getEnchantLevel(itemstack, enchant);
			if (j > i) {
				i = j;
			}
		}
		return i;
	}

	public static CompoundTag getEnchantCompound(EnchantData enchantData) {
		return enchantData.get("enchant");
	}

	public static CompoundTag getEnchantCompound(ItemStack itemStack) {
		return getEnchantData(itemStack).get("enchant");
	}

	public static Map<String, Integer> getEnchantsWithResourceLocation(EnchantData enchantData) {
		CompoundTag enchants = getEnchantCompound(enchantData);
		Set<String> keys = enchants.keySet();
		Map<String, Integer> map = new HashMap<>();
		for (String key : keys) {
			map.put(key, enchants.getInt(key).orElse(0));
		}
		return map;
	}

	public static Map<Enchant, Integer> getEnchants(EnchantData enchantData) {
		CompoundTag enchants = getEnchantCompound(enchantData);
		Set<String> keys = enchants.keySet();
		Map<Enchant, Integer> map = new HashMap<>();
		for (String key : keys) {
			map.put(EnchantReg.getEnchant(key), enchants.getInt(key).orElse(0));
		}
		return map;
	}

	public static Map<Enchant, Integer> getEnchants(ItemStack itemStack) {
		return getEnchants(getEnchantData(itemStack));
	}

	public static CompoundTag getAllData(EnchantData enchantData) {
		return enchantData.getTagCopy();
	}

	public static CompoundTag getAllData(ItemInstance itemStack) {
		return getEnchantData(itemStack).getTagCopy();
	}

	public static int getEnchantWeight() {
		int[] a = {0};
		EnchantReg.ENCHANT.registry().asHolderIdMap().forEach(enchantReg -> {
			a[0] += enchantReg.value().getWeight();
		});
		return a[0];
	}

	public static void getAllEnchants(List<Enchant> set) {
		set.clear();
		set.addAll(EnchantReg.ENCHANT.registry().stream().toList());
	}

	public static List<Enchant> getAllEnchants() {
		return new ArrayList<>(EnchantReg.ENCHANT.registry().stream().toList());
	}
}
