package dev.anye.mc.srsl.datagen;

import com.google.common.collect.ImmutableList;
import dev.anye.mc.srsl.block.BlockReg;
import dev.anye.mc.srsl.item.ItemReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataGen_Recipe extends RecipeProvider implements IConditionBuilder {
    public static final ImmutableList<ItemLike> StarRemains = ImmutableList.of( BlockReg.STAR_REMAINS.get());
    public DataGen_Recipe(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(pOutput,lookup);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemReg.RevelationStone.get(), 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("DBD")
                .define('A',Items.ECHO_SHARD)
                .define('B',Items.LAPIS_BLOCK)
                .define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
                .define('D', Items.NETHER_STAR)
                .unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()),has(ItemReg.STAR_REMAINS_ESSENCE.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemReg.STRUCTURE_SCROLL.get(), 1)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("DBD")
                .define('A',Items.DRAGON_HEAD)
                .define('B',Items.ECHO_SHARD)
                .define('C', ItemReg.STAR_REMAINS_ESSENCE.value())
                .define('D', Items.NETHER_STAR)
                .unlockedBy(getHasName(ItemReg.STAR_REMAINS_ESSENCE.value()),has(ItemReg.STAR_REMAINS_ESSENCE.value()))
                .save(recipeOutput);
        oreSmelting(recipeOutput,StarRemains,RecipeCategory.MISC, ItemReg.STAR_REMAINS_ESSENCE.get(), 1F, 1600, "star_remains");
    }
}
