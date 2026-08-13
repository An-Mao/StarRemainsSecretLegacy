package dev.anye.mc.srsl.gui;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

public class ToriNoUtaGui {
	public static final String ID = "tori_no_uta";
	public static final Identifier RESOURCE_LOCATION = Identifier.fromNamespaceAndPath(SRSL.MOD_ID, ID);
	private static final Identifier image = Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "textures/enchant/feather.png");
	private static final int FPS_LIMIT = 60;

	private static final float QUOTA = EnchantReg.TORI_NO_UTA.get().quota();

	private static final double ANGLE = 2 * Math.PI / QUOTA;
	private static final double REVOLVE_ANGLE = Math.PI / 360;

	private static boolean start = false;
	private static float t;

	private static final List<FeatherData> feathers = new ArrayList<>();


	private ToriNoUtaGui(){}


	public static void setStart(boolean newState){
		start = newState;
	}

	public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
		if (EnchantReg.TORI_NO_UTA.get().enable() && EnchantReg.TORI_NO_UTA.get().showEffects()) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.player instanceof LocalPlayer player) {
				ItemStack itemStack = player.getMainHandItem();
				if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && player.getOffhandItem().is(Items.FEATHER)) {
					render(minecraft,guiGraphics);
				}else feathers.clear();
			}
		}
	}
	private static void render(Minecraft minecraft,GuiGraphicsExtractor guiGraphics){
		int screenWidth = minecraft.getWindow().getGuiScaledWidth();
		int screenHeight = minecraft.getWindow().getGuiScaledHeight();
		if (start) {
			charge(guiGraphics, screenWidth, screenHeight, minecraft.getFps());
			return;
		}
		t = 0;
		normal(guiGraphics, screenWidth, screenHeight, minecraft.getFps());
	}

	private static void normal(GuiGraphicsExtractor guiGraphics,int screenWidth,int screenHeight,int fps){
		if (feathers.isEmpty()) {
			for (int i = 0; i < QUOTA; i++) {
				feathers.add(FeatherData.getRandom(screenWidth, screenHeight));
			}
		}
		feathers.forEach(featherData -> {
			featherData.tick(guiGraphics,fps);
			if (featherData.y > screenHeight || featherData.x > screenWidth || featherData.x < 0) {
				featherData.random(screenWidth, screenHeight);
			}

		});
	}

	private static void charge(GuiGraphicsExtractor guiGraphics,int screenWidth,int screenHeight,int fps){
		if (t < 50) t += 0.5F / fps * FPS_LIMIT ;
		float centerX = (float) screenWidth / 2 - 8;
		float centerY = (float) screenHeight / 2 - 8;
		double angles = 0;
		for (int i = 0; i < QUOTA; i++) {
			angles = _Math.pullBackWithPI(angles + ANGLE);
			double rr = 80 - t ;

			double x = rr * Math.cos(angles) ;
			double y = rr * Math.sin(angles) ;

			Matrix3x2fStack pose = guiGraphics.pose();
			pose.pushMatrix();
			pose.translate(centerX,centerY);
			pose.translate((float) y, (float) -x);
			//guiGraphics.item(new ItemStack(Items.FEATHER), (int) x, (int) y);
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, image, 0, 0, 0, 0, 16, 16, 16, 16);
			pose.popMatrix();
		}
	}

	public static class FeatherData {
		public float x;
		public float y;
		public int speed;
		public int p;
		public double revolve;
		public float size;

		public FeatherData(
				float x,float y,
				int speed, int p,
				double revolve, float size
		) {

			this.x = x;
			this.y = y;
			this.speed = speed;
			this.p = p;
			this.revolve = revolve;
			this.size = size;

		}
		public FeatherData() {
			this(0,0,0,0,0,0);
		}


		public void addY(int v){
			y += v;
		}
		public void addX(float v){
			addX ((int) v);
		}
		public void addX(int v){
			x += v;
		}

		public void tick(GuiGraphicsExtractor guiGraphics, float fps){

			Matrix3x2fStack pose = guiGraphics.pose();
			pose.pushMatrix();
			float zoom = FPS_LIMIT / fps;
			y += speed * zoom;
			x += p * zoom + (_Math.RD.getRandomFloat() - 0.5f) * 2 * zoom;
			pose.translate(x,y);
			pose.scale(size, size);
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, image, 0, 0, 0, 0, 16, 16, 16, 16);
			pose.popMatrix();
		}

		public FeatherData random(int screenWidth, int screenHeight){
			this.x = _Math.RD.getIntRandomNumber(0,screenWidth);
			this.y = 0;
			this.revolve = REVOLVE_ANGLE * _Math.RD.getIntRandomNumber(0,360);
			this.speed = _Math.RD.getIntRandomNumber(1, 3);
			this.size = (float) _Math.RD.getIntRandomNumber(5, 15) / 10;
			this.p = _Math.RD.getIntRandomNumber(-3,3);
			return this;
		}

		@Override
		public String toString() {
			return "FeatherData{" +
					"x=" + x +
					", y=" + y +
					", revolve=" + revolve +
					", speed=" + speed +
					", resizing=" + size +
					'}';
		}


		private static FeatherData getRandom(int screenWidth, int screenHeight) {
			return new FeatherData().random(screenWidth,screenHeight);
		}
	}
}
