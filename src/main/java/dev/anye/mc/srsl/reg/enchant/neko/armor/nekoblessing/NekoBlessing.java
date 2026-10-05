package dev.anye.mc.srsl.reg.enchant.neko.armor.nekoblessing;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.curse.corrode.Corrode;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class NekoBlessing extends NekoEA {
	public static final String ENCHANTMENT_KEY_BLESSING = "blessing";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("add",1).add("dec",2);
	});


	private final int add;
	private final int dec;

	public record Data(int add,int dec) {
		public static final Data DEFAULT = new Data(1,2);
	}

	public NekoBlessing(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		this.add = config.asInt("add");
		this.dec = config.asInt("dec");
	}


	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onBaby);
		modEventBus.addListener(this::onDeath);
	}

	public void onBaby(BabyEntitySpawnEvent event) {
		if (event.getCausedByPlayer() instanceof ServerPlayer player && event.getChild() != null && event.getChild() instanceof Cat) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(player);
			for (ItemStack slot : armorItemStacks) {
				if (EnchantHelper.hasEnchant(slot, Enchants.na_blessing)) {
					changeBless(slot, add);
				}
			}

		}
	}

	public void onDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player && event.getEntity() instanceof Cat) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(player);
			for (ItemStack slot : armorItemStacks) {
				if (EnchantHelper.hasEnchant(slot, Enchants.na_blessing)) {
					changeBless(slot, -dec);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onHurt(LivingDamageEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(serverPlayer);
			float amount = event.getNewDamage();
			for (ItemStack slot : armorItemStacks) {
				int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_blessing);
				if (lvl > 0) {
					EnchantData enchantData = EnchantHelper.getEnchantData(slot);
					CompoundTag nbt = enchantData.getTagCopy();
					CompoundTag blessData = nbt.getCompound("bless.data").orElse(new CompoundTag());
					int blessing = blessData.getInt(ENCHANTMENT_KEY_BLESSING).orElse(0);
					if (blessing >= 1) {
						blessData.putInt(ENCHANTMENT_KEY_BLESSING, blessing - 1);
						nbt.put("bless.data", blessData);
						enchantData = new EnchantData(nbt);
						slot.set(DataReg.Enchant_Data.get(), enchantData);
						amount *= 0.9F - _Math.log2Floor(blessing) / 100.0f;
					}
				}
			}
			event.setNewDamage(amount);

		}
	}

	public int changeBless(ItemStack itemStack, int value) {
		EnchantData enchantData = EnchantHelper.getEnchantData(itemStack);
		CompoundTag nbt = enchantData.getTagCopy();
		CompoundTag blessData = nbt.getCompound("bless.data").orElse(new CompoundTag());
		int blessing = blessData.getInt(ENCHANTMENT_KEY_BLESSING).orElse(0);
		blessing += value;
		blessData.putInt(ENCHANTMENT_KEY_BLESSING, blessing);
		nbt.put("bless.data", blessData);
		enchantData = new EnchantData(nbt);
		itemStack.set(DataReg.Enchant_Data.get(), enchantData);
		return blessing;
	}
}
