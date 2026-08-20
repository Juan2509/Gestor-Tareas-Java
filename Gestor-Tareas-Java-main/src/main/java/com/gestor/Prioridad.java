package com.gestor;

public enum Prioridad {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private final String etiqueta;

    Prioridad(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static String[] listar() {
        Prioridad[] valores = values();
        String[] etiquetas = new String[valores.length];

        for (int i = 0; i < valores.length; i++) {
            etiquetas[i] = valores[i].getEtiqueta();
        }

        return etiquetas;
    }
}