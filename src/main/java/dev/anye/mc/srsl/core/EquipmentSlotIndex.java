package dev.anye.mc.srsl.core;

import net.minecraft.world.entity.EquipmentSlot;

public class EquipmentSlotIndex {
    public static final int BASE = 100;
    public static final int HEAD = BASE + EquipmentSlot.HEAD.getIndex();
    public static final int CHEST = BASE + EquipmentSlot.CHEST.getIndex();
    public static final int LEGS = BASE + EquipmentSlot.LEGS.getIndex();
    public static final int FEET = BASE + EquipmentSlot.FEET.getIndex();
    public static final int MAINHAND = 98;
    public static final int OFFHAND = 99;
    public static final int BODY = BASE + 5;
    public static final int SADDLE = BASE + 6;
    private EquipmentSlotIndex(){}
}
