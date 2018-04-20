package Sensors.core;

import Sensors.Interfaces.ICityFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.databind.*;

public class CityFactory implements ICityFactory {

    private static ICityFactory instance = new CityFactory();
    private Map<String, CityImpl> cities = new HashMap<>();

    private CityFactory() {

    }

    public static ICityFactory getInstance() {
        return instance;
    }

    public boolean CityFound(String cityName){
        return cities.containsKey(cityName);
    }
    
    @Override
    public CityImpl getCity(String cityName) throws IOException, MalformedURLException {
        double lng = 0;
        double lat = 0;
        if (cities.containsKey(cityName)) {
            return cities.get(cityName);
        } else {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName + "&key=AIzaSyCLefuA10WTMg7yE36Tv7uLXrmqIDRuurA";
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            Scanner in = new Scanner(connection.getInputStream());
            String n = "";

            while (in.hasNext()) {
                n += in.nextLine();
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(n).path("results").path(0);

            String formattedAddress = root.path("formattedAddress").asText();
            lng = root.path("geometry").path("location").path("lng").asDouble();
            lat = root.path("geometry").path("location").path("lat").asDouble();

        }
        CityImpl city= new CityImpl(cityName, new GPSImpl(lat, lng));
        cities.put(cityName, city);
        return city;
    }

}
