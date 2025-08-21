package dev.anye.mc.srsl.config.neko$king;

import dev.anye.core.json._JsonConfig;
import com.google.gson.reflect.TypeToken;
import dev.anye.mc.srsl.config.ConfigCore;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class NekoKingConfig extends _JsonConfig<Map<String, NekoKingConfigData>> {
    public static final String file =  ConfigCore.ConfigDir +"NekoKingRefineItem.json";

    public static final NekoKingConfig INSTANCE = new NekoKingConfig();
    public NekoKingConfig() {
        super(file, """
                {
                  "minecraft:iron_ingot": {
                    "refine": 1,
                    "exp":100
                  },
                  "minecraft:diamond": {
                    "refine": 5,
                    "exp":500
                  },
                  "minecraft:netherite_ingot": {
                    "refine": 20,
                    "exp":2000
                  }
                }""", new TypeToken<>(){});
    }
    public int getRefine(Item item){
        return getRefine(ForgeRegistries.ITEMS.getKey(item).toString());
    }
    public int getRefine(String id){
        NekoKingConfigData nekoKingConfig = getDatas().get(id);
        if (nekoKingConfig != null){
            return nekoKingConfig.getRefine();
        }
        return 0;
    }
    public int getExp(Item item){
        return getExp(ForgeRegistries.ITEMS.getKey(item).toString());
    }
    public int getExp(String id){
        NekoKingConfigData nekoKingConfig = getDatas().get(id);
        if (nekoKingConfig != null){
            return nekoKingConfig.getExp();
        }
        return 0;
    }
}
