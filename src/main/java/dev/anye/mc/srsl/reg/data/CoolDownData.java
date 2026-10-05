package dev.anye.mc.srsl.reg.data;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class CoolDownData {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final CoolDownData EMPTY = new CoolDownData(new CompoundTag());
	public static final Codec<CoolDownData> CODEC = Codec.withAlternative(CompoundTag.CODEC, TagParser.FLATTENED_CODEC)
			.xmap(CoolDownData::new, coolDownData -> coolDownData.cooldown);
	public static final StreamCodec<ByteBuf, CoolDownData> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(CoolDownData::new, coolDownData -> coolDownData.cooldown);

	private final CompoundTag cooldown;

	public CoolDownData(CompoundTag cooldown) {
		this.cooldown = cooldown;
	}

	public static CoolDownData of(CompoundTag cooldown) {
		return new CoolDownData(cooldown);
	}

	public CoolDownData copy() {
		return new CoolDownData(getTagCopy());
	}

	public CompoundTag getTagCopy() {
		return cooldown.copy();
	}

	public CompoundTag getUnsafe() {
		return this.cooldown;
	}

	public CoolDownData update(Consumer<CompoundTag> pUpdater) {
		var cooldown = getTagCopy();
		pUpdater.accept(cooldown);
		return new CoolDownData(cooldown);
	}

	public long getCooldown(String key) {
		return this.cooldown.getLong(key).orElse(0l);
	}

	public void setCooldown(String key, long value) {
		this.cooldown.putLong(key, value);
	}

	public void addCooldown(String key, long value) {
		this.cooldown.putLong(key, getCooldown(key) + value);
	}

	public boolean isCoolDown(String key) {
		return getCooldown(key) > 0;
	}

	public void removeCoolDown(String key) {
		this.cooldown.remove(key);
	}

	public void diminishCoolDown(String key) {
		long cd = getCooldown(key);
		if (cd > 0) this.cooldown.putLong(key, cd - 1);
		if (cd > 0) removeCoolDown(key);
	}


	public static CoolDownData getCooldownData(LivingEntity livingEntity) {
		return livingEntity.getData(DataReg.CoolDown.get());
	}
}
