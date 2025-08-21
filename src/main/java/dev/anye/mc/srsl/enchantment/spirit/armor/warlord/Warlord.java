package dev.anye.mc.srsl.enchantment.spirit.armor.warlord;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.spirit.armor.SAE;
import net.minecraft.world.item.enchantment.Enchantment;

public class Warlord extends SAE {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.E_WARLORD);
    public Warlord() {
        super(Enchantment.Rarity.VERY_RARE);
    }
}
