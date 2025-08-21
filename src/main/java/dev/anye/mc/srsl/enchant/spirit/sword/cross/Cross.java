package dev.anye.mc.srsl.enchant.spirit.sword.cross;

import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Cross extends SSE {
    private final float multiplier = EnchantsConfig.INSTANCE.getValue(EnchantReg.E_CROSS,"multiplier");
    public Cross() {
        super();
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide  && pAttacker instanceof ServerPlayer serverPlayer){
            if (pTarget instanceof Bat){
                Collection<AttributeModifier> attack = ItemHelper.getAttributeModifiers(serverPlayer.getMainHandItem(),EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);//                        serverPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
                if (!attack.isEmpty()){
                    double damage = AttributeHelper.getAttributeModifierValue(attack) * multiplier;
                    pTarget.hurt(pAttacker.damageSources().fellOutOfWorld(), (float) damage);
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
