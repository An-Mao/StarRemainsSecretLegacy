package dev.anye.mc.srsl.enchant.spirit.armor;

import dev.anye.mc.srsl.core.Enchant;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.enchant.spirit.SpiritE;
import net.minecraft.world.item.ItemStack;

public class SAE extends SpiritE {
    protected SAE() {
        super();
    }
    @Override
    public boolean canEnchant(ItemStack stack) {
        if (isChest(stack) && !hasSpirit(stack)) {
            return true;
        }
        return super.canEnchant(stack);
    }
    public boolean hasSpirit(ItemStack stack) {
        for (Enchant enchant : EnchantHelper.getEnchants(stack).keySet()) {
            if (enchant instanceof SAE) return true;
        }
        return false;
    }
}
