package dev.anye.mc.srsl.enchantment.blood.vampirism;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.attribute.AttributeHelper;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.blood.BloodEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Vampirism extends BloodEnchantment {
    private final int maxLevel = EnchantmentsConfig.INSTANCE.getMaxLevel(EnchantmentRegister.B_DRINK_BLOOD);
    public Vampirism() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (pTarget instanceof LivingEntity){
            int base = Math.max(2, 7 - pLevel);
            Collection<AttributeModifier> atk = pAttacker.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
            float heal = _Math.log(AttributeHelper.getAttributeModifierValue(atk), base);
            if (pAttacker.getHealth() < pAttacker.getMaxHealth()) {
                pAttacker.heal(heal);
            }else {
                if (pAttacker instanceof ServerPlayer player){
                    int food = player.getFoodData().getFoodLevel();
                    if (food < 20) {
                        player.getFoodData().setFoodLevel((int) Math.min(20, food + heal));
                    }else {
                        pAttacker.setAbsorptionAmount(Math.min(pAttacker.getMaxHealth() , pAttacker.getAbsorptionAmount() + heal));
                    }
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
