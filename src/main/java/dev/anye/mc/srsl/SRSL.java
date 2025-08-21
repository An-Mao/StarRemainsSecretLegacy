package dev.anye.mc.srsl;

import dev.anye.mc.srsl.block.BlockReg;
import dev.anye.mc.srsl.core.DataReg;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.item.ItemReg;
import dev.anye.mc.srsl.item.NUCreativeTabs;
import dev.anye.mc.srsl.structure.SRSLStructureTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(SRSL.MOD_ID)
public class SRSL
{
    public static final String MOD_ID = "srsl";
    public SRSL(IEventBus modEventBus, ModContainer modContainer){
        modEventBus.addListener(this::registerRegistries);
        //NeoForge.EVENT_BUS.register(this);
        DataReg.register(modEventBus);
        EnchantReg.register(modEventBus);


        Effects.register(modEventBus);
        ItemReg.register(modEventBus);
        BlockReg.register(modEventBus);

        SRSLStructureTypes.reg(modEventBus);
        NUCreativeTabs.reg(modEventBus);
    }

    public void registerRegistries(NewRegistryEvent event) {
        event.register(EnchantReg.REGISTRY);

    }
}
