package dev.anye.mc.srsl.datagen;

import com.google.common.collect.ImmutableList;
import dev.anye.mc.srsl.reg.block.BlockReg;
import dev.anye.mc.srsl.reg.item.ItemReg;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataGen_Recipe extends RecipeProvider {
	//public static final List<ItemLike> StarRemains = List.of(BlockReg.STAR_REMAINS.get());
	//private final HolderGetter<Item> items;

	protected DataGen_Recipe(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
		super(recipeOutput, advancementOutput);
		//this.items = registries.lookupOrThrow(Registries.ITEM);
	}

	public static MultiRegistryBootstrap create() {
		return new MultiRegistryBootstrap() {
			@Override
			public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
			}

			@Override
			public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
				new DataGen_Recipe(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
			}
		};
	}

	@Override
	protected void buildRecipes() {
		ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemReg.RevelationStone.get())
				.pattern("ABA")
				.pattern("BCB")
				.pattern("DBD")
				.define('A', Items.ECHO_SHARD)
				.define('B', Items.LAPIS_BLOCK)
				.define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
				.define('D', Items.NETHER_STAR)
				.unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()), has(ItemReg.STAR_REMAINS_ESSENCE.value()))
				.save(output);
		ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemReg.STRUCTURE_SCROLL.get())
				.pattern("ABA")
				.pattern("BCB")
				.pattern("DBD")
				.define('A', Items.DRAGON_HEAD)
				.define('B', Items.ECHO_SHARD)
				.define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
				.define('D', Items.NETHER_STAR)
				.unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()), has(ItemReg.STAR_REMAINS_ESSENCE.value()))
				.save(output);
		oreSmelting(List.of(BlockReg.STAR_REMAINS.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ItemReg.STAR_REMAINS_ESSENCE.get(), 1F, 1600, "star_remains");
	}
}
