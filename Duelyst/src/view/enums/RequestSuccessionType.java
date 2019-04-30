package view.enums;

import view.MessageViewer;

public enum RequestSuccessionType {
    MOVE_TO();
    private String message;

    RequestSuccessionType() {

    }

    RequestSuccessionType(String message) {
        this.message = message;
    }

    public void printMessage() {
        MessageViewer messageViewer = MessageViewer.getInstance();
        messageViewer.collectionMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
