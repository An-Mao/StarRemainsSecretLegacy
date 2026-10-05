package dev.anye.mc.srsl.reg.enchant.neko.item.nekochop;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class NekoChop extends NekoEI {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",5);
	});
	/*
	private final ItemStack[] mobHead = {
			new ItemStack(Items.CREEPER_HEAD),
			new ItemStack(Items.DRAGON_HEAD),
			new ItemStack(Items.ZOMBIE_HEAD),
			new ItemStack(Items.PIGLIN_HEAD),
			new ItemStack(Items.WITHER_SKELETON_SKULL),
			new ItemStack(Items.SKELETON_SKULL),
	};

	 */
	private final int maxLevel;
	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(5);
	}
	public NekoChop(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		maxLevel = config.asInt("maxLevel");
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onDeath);
	}

	public void onDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player) {
			int a = getA(event.getEntity());
			if (a < 0 || a > 5) return;
			int lvl = EnchantHelper.getEnchantLevel(player.getMainHandItem(), Enchants.ni_chop);
			if (lvl > 0 && lvl * 15 > _Math.RD.getIntRandomNumber(1, 100)) {
				event.getEntity().spawnAtLocation(player.level(), head(a));

			}
		}
	}

	private ItemStack head(int i){
		ItemStack[] mobHead = {
				new ItemStack(Items.CREEPER_HEAD),
				new ItemStack(Items.DRAGON_HEAD),
				new ItemStack(Items.ZOMBIE_HEAD),
				new ItemStack(Items.PIGLIN_HEAD),
				new ItemStack(Items.WITHER_SKELETON_SKULL),
				new ItemStack(Items.SKELETON_SKULL),
		};
		return mobHead[i];
	}

	private int getA(LivingEntity livingEntity) {
		if (livingEntity instanceof Creeper) {
			return 0;
		}
		if (livingEntity instanceof EnderDragon) {
			return 1;
		}
		if (livingEntity instanceof Zombie) {
			return 2;
		}
		if (livingEntity instanceof Piglin) {
			return 3;
		}
		if (livingEntity instanceof WitherSkeleton) {
			return 4;
		}
		if (livingEntity instanceof Skeleton) {
			return 5;
		}
		return -1;
	}
}
