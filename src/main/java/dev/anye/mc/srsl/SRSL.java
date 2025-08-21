package dev.anye.mc.srsl;

import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SRSL.MOD_ID)
public class SRSL
{
    public static final String MOD_ID = "srsl";
    public SRSL(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Effects.register(modEventBus);
        EnchantmentRegister.register(modEventBus);
    }
}
