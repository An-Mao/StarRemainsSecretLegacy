package dev.anye.mc.srsl.config.enchants$config;

import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.srsl.config.ConfigCore;
import dev.anye.mc.srsl.core.Enchant;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;

public class EnchantsConfig extends _JsonConfig<Map<String, EnchantsConfigData>> {
    public static final String file = _File.getFilePath(ConfigCore.ConfigDir,"enchants.json");

    public static final EnchantsConfig INSTANCE = new EnchantsConfig();
    public EnchantsConfig() {
        super(file, EnchantsConfigDefaultData.data, new TypeToken<>() {
        });
    }

    public EnchantsConfigData getConfig(DeferredHolder<Enchant, Enchant> enchantment){
        return getData().get(enchantment.getId().getPath());
    }
    public EnchantsConfigData getConfig(String id){
        return getData().get(id);
    }
    public JsonObject getParameters(DeferredHolder<Enchant, Enchant>  enchantment){
        return getConfig(enchantment).getParameters();
    }
    public float getValue(DeferredHolder<Enchant, Enchant>  enchantment, String key){
        if (enchantment == null){
            return 0;
        }
        return getParameters(enchantment).get(key).getAsFloat();
    }
    public boolean getBoolean(DeferredHolder<Enchant, Enchant>  enchantment, String key){
        if (enchantment == null){
            return false;
        }
        return getParameters(enchantment).get(key).getAsBoolean();
    }
    public int getMaxLevel(DeferredHolder<Enchant, Enchant>  enchantment){
        return (int) getValue(enchantment,"maxLevel");
    }
    public boolean isEnable(DeferredHolder<Enchant, Enchant>  enchantment){
        if (enchantment == null){
            return false;
        }
        return getConfig(enchantment).isEnable();
    }
    public boolean isEnable(String id){
        EnchantsConfigData configData = getConfig(id);
        if (configData == null){
            return false;
        }
        return configData.isEnable();
    }
}
