package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Unique
    private static final boolean nekoEnchantment$ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.TORI_NO_UTA);
    @Unique
    private static final float nekoEnchantment$quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.TORI_NO_UTA,"quota");
    @ModifyVariable(method = "releaseUsing",
            at = @At(value = "LOAD", ordinal = 0),
            ordinal = 0,
            name = "f",
            require = 0,
            allow = 1)
    private float ne$releaseUsing$ToriNoUta(float f, ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        /*
        if (nekoEnchantment$ENABLE && EnchantHelper.hasEnchant(pStack, Enchants.toriNoUta)){
            ItemStack itemStack = pEntityLiving.getOffhandItem();
            if (itemStack.getItem() == Items.FEATHER){
                itemStack.setCount(itemStack.getCount() - 1);
                return f * nekoEnchantment$quota;
            }
        }

         */
        return f;
    }
}
