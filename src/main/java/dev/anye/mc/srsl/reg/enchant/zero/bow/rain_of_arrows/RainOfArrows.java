package dev.anye.mc.srsl.reg.enchant.zero.bow.rain_of_arrows;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.bow.ZeroBowEnchant;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntitySpawnRequest;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class RainOfArrows extends ZeroBowEnchant {
	private static final float HALF_PIE = (float) (Math.PI / 180F);
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("number",10)
				.add("minXRot",60)
				.add("maxXRot",90)
				.add("minYRot",-180)
				.add("maxYRot",180)
				.add("velocity",1F)
				.add("inaccuracy",1F)
		;
	});
	private final int number;
	private final int minXRot;
	private final int maxXRot;
	private final int minYRot;
	private final int maxYRot;
	private final float velocity;
	private final float inaccuracy;

	public record Data(int number,int minXRot,int maxXRot,int minYRot,int maxYRot,int velocity,int inaccuracy) {
		public static final Data DEFAULT = new Data(10,60,90,-180,180,1,1);
	}
	public RainOfArrows(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		number = config.asInt("number");
		minXRot = config.asInt("minXRot");
		maxXRot = config.asInt("maxXRot");
		minYRot = config.asInt("minYRot");
		maxYRot = config.asInt("maxYRot");
		velocity = config.asFloat("velocity");
		inaccuracy = config.asFloat("inaccuracy");
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAttack);
	}

	public void onAttack(LivingIncomingDamageEvent event) {
		if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack itemStack = serverPlayer.getMainHandItem();
			if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.rainOfArrows) && event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
				CompoundTag tag = arrow.getPersistentData().copy();
				if (!tag.getBoolean("shadow").orElse(false)) {
					LivingEntity t = event.getEntity();
					tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(arrow.getType()).toString());
					for (int i = 0; i < number; i++) {
						shoot(serverPlayer,tag,arrow,t);
					}
				}
			}
		}
	}
	private void shoot(ServerPlayer serverPlayer,CompoundTag tag,AbstractArrow arrow,LivingEntity t){
		EntityType.create(TagValueInput.create(new ProblemReporter.Collector(), serverPlayer.level().registryAccess(), tag), serverPlayer.level(), new EntitySpawnRequest(EntitySpawnReason.SPAWN_ITEM_USE, false)).ifPresent(entity -> {
			if (entity instanceof AbstractArrow abstractArrow) {
				abstractArrow.getPersistentData().putBoolean("shadow", true);
				abstractArrow.setCritArrow(arrow.isCritArrow());
				abstractArrow.setBaseDamage(arrow.getOrDefault(DataComponents.DAMAGE, 2));
				abstractArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
				double x = t.getX() + _Math.RD.getIntRandomNumber(-1, 1);
				double y = t.getY() + t.getBbHeight() + 2.0F;
				double z = t.getZ() + _Math.RD.getIntRandomNumber(-1, 1);
				abstractArrow.setPos(x, y, z);
				int xr = _Math.RD.getIntRandomNumber(minXRot, maxXRot);
				int yr = _Math.RD.getIntRandomNumber(minYRot, maxYRot);
				float f = -Mth.sin(yr * HALF_PIE * Mth.cos(xr * HALF_PIE));
				float f1 = -Mth.sin(xr * HALF_PIE);
				float f2 = Mth.cos(yr * HALF_PIE) * Mth.cos(xr * HALF_PIE);
				abstractArrow.shoot(f, f1, f2, velocity, inaccuracy);
				Vec3 vec3 = serverPlayer.getDeltaMovement();
				abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().add(vec3.x, serverPlayer.onGround() ? 0.0D : vec3.y, vec3.z));
				serverPlayer.level().addFreshEntity(abstractArrow);
			}
		});
	}
}
