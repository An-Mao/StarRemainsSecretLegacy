package dev.anye.mc.srsl.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public record NekoKingDatapack(int refine, int exp) {
	public static final Codec<NekoKingDatapack> CODEC = RecordCodecBuilder.create(
			nekoKingDataInstance -> nekoKingDataInstance.group(
					Codec.INT.fieldOf("refine").forGetter(NekoKingDatapack::refine),
					Codec.INT.fieldOf("exp").forGetter(NekoKingDatapack::exp)
			).apply(nekoKingDataInstance, NekoKingDatapack::new)
	);

	public static final ResourceKey<Registry<NekoKingDatapack>> KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "enchant/neko/king"));


	public static void reg(BootstrapContext<NekoKingDatapack> bootstrap) {
		bootstrap.register(
				ResourceKey.create(KEY, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, Items.IRON_INGOT.getDescriptionId())),
				new NekoKingDatapack(10, 100));
		bootstrap.register(
				ResourceKey.create(KEY, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, Items.DIAMOND.getDescriptionId())),
				new NekoKingDatapack(40, 400));
		bootstrap.register(
				ResourceKey.create(KEY, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, Items.NETHERITE_INGOT.getDescriptionId())),
				new NekoKingDatapack(150, 15000));
	}

	public static Identifier getItemKey(Item item) {
		return Identifier.fromNamespaceAndPath(SRSL.MOD_ID, item.getDescriptionId());
	}

	public static int getRefine(Level level, Item item) {
		return getRefine(level, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, item.getDescriptionId()));
	}

	public static int getRefine(Level level, Identifier key) {
		int[] r = {0};
		level.registryAccess().lookup(KEY).ifPresent(nekoKingDatapacks -> {
			nekoKingDatapacks.get(key).ifPresent(nekoKingDatapackReference -> r[0] = nekoKingDatapackReference.value().refine);
		});
		return r[0];
	}
}