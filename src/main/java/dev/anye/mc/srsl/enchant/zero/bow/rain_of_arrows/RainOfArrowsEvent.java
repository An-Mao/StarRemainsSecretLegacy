package dev.anye.mc.srsl.enchant.zero.bow.rain_of_arrows;

import dev.anye.core.math._Math;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class RainOfArrowsEvent {
    private static final float hPIE = (float) (Math.PI / 180F);
    private static final float number = EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"number");
    private static final int minXRot = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"minXRot");
    private static final int maxXRot = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"maxXRot");
    private static final int minYRot = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"minYRot");
    private static final int maxYRot = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"maxYRot");
    private static final float velocity = EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"velocity");
    private static final float inaccuracy = EnchantsConfig.INSTANCE.getValue(EnchantReg.RAIN_OF_ARROWS,"inaccuracy");
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event){
        if (RainOfArrows.ENABLE && event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.getMainHandItem();
            if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.rainOfArrows) && event.getSource().getDirectEntity() instanceof AbstractArrow arrow){
                CompoundTag tag = arrow.getPersistentData().copy();
                if (!tag.getBoolean("shadow")) {
                    LivingEntity t = event.getEntity();
                    tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(arrow.getType()).toString());
                    for (int i = 0; i<number;i++) {
                        EntityType.create(tag, serverPlayer.level()).ifPresent(entity -> {
                            if (entity instanceof AbstractArrow abstractArrow) {
                                abstractArrow.getPersistentData().putBoolean("shadow", true);
                                abstractArrow.setCritArrow(arrow.isCritArrow());
                                abstractArrow.setBaseDamage(arrow.getBaseDamage());
                                abstractArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                                double x = t.getX() + _Math.RD.getIntRandomNumber(-1,1);
                                double y = t.getY() + t.getBbHeight() + 2.0F;
                                double z = t.getZ() + _Math.RD.getIntRandomNumber(-1,1);
                                abstractArrow.setPos(x,y,z);
                                int xr = _Math.RD.getIntRandomNumber(minXRot,maxXRot);
                                int yr = _Math.RD.getIntRandomNumber(minYRot,maxYRot);
                                float f = -Mth.sin(yr * hPIE * Mth.cos(xr * hPIE));
                                float f1 = -Mth.sin(xr * hPIE);
                                float f2 = Mth.cos(yr * hPIE) * Mth.cos(xr * hPIE);
                                abstractArrow.shoot(f, f1, f2, velocity, inaccuracy);
                                Vec3 vec3 = serverPlayer.getDeltaMovement();
                                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().add(vec3.x, serverPlayer.onGround() ? 0.0D : vec3.y, vec3.z));
                                serverPlayer.level().addFreshEntity(abstractArrow);
                            }
                        });
                    }
                }
            }
        }
    }
}
