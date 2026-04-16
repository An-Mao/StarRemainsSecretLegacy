package dev.anye.mc.srsl.enchant.phenomenon;

import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.srsl.core.Enchant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PhenomenonEnchant extends Enchant {
    protected PhenomenonEnchant( ) {
        super();
    }
    @Override
    public @NotNull Component getFullname(int pLevel) {
        return ComponentStyle.Flash(Component.translatable(this.getDescriptionId()).getString());
        //return _AM_Color.RainbowTextColor(Component.translatable(this.getDescriptionId()).getString(),System.currentTimeMillis()/50);
    }
    @Override
    public boolean canEnchant(ItemStack stack) {
        if (isWeapon(stack)) {
            return true;
        }
        return super.canEnchant(stack);
    }
}
