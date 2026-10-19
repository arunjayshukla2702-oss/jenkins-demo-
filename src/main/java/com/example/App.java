package com.example;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) throws Exception {
        String version = System.getenv().getOrDefault("APP_VERSION", "dev");

        System.out.println("App v2 raha hai");
        System.out.println("2 + 3 = " + add(2, 3));
        System.out.println("5 - 2 = " + subtract(5, 2));

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            String msg = "Hello from Jenkins CD! Build: " + version
                    + " | 2+3=" + add(2, 3)
                    + " | 5-2=" + subtract(5, 2) + "\n";
            byte[] body = msg.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();
        System.out.println("Server started on port 8080");
    }
}
