package dev.anye.mc.srsl.effect;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.breakdefense.BreakDefense;
import dev.anye.mc.srsl.effect.evilcreature.EvilCreature;
import dev.anye.mc.srsl.effect.yanling.YanLing;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Effects {
    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SRSL.MOD_ID);
    public static RegistryObject<MobEffect> YAN_LING = EFFECTS.register("yan_ling", YanLing::new);
    public static RegistryObject<MobEffect> BREAK_DEFENSE = EFFECTS.register("break_defense", BreakDefense::new);
    public static RegistryObject<MobEffect> EVIL_CREATURE = EFFECTS.register("evil_creature", EvilCreature::new);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
