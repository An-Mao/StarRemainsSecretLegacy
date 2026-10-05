package dev.anye.mc.srsl.reg.enchant.zero.item.alone;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Collection;

public class Alone extends ZeroItemE {
	public static final String ENCHANTMENT_KEY_ALONE = "alone";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",5F);
	});
	private final float quota;
	public record Data(float quota) {
		public static final Data DEFAULT = new Data(5);
	}

	public Alone(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
	}

	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		if (EnchantHelper.getEnchantCompound(enchantedItem).size() == 1) {
			float amount;
			CompoundTag nbt = EnchantHelper.getAllData(enchantedItem);
			CompoundTag aloneData = nbt.getCompound("alone.data").orElse(new CompoundTag());
			amount = aloneData.getInt(ENCHANTMENT_KEY_ALONE).orElse(0);

			Collection<AttributeModifier> att = ItemHelper.getAttributeModifiers(enchantedItem, EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
			amount = (float) (_Math.log(amount, (int) quota) * AttributeHelper.getAttributeModifierValue(att));
			return amount;
		}
		return super.getDamageBonus(level, mobType, enchantedItem);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAttack);
	}

	public void onAttack(LivingDamageEvent.Post event) {
		if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack item = serverPlayer.getMainHandItem();
			if (EnchantHelper.hasEnchant(item, Enchants.zi_alone) && EnchantHelper.getEnchantCompound(item).size() == 1) {
				int amount = 0;
				EnchantData enchantData = EnchantHelper.getEnchantData(item);
				CompoundTag nbt = EnchantHelper.getAllData(enchantData);
				CompoundTag aloneData = nbt.getCompound("alone.data").orElse(new CompoundTag());
				amount = aloneData.getInt(Alone.ENCHANTMENT_KEY_ALONE).orElse(0);
				aloneData.putInt(Alone.ENCHANTMENT_KEY_ALONE, amount + 1);
				nbt.put("alone.data", aloneData);
				enchantData = new EnchantData(nbt);
				item.set(DataReg.Enchant_Data.get(), enchantData);
			}
		}
	}
}
