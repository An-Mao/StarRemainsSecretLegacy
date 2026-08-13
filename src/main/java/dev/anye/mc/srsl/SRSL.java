package dev.anye.mc.srsl;

import dev.anye.mc.srsl.config.ConfigCore;
import dev.anye.mc.srsl.config.enchants.EnchantIO;
import dev.anye.mc.srsl.reg.block.BlockReg;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.effect.Effects;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.item.ItemReg;
import dev.anye.mc.srsl.reg.item.NUCreativeTabs;
import dev.anye.mc.srsl.reg.structure.SRSLStructureTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(SRSL.MOD_ID)
public class SRSL {
	public static final String MOD_ID = "srsl";

	static {
		ConfigCore.init();
	}

	public SRSL(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(this::registerRegistries);
		//NeoForge.EVENT_BUS.register(this);
		DataReg.register(modEventBus);
		EnchantReg.register(modEventBus);
		ConfigCore.load();




		Effects.register(modEventBus);
		ItemReg.register(modEventBus);
		BlockReg.register(modEventBus);

		SRSLStructureTypes.reg(modEventBus);
		NUCreativeTabs.reg(modEventBus);


		modEventBus.addListener(this::fml);
	}

	public void registerRegistries(NewRegistryEvent event) {
		event.register(EnchantReg.ENCHANT.registry());
	}

	public void fml(FMLCommonSetupEvent event){
		if (ConfigCore.outDefaultConfig) {
			EnchantIO.OutDefaultConfig();
		}
		EnchantReg.ENCHANT.foreach(enchant -> enchant.event(NeoForge.EVENT_BUS));
	}



}
