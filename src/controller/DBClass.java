package controller;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import java.util.HashMap;

public class DBClass {
    private static DBClass ourInstance = new DBClass();
    private static final String baseAddress = "http://127.0.0.1:8000/";

    public static DBClass getInstance() {
        return ourInstance;
    }

    private DBClass() {
    }

    public static HttpResponse<String> main(HashMap<String,Object> parameters,final String orderPath){
        
        //final String path = "get_all_keys";
        HttpResponse<String> response = null;
//        HashMap<String, Object> parameters = new HashMap<>();
//        parameters.put("name", "DB_name");
//        parameters.put("key", "key for adding to a DB or deleting from it");
//        parameters.put("value", "your content to save");
        try {
            response = Unirest.post(baseAddress + orderPath).fields(parameters).asString();
        } catch (UnirestException e) {
            e.printStackTrace();    //do something
        }

        return response;
    }

}
