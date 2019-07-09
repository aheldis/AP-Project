//package controller;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationConfig;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.google.gson.Gson;
//import controller.server.Server;
//import controller.server.SocketClass;
//import kong.unirest.HttpResponse;
//import kong.unirest.JsonNode;
//import kong.unirest.Unirest;
//import kong.unirest.UnirestException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class DBClass {
//    private static DBClass ourInstance = new DBClass();
//    private static final String baseAddress = "http://127.0.0.1:8080/";
//
//    public static DBClass getInstance() {
//        return ourInstance;
//    }
//
//    private DBClass() {
//    }
//
//    public static void makeDB(){
//        HashMap<String, Object> parameters = new HashMap<>();
//        parameters.put("name","myBD");
//        try {
//            Unirest.post(baseAddress + "init_DB").fields(parameters);
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void putSocketClass(){
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "myDB");
//        hashMap.put("key", "socketClasses");
//        hashMap.put("value", Server.getSockets());
//        try {
//            Unirest.post(baseAddress + "put").fields(hashMap);
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public  static void addToSocketClass(){//after updating
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("name","myDB");
//        hashMap.put("key","socketClasses");
//        try {
//            Unirest.post(baseAddress+ "del_from_DB");
//        }catch (UnirestException e){
//            e.printStackTrace();
//        }
//        putSocketClass();
//    }
//
//    public static ArrayList<SocketClass> getSocketClass(){
//        HttpResponse<JsonNode> response= null;
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "myDB");
//        hashMap.put("key", "socketClasses");
//        ArrayList<SocketClass> socketClasses;
//        try {
//
//            response  = Unirest.post(baseAddress + "get").fields(hashMap).asJson();
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            String json= objectMapper.writeValueAsString(response);
//            Gson gson = new Gson();
//            socketClasses = gson.fromJson(json,ArrayList.class);
//        } catch (UnirestException | JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void putCustomCard(Gson gson,String customName){
//        putObject(gson,customName);
//    }
//
//    public static String getCustomCard(String customName){
//       return getObject(customName);
//    }
//
//    public static void putObject(Object object,String key){
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "myDB");
//        hashMap.put("key", key);
//        hashMap.put("value", object);
//        try {
//            Unirest.post(baseAddress + "put").fields(hashMap);
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getObject(String key){
//        HttpResponse<JsonNode> response= null;
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "myDB");
//        hashMap.put("key", key);
//        try {
//
//            response  = Unirest.post(baseAddress + "get").fields(hashMap).asJson();
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            return objectMapper.writeValueAsString(response);
//        } catch (UnirestException | JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return "null";
//    }
//
//
//
//
//}
