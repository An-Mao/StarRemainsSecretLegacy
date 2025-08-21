package dev.anye.mc.srsl.enchant.spirit.sword.angel;

import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Angel extends SSE {

    public Angel() {
        super();
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (pAttacker instanceof ServerPlayer serverPlayer){
            Collection<AttributeModifier> attack = ItemHelper.getAttributeModifiers(serverPlayer.getMainHandItem(),EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);//                    serverPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
            if (!attack.isEmpty()) {
                double damage = 0.5;
                if (pTarget instanceof Monster){
                    damage = 1.0;
                }
                damage *= AttributeHelper.getAttributeModifierValue(attack);
                pTarget.hurt(pAttacker.damageSources().magic(),(float) damage);
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
