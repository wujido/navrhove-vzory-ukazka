package cz.wujido.shottrack.entities;

public class Notification {
    public final String content;
    public final Type type;

    public Notification(String content, Type type) {
        this.content = content;
        this.type = type;
    }

    public enum Type {
        ERR("31m"),
        SUCCESS("32m");

        public final String ansiColor;

        Type(String ansiColor) {

            this.ansiColor = ansiColor;
        }
    }
}
