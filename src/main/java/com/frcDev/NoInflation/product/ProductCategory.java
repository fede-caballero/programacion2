package com.frcDev.NoInflation.product;

public enum ProductCategory {
    DAIRY("Lacteos", "Quesos, leche, yogurt, mantequilla, crema, etc."),
    MEAT("Carnes", "Res, cerdo, pollo, pescado, mariscos, etc."),
    FRUITS("Frutas", "Manzanas, peras, uvas, fresas, platanos, etc."),
    VEGETABLES("Verduras", "Lechuga, espinaca, zanahoria, papa, cebolla, etc."),
    BAKERY("Panaderia", "Pan, tortillas, pasteles, galletas, etc."),
    BEVERAGES("Bebidas", "Refrescos, jugos, cervezas, vinos, licores, etc."),
    FROZEN("Congelados", "Helados, pizzas, papas, verduras, etc."),
    CLEANING("Limpieza", "Detergentes, jabones, cloro, escobas, trapeadores, etc."),
    PERSONAL("Cuidado personal", "Shampoo, jabon, pasta de dientes, papel higienico, etc.");

    private final String displayName;
    private final String icon;

    ProductCategory(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }
}
