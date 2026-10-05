package dev.anye.mc.srsl.config.enchants;


import dev.anye.core.data._Data;

import java.util.function.Consumer;

public record EnchantConfigData(boolean enable, _Data parameters) {


	public static  EnchantConfigData create(_Data parameters){
		return new EnchantConfigData(true,parameters);
	}

	public static EnchantConfigData create(Consumer<_Data> consumer){
		return create(true,_Data.create(consumer));
	}

	public static EnchantConfigData create(boolean enable, _Data parameters){
		return new EnchantConfigData(enable,parameters);
	}

	public static EnchantConfigData empty(){
		return new EnchantConfigData(true,_Data.empty());
	}
}
