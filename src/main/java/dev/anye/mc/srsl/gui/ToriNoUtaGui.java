package dev.anye.mc.srsl.gui;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import dev.anye.mc.srsl.enchant.zero.bow.tori_no_uta.ToriNoUta;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.*;

import java.lang.Math;
import java.util.HashMap;
import java.util.Optional;

public class ToriNoUtaGui {
    public static final String id = "tori_no_uta";
    public static final Identifier RESOURCE_LOCATION =  Identifier.fromNamespaceAndPath(SRSL.MOD_ID,id);
    private static final Identifier image = Identifier.fromNamespaceAndPath(SRSL.MOD_ID,"textures/enchant/feather.png");
    public static final boolean showEffects = EnchantsConfig.INSTANCE.getBoolean(EnchantReg.TORI_NO_UTA,"showEffects");
    private static final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.TORI_NO_UTA,"quota");
    private static final double a =  2 * Math.PI / quota;
    private static final double fr = -45.0 * Math.PI / 180.0;
    private static final double r = Math.PI / 360;
    private static double d = 0;
    public static boolean start = false;
    private static double t;
    private static final HashMap<Integer, FeatherData> map = new HashMap<>();



    public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker pPartialTick) {
        if (ToriNoUta.ENABLE && showEffects){
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null){
                ItemStack itemStack = player.getMainHandItem();
                if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && player.getOffhandItem().getItem() == Items.FEATHER){
                    int screenWidth = minecraft.getWindow().getGuiScaledWidth();
                    int screenHeight = minecraft.getWindow().getGuiScaledHeight();
                    int centerX = screenWidth / 2 - 8;
                    int centerY = screenHeight / 2 - 8;
                    if (start) {
                        if (t < 50) {
                            t+=0.5;
                        }
                        double angles = 0;
                        for (int i = 0; i < quota; i++) {
                            angles += a;
                            if (angles > Math.PI){
                                angles = angles - _Math.TWICE_PI;
                            }
                            if (angles < -Math.PI){
                                angles = angles + _Math.TWICE_PI;
                            }
                            double rr = 80 - t;
                            int x = (int) (rr * Math.cos(angles));
                            int y = (int) (rr * Math.sin(angles));
                            Matrix3x2fStack pose = guiGraphics.pose();
                            pose.pushMatrix();
                            pose.translate(centerX +y, centerY - x);
                            guiGraphics.item(new ItemStack(Items.FEATHER), x, y);
                            pose.popMatrix();
                        }
                    }else {
                        t = 0;
                        if (map.isEmpty()){
                            for (int i = 0; i < quota ; i++){
                                map.put(i, getRandom(screenWidth,screenHeight));
                            }
                        }
                        HashMap<Integer, FeatherData> newMap = new HashMap<>(map);
                        newMap.forEach((integer, featherData) -> {
                            if (featherData != null){
                                featherData.y += featherData.speed;
                                featherData.x += (int) (featherData.p + (_Math.RD.getRandomFloat() - 0.5f) * 2);
                                Matrix3x2fStack pose = guiGraphics.pose();
                                pose.pushMatrix();
                                pose.scale(featherData.resizing, featherData.resizing);
                                //pose.transform(0,0,1,pose.rotate());
                                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,image, featherData.x, featherData.y,0,0,16,16,16,16);
                                pose.popMatrix();
                                if (featherData.y > screenHeight || featherData.x > screenWidth || featherData.x < 0){
                                    map.put(integer, getRandom(screenWidth,screenHeight));
                                }else {
                                    map.put(integer, featherData);
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private static void draw(GuiGraphicsExtractor guiGraphics, int centerX, int centerY) {
        if (start) {
            if (t < 50) {
                t+=0.5;
            }
            double angles = 0;
            for (int i = 0; i < quota; i++) {
                angles += a;
                if (angles > Math.PI){
                    angles = angles - _Math.TWICE_PI;
                }
                if (angles < -Math.PI){
                    angles = angles + _Math.TWICE_PI;
                }
                double rr = 80 - t;
                int x = (int) (rr * Math.cos(angles));
                int y = (int) (rr * Math.sin(angles));
                Matrix3x2fStack pose = guiGraphics.pose();
                pose.pushMatrix();
                pose.translate((centerX + y),(centerY - x));
                //pose.mul(new Quaternionf(new AxisAngle4d(angles + fr,0,0,1)));
                guiGraphics.item(new ItemStack(Items.FEATHER), x, y);
                pose.popMatrix();
            }
        }else {
            t = 0;
            double angles = 0;
            for (int i = 0; i < quota; i++) {
                angles += a + d;
                int x = centerX + (int) (80 * Math.cos(angles));
                int y = centerY + (int) (80 * Math.sin(angles));
                Matrix3x2fStack pose = guiGraphics.pose();
                pose.pushMatrix();
                guiGraphics.item(new ItemStack(Items.FEATHER), x, y);
                pose.popMatrix();
            }
            d += r;
            if (d > Math.PI) {
                d -= Math.PI;
            }
        }
    }
    private static FeatherData getRandom(int screenWidth, int screenHeight){
        FeatherData featherData = new FeatherData();
        featherData.x = _Math.RD.getIntRandomNumber(0,screenWidth);
        featherData.y = 0;
        featherData.revolve = r * _Math.RD.getIntRandomNumber(0,360);
        featherData.speed = _Math.RD.getIntRandomNumber(1, 3);
        featherData.resizing = (float) _Math.RD.getIntRandomNumber(5, 15) / 10;
        featherData.p = _Math.RD.getIntRandomNumber(-3,3);
        return featherData;
    }
    public static class FeatherData {
        public int x,y,speed,p;
        public double revolve;
        public float resizing;
        public FeatherData(){}

        @Override
        public String toString() {
            return "FeatherData{" +
                    "x=" + x +
                    ", y=" + y +
                    ", revolve=" + revolve +
                    ", speed=" + speed +
                    ", resizing=" + resizing +
                    '}';
        }
    }
}
