package it.hyspace.survivalgamesx.enums;

import it.hyspace.survivalgamesx.SGPlugin;

public enum MessageConfiguration {

    PREFIX("prefix"),
    NO_PERMS("error.not-permession");


    private final String path;

    MessageConfiguration(String path) {
        this.path = path;
    }

    public String getString() {
        String message = SGPlugin.getInstance().getMessages().getString(path);
        if (message == null) {
            message = "";
        }

        message = message.replace("%prefix%", SGPlugin.getInstance().getMessages().getString("prefix"));
        return message;
    }

}
