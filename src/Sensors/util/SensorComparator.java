/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.util;

import Sensors.Interfaces.Sensor;
import Sensors.core.Temperature;
import java.util.Comparator;

/**
 *
 * @author belal
 */
public class SensorComparator implements Comparator<Sensor>{

    @Override
    public int compare(Sensor t, Sensor t1) {
     if(t.getValue()>t1.getValue()){
        return 1;
    }else if(t.getValue()<t1.getValue()){
        return -1;
    }else return 0;
    }
    
}
