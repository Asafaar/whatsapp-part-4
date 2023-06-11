package com.example.whatsapp_part_4.Dialog;

public class ThemeOption {
    private String name;
    private int colorResId;

    private int nameofres;

    public ThemeOption(String name, int colorResId, int nameofres) {
        this.name = name;
        this.colorResId = colorResId;
        this.nameofres = nameofres;
    }

    public String getName() {
        return name;
    }

    public int getColorResId() {
        return colorResId;
    }

    public int getNameofres() {
        return nameofres;
    }
}
