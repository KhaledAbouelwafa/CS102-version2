package Sensors.Interfaces;

import com.sun.org.apache.xerces.internal.util.URI;
import java.io.IOException;

public interface ICityFactory{
    
    public abstract City getCity(String cityName)throws IOException,URI.MalformedURIException;
    
}
