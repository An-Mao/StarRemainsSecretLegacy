package dev.anye.mc.srsl.datapack;

import dev.anye.mc.srsl.SRSL;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class DatapackRegister {

	private DatapackRegister() {
	}

	@SubscribeEvent
	public static void register(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(
				NekoKingDatapack.KEY,
				NekoKingDatapack.CODEC,
				NekoKingDatapack.CODEC
		);
	}

}
