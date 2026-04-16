package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.core.EnchantHelper;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LootItemRandomChanceWithEnchantedBonusCondition.class,priority = 1)
public class LootItemRandomChanceWithEnchantedBonusConditionMixin {
    @Final
    @Shadow
    private float unenchantedChance;

    @Final
    @Shadow
    private LevelBasedValue enchantedChance;

    @Final
    @Shadow
    private Holder<Enchantment> enchantment;

    /**
     * @author AnMao
     * @reason add loot
     */
    @Inject(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At("HEAD"), cancellable = true)
    public void ne$test$add(LootContext pContext, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = pContext.getParameter(LootContextParams.ATTACKING_ENTITY);
        int i = entity instanceof LivingEntity livingentity ? EnchantmentHelper.getEnchantmentLevel(this.enchantment, livingentity) : 0;
        float f = i > 0 ? this.enchantedChance.calculate(i) : this.unenchantedChance;
        if (entity instanceof ServerPlayer serverPlayer) {
            //System.out.println("loot 1");
            ItemStack item = serverPlayer.getMainHandItem();
            CompoundTag nbt = EnchantHelper.getAllData(item);
            CompoundTag dualityData = nbt.getCompound("duality.data").orElse(new CompoundTag());
            int a = dualityData.getInt("duality").orElse(0);
            if (a > 0) {
                a /= 5;
                f += a;
            }
        }
        cir.setReturnValue(pContext.getRandom().nextFloat() < f);
        //return pContext.getRandom().nextFloat() < f;
    }

}
