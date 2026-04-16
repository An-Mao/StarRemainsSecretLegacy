package dev.anye.mc.srsl.datagen;

import com.google.common.collect.ImmutableList;
import dev.anye.mc.srsl.block.BlockReg;
import dev.anye.mc.srsl.item.ItemReg;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataGen_Recipe extends RecipeProvider{
    public static final ImmutableList<ItemLike> StarRemains = ImmutableList.of( BlockReg.STAR_REMAINS.get());
    private final HolderGetter<Item> items;
    protected DataGen_Recipe(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemReg.RevelationStone.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("DBD")
                .define('A',Items.ECHO_SHARD)
                .define('B',Items.LAPIS_BLOCK)
                .define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
                .define('D', Items.NETHER_STAR)
                .unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()),has(ItemReg.STAR_REMAINS_ESSENCE.value()))
                .save(output);
        ShapedRecipeBuilder.shaped(items,RecipeCategory.MISC, ItemReg.STRUCTURE_SCROLL.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("DBD")
                .define('A',Items.DRAGON_HEAD)
                .define('B',Items.ECHO_SHARD)
                .define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
                .define('D', Items.NETHER_STAR)
                .unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()),has(ItemReg.STAR_REMAINS_ESSENCE.value()))
                .save(output);
        oreSmelting(StarRemains,RecipeCategory.MISC,CookingBookCategory.MISC, ItemReg.STAR_REMAINS_ESSENCE.get(), 1F, 1600, "star_remains");
    }
    
    public static final class Runner extends RecipeProvider.Runner {
        public Runner(CompletableFuture<HolderLookup.Provider> lookupProvider,PackOutput output) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider lookupProvider, @NotNull RecipeOutput output) {
            return new DataGen_Recipe(lookupProvider, output);
        }

        @Override
        public @NotNull String getName() {
            return "end recipes";
        }
    }
}
