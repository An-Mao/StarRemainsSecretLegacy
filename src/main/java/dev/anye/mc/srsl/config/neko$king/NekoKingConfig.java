package dev.anye.mc.srsl.config.neko$king;

import dev.anye.core.json._JsonConfig;
import dev.anye.mc.srsl.config.ConfigCore;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.Map;

public class NekoKingConfig extends _JsonConfig<Map<String, NekoKingConfigData>> {
    public static final String file =  ConfigCore.ConfigDir +"NekoKingRefineItem.json";

    public static final NekoKingConfig INSTANCE = new NekoKingConfig();
    public NekoKingConfig() {
        super(file, """
                {
                  "minecraft:iron_ingot": {
                    "refine": 10,
                    "exp":100
                  },
                  "minecraft:diamond": {
                    "refine": 40,
                    "exp":4000
                  },
                  "minecraft:netherite_ingot": {
                    "refine": 150,
                    "exp":15000
                  }
                }""", new TypeToken<>(){});
    }
    public int getRefine(Item item){
        return getRefine(BuiltInRegistries.ITEM.getKey(item).toString());
    }
    public int getRefine(String id){
        NekoKingConfigData nekoKingConfig = getDatas().get(id);
        if (nekoKingConfig != null){
            return nekoKingConfig.getRefine();
        }
        return 0;
    }
    public int getExp(Item item){
        return getExp(BuiltInRegistries.ITEM.getKey(item).toString());
    }
    public int getExp(String id){
        NekoKingConfigData nekoKingConfig = getDatas().get(id);
        if (nekoKingConfig != null){
            return nekoKingConfig.getExp();
        }
        return 0;
    }
}
