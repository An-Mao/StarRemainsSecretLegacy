package dev.anye.mc.srsl.config.enchantments$config;

import dev.anye.core.json._JsonConfig;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.anye.mc.srsl.config.ConfigCore;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class EnchantmentsConfig extends _JsonConfig<Map<String, EnchantmentsConfigData>> {
    public static final String file = ConfigCore.ConfigDir +"enchantments.json";
    public static final EnchantmentsConfig INSTANCE = new EnchantmentsConfig();
    public EnchantmentsConfig() {
        super(file, EnchantmentsConfigDefaultData.data, new TypeToken<>() {
        });
    }

    public EnchantmentsConfigData getConfig(RegistryObject<Enchantment> enchantment){
        return getDatas().get(enchantment.getId().getPath());
    }
    public EnchantmentsConfigData getConfig(String id){
        return getDatas().get(id);
    }
    public JsonObject getParameters(RegistryObject<Enchantment> enchantment){
        return getConfig(enchantment).getParameters();
    }
    public float getValue(RegistryObject<Enchantment> enchantment,String key){
        if (enchantment == null){
            return 0;
        }
        return getParameters(enchantment).get(key).getAsFloat();
    }
    public boolean getBoolean(RegistryObject<Enchantment> enchantment,String key){
        if (enchantment == null){
            return false;
        }
        return getParameters(enchantment).get(key).getAsBoolean();
    }
    public int getMaxLevel(RegistryObject<Enchantment> enchantment){
        return (int) getValue(enchantment,"maxLevel");
    }
    public boolean isEnable(RegistryObject<Enchantment> enchantment){
        if (enchantment == null){
            return false;
        }
        return getConfig(enchantment).isEnable();
    }
    public boolean isEnable(String id){
        EnchantmentsConfigData configData = getConfig(id);
        if (configData == null){
            return false;
        }
        return configData.isEnable();
    }
}
