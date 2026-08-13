package dev.anye.mc.srsl.reg.enchant.zero.item.purify;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Purify extends ZeroItemE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("probability",5F);
	});
	private final float probability;
	//String[] SK = {"Health", "Pos"};
	public record Data(float probability) {
		public static final Data DEFAULT = new Data(5);
	}
	public Purify(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		probability = config.asFloat("probability");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer) {
			if (pTarget instanceof LivingEntity livingEntity) {
				if (_Math.RD.getIntRandomNumber(1, 100) < probability) {
					//nbt.putByte("NoAI", (byte) 1);
					CompoundTag dat = livingEntity.getPersistentData();
					Iterable<String> keys = dat.keySet();
					for (String k : keys) {
						if (!Objects.equals(k, "Health") && !Objects.equals(k, "Pos")) {
							dat.remove(k);
						}
					}
					//livingEntity.setInvisible();
					livingEntity.entityTags().clear();
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
