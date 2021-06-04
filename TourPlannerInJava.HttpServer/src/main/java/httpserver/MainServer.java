package httpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainServer {
    public static void main(String[] args){
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost",8085),20);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.createContext("/test", MainServer::handleRequest);
        server.createContext("/pics/", MainServer::getImage);
        server.createContext("/style/meinCSS", MainServer::getStyleSheet);
        server.createContext("/tours/", MainServer::getTour);
        server.createContext("/favicon.ico",MainServer::getFavIcon);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
    private static void handleRequest(HttpExchange exchange) throws IOException {
        String response = new MainPage().create();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        //exchange.setAttribute("content-type","html");
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private static void getTour(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        System.out.println(uri.substring(uri.lastIndexOf("/")+1));
        int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/")+1));
        String response = new TourPage(id).create();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        //exchange.setAttribute("content-type","html");
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private static void getImage(HttpExchange exchange) throws IOException{
//        System.out.println("test");
        Headers headers = exchange.getResponseHeaders();
        headers.add("Content-Type", "image/jpg");

        String url = exchange.getRequestURI().toString();
        String image = url.substring(url.lastIndexOf("/")+1);

//        System.out.println(image);
        if(image.contains("..")) return;
        File file = new File ("pics/" +image);
        byte[] bytes  = new byte [(int)file.length()];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.read(bytes, 0, bytes.length);

        exchange.sendResponseHeaders(200, file.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.close();
    }

    private static void getFavIcon(HttpExchange exchange) throws IOException {
        System.out.println("get fav icon");
        Headers headers = exchange.getResponseHeaders();
        headers.add("Content-Type", "image/png");
        File file = null;
        try {
            file = new File(MainServer.class.getClassLoader().getResource("icons/icon.png").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        byte[] bytes  = new byte [(int)file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(bytes, 0, bytes.length);

        exchange.sendResponseHeaders(200, file.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.close();
    }

    private static void getStyleSheet(HttpExchange exchange){
        try{
//            File myObj = new File("css/meinCSS.css");
            File myObj = new File(MainServer.class.getClassLoader().getResource("css/meinCSS.css").toURI());
            Scanner myReader = new Scanner(myObj);
            StringBuilder content = new StringBuilder();
            while (myReader.hasNextLine()) {
                content.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
            String response = content.toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
            //exchange.setAttribute("content-type","html");
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }
}
