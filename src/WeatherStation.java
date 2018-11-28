
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class WeatherStation {
    public static void main(String[] args) {
        StringBuffer response = new StringBuffer();
        Scanner text = new Scanner(System.in);

        boolean koniec = false;

        do {
            System.out.println("Podaj miasto");

            String miasto = text.nextLine();
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + miasto + "&units=metric&APPID=84f311e61a853d7c6881876cd312d669";

            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                //  System.out.println("Response: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (MalformedURLException ex) {
                System.out.println("bad url");
            } catch (IOException ex) {
                System.out.println("Connection failed");
            }
            // System.out.println(response);

            Gson gson = new Gson();

            Map m = gson.fromJson(response.toString(), Map.class);
            Map main = gson.fromJson(m.get("main").toString(), Map.class);

//            System.out.println(m.get("main"));
//            System.out.println(main.get("temp"));
//            System.out.println(main.get("pressure"));
//            System.out.println(main.get("humidity"));
//            System.out.println(main.get("temp_min"));
//            System.out.println(main.get("temp_max"));

            Weather pogoda = new Weather((double)main.get("temp"),(double)main.get("pressure"),(double)main.get("humidity"),(double)main.get("temp_min"),(double)main.get("temp_max") );
            System.out.println(pogoda.toString());


            System.out.println("Wyjsc? T/n");
            String wyjsc = text.nextLine();
            if (wyjsc.equals("T")) {
                koniec = true;
            }
            response = new StringBuffer();

        } while (!koniec);


    }

}