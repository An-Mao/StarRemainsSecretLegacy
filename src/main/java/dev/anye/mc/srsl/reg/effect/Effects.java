package dev.anye.mc.srsl.effect;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.breakdefense.BreakDefense;
import dev.anye.mc.srsl.effect.evilcreature.EvilCreature;
import dev.anye.mc.srsl.effect.yanling.YanLing;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Effects {
	public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, SRSL.MOD_ID);
	public static DeferredHolder<MobEffect, YanLing> YAN_LING = EFFECTS.register("yan_ling", YanLing::new);
	public static DeferredHolder<MobEffect, BreakDefense> BREAK_DEFENSE = EFFECTS.register("break_defense", BreakDefense::new);
	public static DeferredHolder<MobEffect, EvilCreature> EVIL_CREATURE = EFFECTS.register("evil_creature", EvilCreature::new);

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}
