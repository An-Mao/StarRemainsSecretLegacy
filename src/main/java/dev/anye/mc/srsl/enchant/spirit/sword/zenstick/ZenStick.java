package dev.anye.mc.srsl.enchant.spirit.sword.zenstick;

import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import org.jetbrains.annotations.NotNull;

public class ZenStick extends SSE {
    private static final MobEffectInstance YanLing = new MobEffectInstance(Effects.YAN_LING.getDelegate(),1,200);

    public ZenStick() {
        super();
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide() && pTarget instanceof Mob mob){
            if (mob instanceof Monster){
                int i = 1;
                for (;i < 18 ;i++){
                    String tag = "chaodulv"+i;
                    if (!pTarget.entityTags().contains(tag)){
                        pTarget.addTag(tag);
                        break;
                    }
                }
                //System.out.println("i"+i);
                if (i == 17){
                    pTarget.kill((ServerLevel)pAttacker.level());
                }
            }else {
                mob.addEffect(YanLing);
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
