package de.andreasbreer.gameoflife.util;

public enum PropertyName {
    WINDOW_WIDTH("window.width"), WINDOW_HEIGHT("window.height"), WORLD_WIDTH("world.width"), WORLD_HEIGHT("world.height");

    final String propertyKey;

    private PropertyName(final String propertyKey) {
        this.propertyKey = propertyKey;
    }
}
