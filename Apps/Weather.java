package Apps;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.Collections;
import java.text.SimpleDateFormat;

public class Weather extends App{

    public String m_locationString;
    public String m_location;
    public int m_MaxTemp;
    public int m_MinTemp;
    public Date m_timeStamp;
    public Vector<Integer> m_rainChance;
    public Vector<Integer> m_Temperature;
    public Vector<Integer> m_FeelsLikeTemperature;
    public String m_Sunrise;
    public String m_Sunset;
    public Vector<Integer> m_WindSpeed;
    public Vector<String> m_WindDirection;
    public Vector<String> m_WeatherString;
    public Vector<Date> m_WeatherTime;

    public Weather( String location, int x, int y, int w, int h ){
	m_location = location;
	m_locationString = "";
	m_rainChance = new Vector<Integer>();
	m_Temperature = new Vector<Integer>();
	m_FeelsLikeTemperature = new Vector<Integer>();
	m_WindSpeed = new Vector<Integer>();
	m_WindDirection = new Vector<String>();
	m_WeatherString = new Vector<String>();
	m_WeatherTime = new Vector<Date>();
	m_MaxTemp = 0;
	m_MinTemp = 0;
	m_Sunrise = "0";
	m_Sunset = "0";
	m_top = y;
	m_left = x;
	m_width = w;
	m_height = h;
	m_name = "WeatherApp";
    }

    private int GetTimeOfDay( Date d ){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(d);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	for ( int i = 1; i < m_WeatherTime.size(); i++ ){
	    Calendar thisTime = GregorianCalendar.getInstance();
	    thisTime.setTime(m_WeatherTime.elementAt(i));
	    Calendar lastTime = GregorianCalendar.getInstance();
	    lastTime.setTime(m_WeatherTime.elementAt(i-1));
	    if ( hour > lastTime.get(Calendar.HOUR_OF_DAY) && hour <= thisTime.get(Calendar.HOUR_OF_DAY) ){
		return i-1;
	    }
	}
	return m_WeatherTime.size() - 1;
    }

    public void Draw(){
	final String DEGREE  = "\u00b0";
        final int timeInt = GetTimeOfDay( m_timeStamp );
        m_frame.DrawText( m_locationString, 20, 20 );
        //m_frame.DrawText( "Refreshed at : " + m_timeStamp, 20, 40 );
	m_frame.DrawText( GetWeatherSymbol( m_WeatherString.elementAt( timeInt ) ) + " " +
			  String.valueOf(m_Temperature.elementAt( timeInt )) + DEGREE + "C", 20, 60, 40 );
        m_frame.DrawText( "▲ " + String.valueOf( m_MaxTemp ) + DEGREE + "C  ▼ " + String.valueOf( m_MinTemp ) + DEGREE + "C", 20, 80 );
        m_frame.DrawText( "☂ " + String.valueOf( Collections.max( m_rainChance.subList(timeInt,m_rainChance.size()-1) ) ) +  " % ", 20, 120 );
        m_frame.DrawText( GetWindSymbol( m_WindDirection.elementAt( timeInt ).trim() ) + " " +
			  String.valueOf( m_WindSpeed.elementAt( timeInt ) ) + " mph " +  m_WindDirection.elementAt( timeInt ), 20, 100 );
    }

    private String GetWeatherSymbol( String weatherStr ){
	
	if ( weatherStr.contains("Rain") ||
	     weatherStr.contains("rain") ||
	     weatherStr.contains("Drizzle") ||
	     weatherStr.contains("drizzle") ||
	     weatherStr.contains("Showers") ||
	     weatherStr.contains("showers") ) return "☂";
	else if ( weatherStr.contains("Cloud") ||
		  weatherStr.contains("cloud") ||
		  weatherStr.contains("Gust") ||
		  weatherStr.contains("gust") ||
		  weatherStr.contains("Wind") ||
		  weatherStr.contains("wind") ) return "☁";
	else if ( weatherStr.contains("Snow") ||
		  weatherStr.contains("snow") ) return "☃";
	else return "☀";
    }

    private String GetWindSymbol( String WindDir ){

	if ( WindDir.contains("NW") ) return "↘";
	else if ( WindDir.contains("SW") ) return "↗";
	else if ( WindDir.contains("SE") ) return "↖";
	else if ( WindDir.contains("NE") ) return "↙";
	else if ( WindDir.contains("N") ) return "↓";
	else if ( WindDir.contains("W") ) return "→";
	else if ( WindDir.contains("S") ) return "↑";
	else if ( WindDir.contains("E") ) return "←";

	else return "?";
    }
	

   
    public void Update() throws Exception {
	m_timeStamp = new Date();
	String url = 
	    "http://www.worldweatheronline.com/v2/weather.aspx?q=" 
	    + 
	    m_location.trim() + "&day=0";
	URL websiteURL = new URL( url );
	BufferedReader in = new BufferedReader(
	   new InputStreamReader(websiteURL.openStream()));
	String inputLine;

	m_rainChance.clear();
	m_Temperature.clear();
	m_FeelsLikeTemperature.clear();
	m_WindSpeed.clear();
	m_WindDirection.clear();
	m_WeatherTime.clear();
	m_MaxTemp = 0;
	m_MinTemp = 0;
	m_Sunrise = "";
	m_Sunset = "";
	m_WeatherString.clear();
	m_locationString = "";
	int nEntries = 8;
	
	int dayCounter = 0;
        while ((inputLine = in.readLine()) != null){
	    
	    // Only care about today so only consider the first day
	    if ( inputLine.contains("<div class=\"forecast-block\">") ){
		dayCounter ++;
		if ( dayCounter == 1 ){
		    m_MinTemp = Integer.parseInt((inputLine.split("Min: ")[1]).split("&deg")[0].trim());
		    m_MaxTemp = Integer.parseInt((inputLine.split("Max: ")[1]).split("&deg")[0].trim());
		    m_Sunrise = (inputLine.split("Sunrise: ")[1]).split("<")[0];
		    m_Sunset = (inputLine.split("Sunset: ")[1]).split("<")[0];
		}
	    }
	    if ( dayCounter > 1 ) break;
	    //System.out.println( inputLine );
	    
	    if ( inputLine.contains("Weather in ") ){
		m_locationString = inputLine.split("Weather in ")[1].split("\\|")[0].trim();
	    }
	    
	    // Get the % chance of rain for Morning, Afternoon, Evening & Night
	    if ( inputLine.contains("Rain?") ){
		String chanceString = (inputLine.split("Rain\\?")[1]).split("</tr>")[0].split("</th>")[1];
		for ( int i = 0; i < nEntries ; i++ ){
		    String thisChance = (chanceString.split("<td>")[i+1]).split("</td>")[0];
		    m_rainChance.addElement( Integer.parseInt(thisChance.replace("%","").trim()) );
		}
	    }
	    
	    // Get the temperature
	    if ( inputLine.contains("Temp" ) ) {
		String tempString = (inputLine.split("Temp")[1]).split("</th>")[1];
		for ( int i = 0; i < nEntries ; i++ ){
		    String thisTemp = (tempString.split("\">")[i+1]).split("&deg")[0].trim();
		    m_Temperature.addElement( Integer.parseInt( thisTemp ) );
		}
	    }		       
	    
	    // Get the "Feels like" temperature
	    if ( inputLine.contains("Feels Like") ) {
		String tempString = (inputLine.split("Feels Like")[1]).split("</th>")[1];
                for ( int i = 0; i < nEntries ; i++ ){
                    String thisTemp = (tempString.split("\">")[i+1]).split("&deg")[0].trim();
		    m_FeelsLikeTemperature.addElement( Integer.parseInt(thisTemp) );
		}
            }
	    
	    // Get the wind speed
	    if ( inputLine.contains("Wind") ) {
                String tempString = (inputLine.split("Wind")[1]).split("</th>")[1];
		for ( int i = 0; i < nEntries ; i++ ){
                    String thisWindSpeed = (tempString.split("\">")[i+1]).split("mph")[0].trim();
                    m_WindSpeed.addElement( Integer.parseInt(thisWindSpeed) );
		}
	    }
	    
	    // Get the wind direction
            if ( inputLine.contains("Dir") ) {
                String tempString = (inputLine.split("Dir")[1]).split("</th>")[1];
                for ( int i = 0; i < nEntries ; i++ ){
		    String thisWindDirection = ((tempString.split("<td>")[i+1])).split("<img")[0];
		    m_WindDirection.addElement( thisWindDirection );
		}
	    }

	    // Get the weather strings
	    if ( inputLine.contains( "<th>Weather</th>" ) ){
		String tempString = inputLine.split("<th>Weather</th>")[1];
		for ( int i = 0; i < nEntries; i++ ){
		    String thisString = (tempString.split("alt=\"")[1]).split("\"")[0];
		    m_WeatherString.addElement( thisString );
		}
	    }
	    
	    // Get the times of the weather entries
	    if ( inputLine.contains( "00:00" ) ){
		for ( int i = 0; i < nEntries; i++ ){
		    String thisTime = ( inputLine.split("</td>")[i] ).split(">")[1];
		    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		    Date thisDate = format.parse(thisTime);
		    m_WeatherTime.addElement( thisDate );
		}
	    }
	}
	in.close();	    
	if ( Collections.min( m_Temperature ) < m_MinTemp ) m_MinTemp = Collections.min( m_Temperature );
	if ( Collections.max( m_Temperature ) < m_MaxTemp ) m_MaxTemp = Collections.max( m_Temperature );
    }
    

} 
