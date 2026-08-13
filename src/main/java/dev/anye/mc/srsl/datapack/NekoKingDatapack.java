package dev.anye.mc.srsl.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record NekoKingData(int refine, int exp) {
	public static final Codec<NekoKingData> CODEC = RecordCodecBuilder.create(
			nekoKingDataInstance -> nekoKingDataInstance.group(
					Codec.INT.fieldOf("refine").forGetter(NekoKingData::refine),
					Codec.INT.fieldOf("exp").forGetter(NekoKingData::exp)
			).apply(nekoKingDataInstance, NekoKingData::new)
	);
}