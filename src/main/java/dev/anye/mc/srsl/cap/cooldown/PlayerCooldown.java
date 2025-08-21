package dev.anye.mc.srsl.cap.cooldown;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PlayerCooldown {
    private static final String KEY = "PlayerItemCD";
    private static final String[] KEYS ={
            "AdaptiveArmorCD",
            "AdaptiveArmorTime",
            "NaturalArmorCD",
            "WarlordArmorCD"
    };
    private final Gson gson = new Gson();
    private HashMap<String, Integer> data = new HashMap<>();
    public PlayerCooldown(){
        for (String a:KEYS) {
            data.put(a, 0);
        }
    }

    public void setData(String key,int time) {
        this.data.put(key,time);
    }
    public boolean isData(String key){
        return this.data.get(key)> 0;
    }
    public void subCD(){
        this.data.forEach((key,value)->{
            if (value > 0) {
                this.data.put(key, value - 1);
            }
        });
    }
    public void copyFrom(PlayerCooldown source){
        this.data = source.data;
    }
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putString(KEY,gson.toJson(this.data));
    }
    public void loadNBTData(CompoundTag nbt)
    {
        String s = nbt.getString(KEY);
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        this.data = gson.fromJson(s, type);
    }
}
