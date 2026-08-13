package dev.anye.mc.srsl.reg.enchant.phenomenon.duality;

import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.phenomenon.PhenomenonEnchant;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.enchanting.GetEnchantmentLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Duality extends PhenomenonEnchant {
	private static final String KEY = "duality";
	private static final String KEY_DATA = "duality.data";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",10F)
				.add("maxDuality",40)
				.add("minDuality",-40)
				.add("interval",600)
		;

	});
	private final float quota;
	private final int maxDuality;
	private final int minDuality;
	private final Map<UUID, Boolean> playerData = new HashMap<>();
	private final Map<UUID,Integer> playerTick = new HashMap<>();
	private final int interval;
	public record Data(float quota,int maxDuality,int minDuality,int interval) {
		public static final Data DEFAULT = new Data(10,40,-40,600);
	}
	public Duality(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
		maxDuality = config.asInt("maxDuality");
		minDuality = config.asInt("minDuality");
		interval = config.asInt("interval");
	}

	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
		CompoundTag dualityData = nbt.getCompound(KEY_DATA).orElse(new CompoundTag());
		float duality = dualityData.getInt(KEY).orElse(0);
		if (duality < 0) {
			duality = (float) (ItemHelper.getItemDamage(enchantedItem) * -duality / quota);
			return duality;
		}
		return super.getDamageBonus(level, mobType, enchantedItem);
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onBaby);
		modEventBus.addListener(this::onDeath);
		modEventBus.addListener(this::onPlayerTick);
		modEventBus.addListener(this::onLooting);
	}

	public void onBaby(BabyEntitySpawnEvent event) {
		if (event.getChild() != null && event.getCausedByPlayer() instanceof ServerPlayer player) {
			ItemStack item = player.getMainHandItem();
			if (EnchantHelper.hasEnchant(item, Enchants.duality)) {
				CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
				CompoundTag dualityData = nbt.getCompound(KEY_DATA).orElse(new CompoundTag());
				dualityData.putInt(KEY, dualityData.getInt(KEY).orElse(0) + 1);
				nbt.put(KEY_DATA, dualityData);
				EnchantHelper.setEnchantData(item, nbt);
			}
		}
	}

	public void onDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player) {
			ItemStack item = player.getMainHandItem();
			if (EnchantHelper.hasEnchant(item, Enchants.duality)) {
				CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
				CompoundTag dualityData = nbt.getCompound(KEY_DATA).orElse(new CompoundTag());
				dualityData.putInt(KEY, Math.max(minDuality, dualityData.getInt(KEY).orElse(0) - 1));
				nbt.put(KEY_DATA, dualityData);
				EnchantHelper.setEnchantData(item, nbt);
			}
		}
	}

	public void onLivingDrops(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack item = serverPlayer.getMainHandItem();
			if (EnchantHelper.hasEnchant(item, Enchants.duality)) {
				for (ItemEntity drop : event.getDrops()) {
					ItemStack di = drop.getItem();
					di.setCount(di.getCount() * 2);
				}
			}
		}
	}

	public void onPlayerTick(PlayerTickEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			if (playerTick.containsKey(serverPlayer.getUUID())) {
				playerTick.computeIfPresent(serverPlayer.getUUID(), (uuid, oldTick) -> {
					int tick = oldTick + 1;
					if (tick > interval) {
						tick = 0;
						change(serverPlayer);
					}
					return tick;
				});
			}else playerTick.put(serverPlayer.getUUID(),1);
		}
	}
	public void change(ServerPlayer serverPlayer){
		ItemStack item = serverPlayer.getMainHandItem();
		if (EnchantHelper.hasEnchant(item, Enchants.duality)) {
			boolean sub = playerData.getOrDefault(serverPlayer.getUUID(), true);
			CompoundTag nbt = EnchantHelper.getEnchantData(item).getTagCopy();
			CompoundTag dualityData = nbt.getCompound(KEY_DATA).orElse(new CompoundTag());
			int d = dualityData.getInt(KEY).orElse(0);
			if (sub) {
				d++;
			} else {
				d--;
			}
			if (d >= maxDuality || d <= minDuality) {
				playerData.put(serverPlayer.getUUID(), !sub);
			}
			dualityData.putInt(KEY, d);
			nbt.put(KEY_DATA, dualityData);
			EnchantHelper.setEnchantData(item, nbt);
		}
	}

	public void onLooting(GetEnchantmentLevelEvent event) {
		event.getHolder(Enchantments.LOOTING).ifPresent(enchantmentReference -> {
			Holder<Enchantment> loot = enchantmentReference.getDelegate();
			int looting = event.getEnchantments().getLevel(loot);
			ItemInstance item = event.getStack();
			CompoundTag nbt = EnchantHelper.getAllData(item);
			CompoundTag dualityData = nbt.getCompound(KEY_DATA).orElse(new CompoundTag());
			int a = dualityData.getInt(KEY).orElse(0);
			if (a > 0) {
				a /= 5;
				looting += a;
			}
			event.getEnchantments().set(loot, looting);
		});
	}
}
