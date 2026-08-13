package dev.anye.mc.srsl.reg.enchant.zero.bow.tori_no_uta;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.gui.ToriNoUtaGui;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.bow.ZeroBowEnchant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;

public class ToriNoUta extends ZeroBowEnchant {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",7F).add("showEffects",true);
	});
	private final float quota;
	private final boolean showEffects;
	public record Data(float quota,boolean showEffects) {
		public static final Data DEFAULT = new Data(7,true);
	}
	public ToriNoUta(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
		showEffects = config.asBool("showEffects");
	}

	public float quota(){
		return quota;
	}

	public boolean showEffects(){
		return showEffects;
	}

	public boolean CanRun(Level level) {
		return showEffects && level.isClientSide();
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onArrowNock);
		modEventBus.addListener(this::onEntityJoin);
		modEventBus.addListener(this::onArrowLoose);
	}

	public void onArrowNock(ArrowNockEvent event) {
		if (CanRun(event.getLevel())) {
			ItemStack itemStack = event.getBow();
			if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && event.getEntity().getOffhandItem().getItem() == Items.FEATHER) {
				ToriNoUtaGui.setStart(true);
			}

		}
	}

	public void onEntityJoin(EntityJoinLevelEvent event) {
			if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof ServerPlayer serverPlayer && !arrow.getPersistentData().getBooleanOr("ToriNoUta",false)) {
				arrow.getPersistentData().putBoolean("ToriNoUta",true);
				if (serverPlayer.getOffhandItem().getItem() == Items.FEATHER && EnchantHelper.hasEnchant(serverPlayer.getMainHandItem(), Enchants.toriNoUta)) {
					serverPlayer.getOffhandItem().shrink(1);
					arrow.setDeltaMovement(arrow.getDeltaMovement().scale(quota));
				}
			}
	}

	public void onArrowLoose(ArrowLooseEvent event) {
		if (CanRun(event.getLevel())) {
			ItemStack itemStack = event.getBow();
			if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && event.getEntity().getOffhandItem().getItem() == Items.FEATHER) {
				ToriNoUtaGui.setStart(false);
			}

		}
	}
}
