package dev.anye.mc.srsl.enchant.zero.item.bright;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class BrightEvent {
    @SubscribeEvent
    public static void onMobTick(PlayerTickEvent.Pre event){
        if (Bright.ENABLE && event.getEntity() instanceof ServerPlayer serverPlayer){
            if (EnchantHelper.hasEnchant(serverPlayer.getMainHandItem(), Enchants.zi_bright)) {
                List<Monster> monsters = serverPlayer.level().getEntitiesOfClass(Monster.class, serverPlayer.getBoundingBox().inflate(16));
                for (Monster monster : monsters) {
                    PathNavigation pn = monster.getNavigation();
                    Vec3 vec3 = DefaultRandomPos.getPosAway(monster, 16, 7, serverPlayer.position());
                    if (vec3 != null) {
                        Path path = pn.createPath(vec3.x, vec3.y, vec3.z, 0);
                        if (serverPlayer.distanceToSqr(vec3.x, vec3.y, vec3.z) > serverPlayer.distanceToSqr(monster)) {
                            pn.moveTo(path,2.0D);
                        }
                    }
                }
            }
        }
    }
}
