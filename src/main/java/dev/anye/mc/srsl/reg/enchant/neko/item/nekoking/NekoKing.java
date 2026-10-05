package dev.anye.mc.srsl.reg.enchant.neko.item.nekoking;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.datapack.NekoKingDatapack;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class NekoKing extends NekoEI {
	public static final String ENCHANTMENT_KEY_REFINE = "refine";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",20F);
	});
	private final float quota;
	public record Data(float quota) {
		public static final Data DEFAULT = new Data(20);
	}
	public NekoKing(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
	}

	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		float refine = 1;
		CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
		CompoundTag em = nbt.getCompound("king.data").orElse(new CompoundTag());
		refine += em.getFloat(ENCHANTMENT_KEY_REFINE).orElse(0f) / quota;
		return refine;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAnvil);
	}

	public void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
		if (anvilUpdateEvent.getPlayer() instanceof ServerPlayer serverPlayer && EnchantHelper.hasEnchant(anvilUpdateEvent.getLeft(), Enchants.ni_king)) {
			Item ritem = anvilUpdateEvent.getRight().getItem();
			serverPlayer.level().registryAccess().lookup(NekoKingDatapack.KEY).ifPresent(nekoKingDatapacks -> nekoKingDatapacks.get(NekoKingDatapack.getItemKey(ritem)).ifPresent(nekoKingDatapackReference -> {

				int re = nekoKingDatapackReference.value().refine();
				if (re > 0) {
					ItemStack oldItem = anvilUpdateEvent.getLeft().copy();
					int count = anvilUpdateEvent.getRight().getCount();
					CompoundTag oldItemNbt = EnchantHelper.getEnchantData(oldItem).getTagCopy();
					CompoundTag kingData = oldItemNbt.getCompound("king.data").orElse(new CompoundTag());
					kingData.putInt(ENCHANTMENT_KEY_REFINE, kingData.getInt(ENCHANTMENT_KEY_REFINE).orElse(0) + (re * count));
					oldItemNbt.put("king.data", kingData);
					EnchantHelper.setEnchantData(oldItem, oldItemNbt);
					int exp = nekoKingDatapackReference.value().exp() * count;
					exp = PlayerHelper.getLevelFromExperience(exp);
					anvilUpdateEvent.setOutput(oldItem);
					anvilUpdateEvent.setXpCost(exp);
					anvilUpdateEvent.setMaterialCost(count);
				}

			}));
		}
	}
}
