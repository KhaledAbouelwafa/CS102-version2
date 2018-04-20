/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.core;

import Sensors.util.AvgTempComparator;
import Sensors.util.Date;
import Sensors.Interfaces.City;
import Sensors.Interfaces.DataAnalytics;
import Sensors.Interfaces.IHumidity;
import Sensors.Interfaces.IPressure;
import Sensors.Interfaces.ITempreture;
import Sensors.Interfaces.Measurments;
import Sensors.Interfaces.Sensor;
import Sensors.util.Filter;
import Sensors.util.SensorComparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DataAnalyticsImpl implements DataAnalytics {

    private final Filter filter;
    private static DataAnalyticsImpl instance;
    ArrayList<City> cities;
    private boolean fileFound = true;
    
    private DataAnalyticsImpl(String fileName) throws IOException {
        filter = new Filter();
        fileFound = filter.readFromFile(fileName);
        cities = filter.cities();
    }

    public static DataAnalyticsImpl getInstance(String fileName) throws IOException {
        instance = new DataAnalyticsImpl(fileName);
        return instance;
    }

    public boolean FileFound(){
        return fileFound;
    }
    
    @Override
    public Map<City, Sensor> hottestTemperature(Date d1, Date d2) {

        Map<City, Sensor> maxTempMap = new HashMap<>();

        for (int i = 0; i < cities.size(); i++) {
            ArrayList<Temperature> cityTemperatures = filter.CityTemperatures(cities.get(i).getName(), d1, d2);
            Temperature maxTemp = Collections.max(cityTemperatures, new SensorComparator());
            maxTempMap.put(cities.get(i), maxTemp);
        }
        //System.out.println(maxTempMap);
        return maxTempMap;
    }

    @Override
    public Measurments averageMeasurements(City city, Date d1, Date d2) {

        ArrayList<Measurments> cityMeasurments = filter.CityMeasurmentses(city.getName().toUpperCase(), d1, d2);

        Measurments m = new MeasurmentsImpl();

        double sumTemp = 0.0;
        for (int i = 0; i < cityMeasurments.size(); i++) {
            sumTemp += cityMeasurments.get(i).getTemperatureSensor().getValue();
        }
        double sumHum = 0.0;
        for (int i = 0; i < cityMeasurments.size(); i++) {
            sumHum += cityMeasurments.get(i).getHumiditySensor().getValue();
        }
        double sumPre = 0.0;
        for (int i = 0; i < cityMeasurments.size(); i++) {
            sumPre += cityMeasurments.get(i).getPressureSensor().getValue();
        }
        double avgTemp = (sumTemp / cityMeasurments.size());
        double avgHum = (sumHum / cityMeasurments.size());
        double avgPre = (sumPre / cityMeasurments.size());
        //System.out.println(""+avgTemp+" "+avgHum+" "+avgPre);
        m.setCity(city);
        ITempreture temp = new Temperature();
        temp.setValue(avgTemp);
        temp.setUnit("C");
        m.getSensors().put("Temperature", temp);
        IHumidity hum = new Humidity();
        hum.setValue(avgHum);
        hum.setUnit("p");
        m.getSensors().put("Humidity", hum);
        IPressure pre = new Pressure();
        pre.setValue(avgPre);
        pre.setUnit("mb");
        m.getSensors().put("Pressure", pre);
        return m;
    }

    @Override
    public Set<City> citiesByTemperature(Date d1, Date d2) {

        Map<City, ITempreture> avgTempMap = new HashMap<>();

        for (int i = 0; i < cities.size(); i++) {
            Measurments m = averageMeasurements(cities.get(i), d1, d2);
            ITempreture temp = m.getTemperatureSensor();
            avgTempMap.put(cities.get(i), temp);
        }
        //System.out.println(avgTempMap);
        //sorting cities by avg and transfer the keys to a set
        TreeMap<City, ITempreture> sorted = new TreeMap<>(new AvgTempComparator(avgTempMap));
        sorted.putAll(avgTempMap);
        //System.out.println(sorted);
        Set<City> sortedCities = sorted.keySet();
        //System.out.println(sortedCities);
        return sortedCities;
    }

    @Override
    public ArrayList<Integer> alert(City city, Date d1, Date d2) {

        ArrayList<Measurments> cityMeasurments = filter.CityMeasurmentses(city.getName().toUpperCase(), d1, d2);

        int dCounter = 0;
        int tCounter = 0;
        int pCounter = 0;
        int hCounter = 0;

        for (int i = 0; i < cityMeasurments.size(); i++) {
            double temp = cityMeasurments.get(i).getTemperatureSensor().getValue();
            if (temp > 45) {
                tCounter++;
            }
        }

        for (int i = 0; i < cityMeasurments.size(); i++) {
            double hum = cityMeasurments.get(i).getHumiditySensor().getValue();
            if (hum > 35) {
                hCounter++;
            }
        }

        for (int i = 0; i < cityMeasurments.size(); i++) {
            double pre = cityMeasurments.get(i).getPressureSensor().getValue();
            if (pre > 2050 || pre < 1010) {
                pCounter++;
            }
        }

        for (int i = 0; i < cityMeasurments.size(); i++) {
            double d = cityMeasurments.get(i).getDistanceSensor().getValue();
            if (d < 21) {
                dCounter++;
            }
        }

        ArrayList<Integer> alerts = new ArrayList<>();
        alerts.add(dCounter);
        alerts.add(tCounter);
        alerts.add(pCounter);
        alerts.add(hCounter);
        //System.out.println(alerts);

        return alerts;
    }
}
