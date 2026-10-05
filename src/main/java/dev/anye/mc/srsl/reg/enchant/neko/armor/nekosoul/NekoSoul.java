package dev.anye.mc.srsl.reg.enchant.neko.armor.nekosoul;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

public class NekoSoul extends NekoEA {
	public static final String ENCHANTMENT_KEY_SOUL = "soul";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("consume",5F).add("quota",0.1F);
	});

	private final float consume;
	private final float quota;
	public record Data(float consume,float quota) {
		public static final Data DEFAULT = new Data(200,0.1F);
	}
	public NekoSoul(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		consume = config.asFloat("consume");
		quota = config.asFloat("quota");
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}

	@Override
	public void doPostHurt(LivingEntity pTarget, @NotNull Entity pAttacker, int pLevel) {
		if (pTarget instanceof ServerPlayer serverPlayer) {
			ItemStack mainHandItem = serverPlayer.getMainHandItem();
			if (mainHandItem.getItem() != Items.AIR) {
				CompoundTag compoundTag = EnchantHelper.getEnchantData(mainHandItem).getTagCopy();
				CompoundTag soulData = compoundTag.getCompound("soul.data").orElse(new CompoundTag());
				int souls = soulData.getInt(ENCHANTMENT_KEY_SOUL).orElse(0);
				if (souls >= consume) {
					souls -= (int) consume;
					serverPlayer.heal(serverPlayer.getMaxHealth() * quota);
					soulData.putInt(ENCHANTMENT_KEY_SOUL, souls);
					compoundTag.put("soul.data", soulData);
					EnchantHelper.setEnchantData(mainHandItem, compoundTag);
				}

			}
		}
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAttack);
	}

	public void onAttack(LivingIncomingDamageEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(serverPlayer);
			for (ItemStack slot : armorItemStacks) {
				if (EnchantHelper.hasEnchant(slot, Enchants.na_soul)) {
					CompoundTag nbt = EnchantHelper.getEnchantData(slot).getTagCopy();
					CompoundTag soulData = nbt.getCompound("soul.data").orElse(new CompoundTag());
					int soul = soulData.getInt(ENCHANTMENT_KEY_SOUL).orElse(0);
					soulData.putInt(ENCHANTMENT_KEY_SOUL, soul + 1);
					nbt.put("soul.data", soulData);
					EnchantHelper.setEnchantData(slot, nbt);
				}
			}
		}
	}
}
