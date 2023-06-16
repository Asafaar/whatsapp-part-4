package com.example.whatsapp_part_4.Dialog;

/**
 * Represents a theme option.
 */
public class ThemeOption {
    private String name;
    private int colorResId;
    private int nameOfRes;

    /**
     * Constructs a ThemeOption object.
     *
     * @param name       The name of the theme option.
     * @param colorResId The resource ID of the color associated with the theme option.
     * @param nameOfRes  The resource ID of the name associated with the theme option.
     */
    public ThemeOption(String name, int colorResId, int nameOfRes) {
        this.name = name;
        this.colorResId = colorResId;
        this.nameOfRes = nameOfRes;
    }

    /**
     * Gets the name of the theme option.
     *
     * @return The name of the theme option.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the resource ID of the color associated with the theme option.
     *
     * @return The resource ID of the color.
     */
    public int getColorResId() {
        return colorResId;
    }

    /**
     * Gets the resource ID of the name associated with the theme option.
     *
     * @return The resource ID of the name.
     */
    public int getNameOfRes() {
        return nameOfRes;
    }
}
