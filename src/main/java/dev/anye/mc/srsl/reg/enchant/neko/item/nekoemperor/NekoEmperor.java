package dev.anye.mc.srsl.reg.enchant.neko.item.nekoemperor;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NekoEmperor extends NekoEI {
	public static final String ENCHANTMENT_KEY_KILL = "kill";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",500F);
	});
	public final float quota;
	public final BigDecimal q ;

	public record Data(float quota) {
		public static final Data DEFAULT = new Data(500);
	}
	public NekoEmperor(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
		q = BigDecimal.valueOf(quota);
	}


	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		double kill = 1;
		CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
		CompoundTag em = nbt.getCompound("emperor.data").orElse(new CompoundTag());
		kill += em.getDouble(ENCHANTMENT_KEY_KILL).orElse(0d);
		return (float) kill;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onDeath);
	}

	public void onDeath(LivingDeathEvent livingDeathEvent) {
		if (livingDeathEvent.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
			Iterable<ItemStack> handlist = EnchantHelper.getSlotsItems(serverPlayer, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
			for (ItemStack itemStack : handlist) {
				if (EnchantHelper.hasEnchant(itemStack, Enchants.ni_emperor)) {
					CompoundTag nbt = EnchantHelper.getEnchantData(itemStack).getTagCopy();
					CompoundTag killData = nbt.getCompound("emperor.data").orElse(new CompoundTag());
					double kill = killData.getDouble("k1").orElse(0d);
					double kill2 = killData.getDouble("k2").orElse(0d);
					kill += livingDeathEvent.getEntity().getMaxHealth();
					if (kill < 0) {
						kill += Double.MAX_VALUE;
						kill2 += 1;
					}
					BigDecimal ks = BigDecimal.valueOf(kill);
					for (int i = 0; i < kill2; i++) {
						ks = ks.add(BigDecimal.valueOf(Double.MAX_VALUE));
					}
					ks = ks.divide(q, 2, RoundingMode.HALF_UP);
					killData.putDouble("k1", kill);
					killData.putDouble("k2", kill2);
					killData.putDouble(NekoEmperor.ENCHANTMENT_KEY_KILL, ks.doubleValue());
					nbt.put("emperor.data", killData);
					itemStack.set(DataReg.Enchant_Data.get(), new EnchantData(nbt));
					EnchantHelper.setEnchantData(itemStack, nbt);
				}
			}
		}
	}
}
