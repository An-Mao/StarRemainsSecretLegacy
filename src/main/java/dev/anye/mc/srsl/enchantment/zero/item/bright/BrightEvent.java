package dev.anye.mc.srsl.enchantment.zero.item.bright;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class BrightEvent {
    @SubscribeEvent
    public static void onMobTick(TickEvent.PlayerTickEvent event){
        if (Bright.ENABLE && event.player instanceof ServerPlayer serverPlayer){
            if (serverPlayer.getMainHandItem().getEnchantmentLevel(NekoEnchantments.zi_bright) > 0) {
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
