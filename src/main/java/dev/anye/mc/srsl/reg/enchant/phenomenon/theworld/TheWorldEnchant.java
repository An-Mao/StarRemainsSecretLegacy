package dev.anye.mc.srsl.reg.enchant.phenomenon.theworld;

import dev.anye.mc.cores.am.data.entity.TheWorld;
import dev.anye.mc.cores.am.register.DataRegister;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.phenomenon.PhenomenonEnchant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class TheWorldEnchant extends PhenomenonEnchant {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("time",80);
	});
	public final int time;
	public record Data(int time) {
		public static final Data DEFAULT = new Data(80);
	}
	public TheWorldEnchant(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		time = config.asInt("time");
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack) || isChest(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAttack);
	}

	public void onAttack(LivingIncomingDamageEvent event) {
		if (event.getSource().getEntity() instanceof Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer) {
				if (EnchantHelper.hasEnchant(serverPlayer.getMainHandItem(), Enchants.the_world) && !(event.getEntity() instanceof ServerPlayer)) {
					setTheWorldTime(event.getEntity());
				}
			} else {
				if (event.getEntity() instanceof ServerPlayer player && EnchantHelper.getEnchantLevel(Enchants.the_world, player) > 0) {
					setTheWorldTime(entity);
				}
			}
		}
	}

	public void setTheWorldTime(Entity e) {
		e.setData(DataRegister.THE_WORLD, new TheWorld(time));
	}
}
