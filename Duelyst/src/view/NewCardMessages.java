package view;

public class NewCardMessages {

    private static NewCardMessages singleInstance=null;
    public static NewCardMessages getInstance(){
        if(singleInstance==null)
            singleInstance=new NewCardMessages();
        return singleInstance;
    }


    public void printer(String string){
        System.out.println(string);
    }

}
