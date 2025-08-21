package dev.anye.mc.srsl.enchant.zero.item.confusion;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Confusion extends ZeroItemE {
    private final float radius = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_CONFUSION,"radius");
    public Confusion() {
        super();
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
            if (pAttacker instanceof ServerPlayer && pTarget instanceof Mob mob){
                List<Entity> e = mob.level().getEntities(mob, mob.getBoundingBox().inflate(radius));
                Mob tar = null;
                double d =-1 , td ;
                for (Entity te : e ){
                    if (te instanceof Mob m && m != mob){
                        td = m.getEyePosition().distanceTo(pAttacker.getEyePosition());
                        if (d == -1){
                            d = td;
                        }else if (td < d){
                            d = td;
                            tar = m;
                        }
                    }
                }
                mob.setTarget(tar);
            }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
