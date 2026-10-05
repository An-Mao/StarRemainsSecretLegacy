package dev.anye.mc.srsl.reg.enchant.neko.item.nekolove;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class NekoLove extends NekoEI {
	public static final String ENCHANTMENT_KEY_LOVE = "love";
	public static final String ENCHANTMENT_KEY_LOVE_DATA = "love.data";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();

	public NekoLove(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
	}

	@Override
	public boolean canEnchant(ItemStack itemStack) {
		return itemStack.count() == 1;
	}


	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onSpawn);
		modEventBus.addListener(this::onAnvil);
		modEventBus.addListener(this::onItemUse);
		modEventBus.addListener(this::onToss);
	}

	@SubscribeEvent
	public void onSpawn(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof ItemEntity item && item.level() instanceof ServerLevel serverLevel) {
			ItemStack originalItem = item.getItem();
			if (EnchantHelper.hasEnchant(originalItem, Enchants.ni_love)) {
				CompoundTag nbt = EnchantHelper.getEnchantData(originalItem).getTagCopy();
				CompoundTag loveData = nbt.getCompound(ENCHANTMENT_KEY_LOVE_DATA).orElse(new CompoundTag());
				String uuid = loveData.getString(NekoLove.ENCHANTMENT_KEY_LOVE).orElse(null);
				if (uuid != null) {
					MinecraftServer server = serverLevel.getServer();
					if (server.getPlayerList().getPlayer(uuid) instanceof ServerPlayer serverPlayer && backItem(serverPlayer,originalItem)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}



	public boolean backItem(ServerPlayer serverPlayer,ItemStack originalItem){
		if (easyBack(serverPlayer,originalItem)) return true;
		return replaceBack(serverPlayer,originalItem);
	}

	public boolean easyBack(ServerPlayer serverPlayer,ItemStack originalItem){
		Inventory inventory = serverPlayer.getInventory();
		while (!originalItem.isEmpty()) {
			int slot = inventory.getSlotWithRemainingSpace(originalItem);
			if (slot == -1) slot = inventory.getFreeSlot();
			if (slot == -1) return false;
			int slotHasSpaceFor = originalItem.getMaxStackSize() - inventory.getItem(slot).getCount();
			if (inventory.add(slot, originalItem.split(slotHasSpaceFor))) {
				serverPlayer.connection.send(inventory.createInventoryUpdatePacket(slot));
			}
		}
		return true;
	}

	public boolean replaceBack(ServerPlayer serverPlayer,ItemStack originalItem) {
		NonNullList<ItemStack> items = serverPlayer.getInventory().getNonEquipmentItems();
		ItemStack handItem = serverPlayer.getMainHandItem();
		if (EnchantHelper.hasEnchant(handItem, Enchants.ni_love)) {
			handItem = serverPlayer.getOffhandItem();
			if (EnchantHelper.hasEnchant(handItem, Enchants.ni_love)) {
				for (ItemStack itemStack : items) {
					if (!EnchantHelper.hasEnchant(itemStack, Enchants.ni_love)) {
						replaceItem(serverPlayer, InteractionHand.OFF_HAND, itemStack, originalItem);
						return true;
					}
				}
				return false;
			}
			replaceItem(serverPlayer, InteractionHand.OFF_HAND, handItem, originalItem);
			return true;
		}
		replaceItem(serverPlayer, InteractionHand.MAIN_HAND, handItem, originalItem);
		return true;
	}

	public void replaceItem(ServerPlayer serverPlayer, InteractionHand hand, ItemStack oldItem, ItemStack newItem){
		Direction playerFacing = serverPlayer.getDirection();
		BlockPos playerPos = serverPlayer.blockPosition();
		BlockPos dropPos = playerPos.relative(playerFacing, 2);
		ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), dropPos.getX() + 0.5, dropPos.getY(), dropPos.getZ() + 0.5, oldItem.copy());
		itemEntity.setDeltaMovement(0, 0.1, 0);
		serverPlayer.level().addFreshEntity(itemEntity);
		serverPlayer.setItemInHand(hand, newItem);
	}

	@SubscribeEvent
	public void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
		if (!anvilUpdateEvent.getPlayer().level().isClientSide() && EnchantHelper.hasEnchant(anvilUpdateEvent.getLeft(), Enchants.ni_love)) {
			CompoundTag nbt = EnchantHelper.getEnchantData(anvilUpdateEvent.getLeft()).getTagCopy();
			CompoundTag loveData = nbt.getCompound(ENCHANTMENT_KEY_LOVE_DATA).orElse(new CompoundTag());

			if (loveData.getString(ENCHANTMENT_KEY_LOVE).isPresent()) return;

			Item ritem = anvilUpdateEvent.getRight().getItem();
			if (ritem == Items.TROPICAL_FISH) {
				ItemStack originalItemCopy = anvilUpdateEvent.getLeft().copy();
				loveData.putString(ENCHANTMENT_KEY_LOVE, anvilUpdateEvent.getPlayer().getUUID().toString());
				nbt.put(ENCHANTMENT_KEY_LOVE_DATA, loveData);
				EnchantHelper.setEnchantData(originalItemCopy, nbt);
				anvilUpdateEvent.setOutput(originalItemCopy);
				anvilUpdateEvent.setXpCost(1);
				anvilUpdateEvent.setMaterialCost(1);
			}
		}
	}

	@SubscribeEvent
	public void onItemUse(LivingEntityUseItemEvent.Start event) {
		if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
			ItemStack originalItem = event.getItem();
			CompoundTag nbt = EnchantHelper.getEnchantData(originalItem).getTagCopy();
			CompoundTag loveData = nbt.getCompound(ENCHANTMENT_KEY_LOVE_DATA).orElse(new CompoundTag());
			loveData.getString(NekoLove.ENCHANTMENT_KEY_LOVE).ifPresent(uuid -> {
				if (!livingEntity.getUUID().toString().equals(uuid)) {
					livingEntity.hurtServer(serverLevel,livingEntity.damageSources().fellOutOfWorld(), livingEntity.getMaxHealth() * 0.2F);
				}
			});
		}
	}

	@SubscribeEvent
	public void onToss(ItemTossEvent event) {
		ItemEntity entity = event.getEntity();
		if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
			ItemStack originalItem = entity.getItem();
			if (EnchantHelper.hasEnchant(originalItem, Enchants.ni_love)) {
				CompoundTag nbt = EnchantHelper.getEnchantData(originalItem).getTagCopy();
				CompoundTag loveData = nbt.getCompound(ENCHANTMENT_KEY_LOVE_DATA).orElse(new CompoundTag());

				loveData.getString(ENCHANTMENT_KEY_LOVE).ifPresent(uuid -> {
					if (serverPlayer.getUUID().toString().equals(uuid)) {
						if (easyBack(serverPlayer,originalItem)){
							event.setCanceled(true);
						}
					}
				});
			}
		}
	}
}
