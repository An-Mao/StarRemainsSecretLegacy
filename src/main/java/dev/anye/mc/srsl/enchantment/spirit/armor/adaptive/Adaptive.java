package dev.anye.mc.srsl.enchantment.spirit.armor.adaptive;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.spirit.armor.SAE;
import net.minecraft.world.item.enchantment.Enchantment;

public class Adaptive extends SAE {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.E_ADAPTIVE);
    public Adaptive() {
        super(Enchantment.Rarity.VERY_RARE);
    }
}
