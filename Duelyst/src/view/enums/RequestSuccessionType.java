package view.enums;

import view.MessageViewer;

public enum RequestSuccessionType {
    MOVE_TO();
    private String message;

    RequestSuccessionType() {

    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void printMessage() {
        MessageViewer messageViewer = MessageViewer.getInstance();
        messageViewer.collectionMessage(message);
    }

    RequestSuccessionType(String message) {
        this.message = message;
    }
}
