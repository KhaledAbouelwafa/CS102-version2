/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.util;

import Sensors.Interfaces.City;
import Sensors.Interfaces.Measurments;
import Sensors.Interfaces.Sensor;
import Sensors.core.CityFactory;

import java.util.Map;
import java.util.Scanner;
import Sensors.core.CityImpl;
import Sensors.core.Distance;
import Sensors.core.Humidity;
import Sensors.core.MeasurmentsImpl;
import Sensors.core.Pressure;
import Sensors.core.Temperature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Filter {

    ArrayList<Measurments> measurements;
    CityFactory cf = (CityFactory) CityFactory.getInstance();

    public boolean readFromFile(String fileName) throws IOException {
        measurements = new ArrayList<>();
        try {
            Scanner In = new Scanner(new File(fileName));
            while (In.hasNextLine()) {
                String s = In.nextLine();
                String[] linearray = s.split(";");
                String cityName = linearray[0].toUpperCase();
                CityImpl city = cf.getCity(cityName);

                Map<String, Sensor> sensor = new HashMap<>();
                sensor.put("Temperature", new Temperature(Double.parseDouble(linearray[3]), linearray[4]));
                sensor.put("Humidity", new Humidity(Double.parseDouble(linearray[5]), linearray[6]));
                sensor.put("Pressure", new Pressure(Double.parseDouble(linearray[7]), linearray[8]));
                sensor.put("Distance", new Distance(Double.parseDouble(linearray[9]), linearray[10]));

                String strDate = linearray[11];
                String[] dateArray = strDate.split("/");
                Date date = new Date(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
                measurements.add(new MeasurmentsImpl(city, sensor, date));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex + "\n");
            return false;
        }
        return true;
    }

    //return all cities in the file
    public ArrayList<City> cities() {
        ArrayList<City> cities;
        Set<City> CitySet = new TreeSet<>();

        for (int i = 0; i < measurements.size(); i++) {
            CitySet.add(measurements.get(i).getCity());
        }

        cities = new ArrayList(CitySet);
        return cities;
    }

    //return given city between two dates
    public ArrayList<Measurments> CityMeasurmentses(String cityName, Date d1, Date d2) {
        ArrayList<Measurments> measurmentsOfCity = new ArrayList<>();
        if (d2.compareTo(d1) > 0) {
            Date temp = d2;
            d2 = d1;
            d1 = temp;
        }

        for (int i = 0; i < measurements.size(); i++) {
            if (measurements.get(i).getDate().compareTo(d2) > -1 && measurements.get(i).getDate().compareTo(d1) < 0) {
                if (measurements.get(i).getCity().getName().equalsIgnoreCase(cityName)) {
                    measurmentsOfCity.add(measurements.get(i));
                }
            }
        }
        return measurmentsOfCity;
    }

    public ArrayList<Temperature> CityTemperatures(String cityName, Date d1, Date d2) {
        ArrayList<Temperature> cityTemperatures = new ArrayList<>();
        if (d2.compareTo(d1) > 0) {
            Date temp = d2;
            d2 = d1;
            d1 = temp;
        }

        for (int i = 0; i < measurements.size(); i++) {
            if (measurements.get(i).getDate().compareTo(d2) > 0 && measurements.get(i).getDate().compareTo(d1) < 0) {
                if (measurements.get(i).getCity().getName().equalsIgnoreCase(cityName)) {
                    cityTemperatures.add(measurements.get(i).getTemperatureSensor());
                }
            }

        }
        return cityTemperatures;

    }

    public ArrayList<Distance> CityDistance(String cityName, Date d1, Date d2) {
        ArrayList<Distance> cityDistance = new ArrayList<>();
        if (d2.compareTo(d1) > 0) {
            Date temp = d2;
            d2 = d1;
            d1 = temp;
        }

        for (int i = 0; i < measurements.size(); i++) {
            if (measurements.get(i).getDate().compareTo(d2) > 0 && measurements.get(i).getDate().compareTo(d1) < 0) {
                if (measurements.get(i).getCity().getName().equalsIgnoreCase(cityName)) {
                    cityDistance.add(measurements.get(i).getDistanceSensor());
                }
            }

        }
        return cityDistance;

    }

    public ArrayList<Humidity> CityHumidity(String cityName, Date d1, Date d2) {
        ArrayList<Humidity> cityHumidity = new ArrayList<>();
        if (d2.compareTo(d1) > 0) {
            Date temp = d2;
            d2 = d1;
            d1 = temp;
        }

        for (int i = 0; i < measurements.size(); i++) {
            if (measurements.get(i).getDate().compareTo(d2) > 0 && measurements.get(i).getDate().compareTo(d1) <0) {
                if (measurements.get(i).getCity().getName().equalsIgnoreCase(cityName)) {
                    cityHumidity.add(measurements.get(i).getHumiditySensor());
                }
            }

        }
        return cityHumidity;

    }

    public ArrayList<Pressure> CityPressure(String cityName, Date d1, Date d2) {
        ArrayList<Pressure> cityPressure = new ArrayList<>();
        if (d2.compareTo(d1) > 0) {
            Date temp = d2;
            d2 = d1;
            d1 = temp;
        }

        for (int i = 0; i < measurements.size(); i++) {
            if (measurements.get(i).getDate().compareTo(d2) > 0 && measurements.get(i).getDate().compareTo(d1) < 0) {
                if (measurements.get(i).getCity().getName().equalsIgnoreCase(cityName)) {
                    cityPressure.add(measurements.get(i).getPressureSensor());
                }
            }

        }
        return cityPressure;

    }

}
