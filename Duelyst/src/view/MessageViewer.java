package view;


public class MessageViewer {
    private static MessageViewer singleInstance = null;

    private MessageViewer() {
    }

    public static MessageViewer getInstance() {
        if (singleInstance == null) {
            singleInstance = new MessageViewer();
        }
        return singleInstance;
    }

    public void collectionMessage(String message) {
        System.out.println(message);
    }
}
