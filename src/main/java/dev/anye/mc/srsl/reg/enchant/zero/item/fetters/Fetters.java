package dev.anye.mc.srsl.reg.enchant.zero.item.fetters;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

public class Fetters extends ZeroItemE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("intervals",1200F);
	});
	private final float intervals;
	public record Data(float intervals) {
		public static final Data DEFAULT = new Data(1200);
	}
	public Fetters(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		intervals = config.asFloat("intervals");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		super.doPostAttack(pAttacker, pTarget, pLevel);
		if (pTarget instanceof LivingEntity livingEntity) {
			CompoundTag dat = livingEntity.getPersistentData();
			long gt = livingEntity.level().getGameTime();
			if (gt - dat.getInt("Fetters").orElse(0) < intervals) {
				return;
			}
			dat.putLong("Fetters", gt);
			dat.putDouble("Fx", livingEntity.getX());
			dat.putDouble("Fy", livingEntity.getY());
			dat.putDouble("Fz", livingEntity.getZ());
		}
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onTick);
	}

	public void onTick(EntityTickEvent.Pre event) {
		if (event.getEntity() instanceof LivingEntity le) {
			CompoundTag dat = le.getPersistentData();
			long gt = le.level().getGameTime();
			if (dat.getLong("Fetters").orElse(0l) > 0 && gt - dat.getLong("Fetters").orElse(0l) < 1200) {
				le.teleportTo(dat.getDouble("Fx").orElse(0d), dat.getDouble("Fy").orElse(0d), dat.getDouble("Fz").orElse(0d));
			}
		}
	}
}
