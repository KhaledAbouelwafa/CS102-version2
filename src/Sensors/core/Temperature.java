/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.core;

import Sensors.Interfaces.ITempreture;
import Sensors.Interfaces.Sensor;

/**
 *
 * @author khaled helal
 */
public class Temperature implements ITempreture {

    private double value;
    private String unit;
    
    public Temperature(double value, String unit) {
        setValue(value);
        setUnit(unit);
    }

    public Temperature() {
    }
    
    @Override
    public void setUnit(String unit) {
        if (unit.equalsIgnoreCase("C") || unit.equalsIgnoreCase("K")) {
            this.unit = unit;
        } else {
            throw new IllegalArgumentException("wrong unit");
        }
    }

    @Override
    public void setValue(double value) {
        this.value=value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getUnit() {
        return unit;
    }
    
    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }

    @Override
    public int compareTo(Sensor t) {
        if(this.getValue()>t.getValue())
            return 1;
        else if(this.getValue()<t.getValue())
            return -1;
        return 0;
    }
    
}
