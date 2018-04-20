/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.core;

import Sensors.Interfaces.City;
import Sensors.Interfaces.Measurments;
import Sensors.Interfaces.Sensor;
import Sensors.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TestDemo {

    public static void main(String[] args) throws IOException {

        String fileName = "";
        int count = 0;
        boolean check = true;
        boolean fileFound = true;
        while (check) {
            Scanner in = new Scanner(System.in);
            if (count == 0) {
                System.out.println("-------Please enter file extension---------\n");
                fileName = in.nextLine();
                fileFound = read(fileName, in);
                if (fileFound == false) {
                    continue;
                }
            } else {
                System.out.println("-------Do you want to change the file? y/n---------\n");
                String yn = in.nextLine();
                yn = yn.toLowerCase();
                switch (yn) {
                    case "y": {
                        System.out.println("-------Please enter file extension---------\n");
                        fileName = in.nextLine();
                        fileFound = read(fileName, in);
                        if (fileFound == false) {
                            continue;
                        }
                        break;
                    }
                    case "n": {
                        check = read(fileName, in);
                        break;
                    }
                    default: {
                        System.out.println("Wrong input please enter y or n\n");
                    }
                }
            }
            count++;
        }

    }

    public static boolean read(String fileName, Scanner in) throws IOException {

        CityFactory cf = (CityFactory) CityFactory.getInstance();
        DataAnalyticsImpl dataAnalytics = DataAnalyticsImpl.getInstance(fileName);
        if (dataAnalytics.FileFound()) {
            System.out.println("\n------- Choose from the following methods ---------\n"
                    + "1. hottestTemperature(Date d1, Date d2)\n"
                    + "2. averageMeasurements(City city, Date d1, Date d2) \n"
                    + "3. citiesByTemperature(Date d1, Date d2)\n"
                    + "4. alert(City city, Date d1, Date d2) \n"
                    + "Enter 0 to end the program\n");
            int input = in.nextInt();
            switch (input) {
                case 1:
                    System.out.println("Enter the dates as Day/Month/Year");
                    System.out.println("Enter the 1st date");
                    int day1A = in.nextInt();
                    int month1A = in.nextInt();
                    int year1A = in.nextInt();
                    Date d1A = new Date(day1A, month1A, year1A);
                    System.out.println("Enter the 2nd date");
                    int day2A = in.nextInt();
                    int month2A = in.nextInt();
                    int year2A = in.nextInt();
                    Date d2A = new Date(day2A, month2A, year2A);
                    Map<City, Sensor> hottestMap = dataAnalytics.hottestTemperature(d1A, d2A);
                    Iterator<City> it1 = hottestMap.keySet().iterator();
                    System.out.printf("\nHOTTEST TEMPERATURE DURING %s AND %s\n", d1A, d2A);
                    while (it1.hasNext()) {
                        City city = it1.next();
                        Sensor max = hottestMap.get(city);
                        System.out.printf("%-10s->\t%.2f\n", city.getName(), max.getValue());
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter dates as day/month/year");
                    System.out.println("Enter the 1st date");
                    int day1B = in.nextInt();
                    int month1B = in.nextInt();
                    int year1B = in.nextInt();
                    Date d1B = new Date(day1B, month1B, year1B);
                    System.out.println("Enter the 2nd date");
                    int day2B = in.nextInt();
                    int month2B = in.nextInt();
                    int year2B = in.nextInt();
                    Date d2B = new Date(day2B, month2B, year2B);
                    System.out.println("Enter city name");
                    String name1 = in.next().toUpperCase();
                    City city1;
                    while(!cf.CityFound(name1)){
                        System.out.println("City not in the file. Please type again");
                        name1 = in.next().toUpperCase();
                    }
                    city1 = cf.getCity(name1);
                    Measurments avgM = dataAnalytics.averageMeasurements(city1, d1B, d2B);
                    System.out.printf("\nAVERAGE VALUES OF CITY %s DURING %s AND %s\n", name1, d1B, d2B);
                    System.out.printf("%s -> %s -> %s -> %s\n\n",
                            name1, avgM.getSensors().get("Temperature"), avgM.getSensors().get("Humidity"), avgM.getSensors().get("Pressure"));
                    break;
                case 3:
                    System.out.println("Enter dates as day/month/year");
                    System.out.println("Enter the 1st date");
                    int day1C = in.nextInt();
                    int month1C = in.nextInt();
                    int year1C = in.nextInt();
                    Date d1C = new Date(day1C, month1C, year1C);
                    System.out.println("Enter the 2nd date");
                    int day2C = in.nextInt();
                    int month2C = in.nextInt();
                    int year2C = in.nextInt();
                    Date d2C = new Date(day2C, month2C, year2C);
                    Set<City> cities = dataAnalytics.citiesByTemperature(d1C, d2C);
                    Iterator<City> it2 = cities.iterator();
                    System.out.printf("\nSET OF CITIES ORGANIZED BY THEIR INCREASING ORDER OF AVG TEMPERATURE\n");
                    String output = "";
                    while (it2.hasNext()) {
                        City city = it2.next();
                        String name = city.getName();

                        output += name + " -> ";
                    }
                    output = output.substring(0, output.lastIndexOf("->"));
                    System.out.println(output + "\n");
                    break;
                case 4:
                    System.out.println("Enter dates as day/month/year");
                    System.out.println("Enter the 1st date");
                    int day1D = in.nextInt();
                    int month1D = in.nextInt();
                    int year1D = in.nextInt();
                    Date d1D = new Date(day1D, month1D, year1D);
                    System.out.println("Enter the 2nd date");
                    int day2D = in.nextInt();
                    int month2D = in.nextInt();
                    int year2D = in.nextInt();
                    Date d2D = new Date(day2D, month2D, year2D);
                    System.out.println("Enter city name");
                    String name2 = in.next().toUpperCase();
                    City city2;
                    while(!cf.CityFound(name2)){
                        System.out.println("City not in the file. Please type again");
                        name2 = in.next().toUpperCase();
                    }
                    city2 = cf.getCity(name2);
                    ArrayList<Integer> alerts = dataAnalytics.alert(city2, d1D, d2D);
                    System.out.printf("\nALERTS OF CITY %s BETWEEN %s AND %s\n", name2, d1D, d2D);
                    System.out.printf("Distance Alert: %d\n"
                            + "Temperature Alert: %d\n"
                            + "Pressure Alert: %d\n"
                            + "Humidity Alert: %d\n\n", alerts.get(0), alerts.get(1), alerts.get(2), alerts.get(3));
                    break;
                case 0:
                    return false;
                default:
                    System.out.println("Your Input is wrong. try again");
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
}
