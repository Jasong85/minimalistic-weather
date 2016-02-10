// GetWeather.java	Dorothy Carter
// This class instantiates an object that stores an URL and the content located at that URL
// The URL always points to the REST service of the NDFD

import java.net.*;
import java.io.*;

public class GetWeather {
	protected URL weather;
	protected String content;
	
	public GetWeather(String ZIP, String begin, String end, String units) {
		try {
			buildURL(ZIP, begin, end, units);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		grabData();
	}
	
	protected void grabData() {
		try {
			HttpURLConnection cnx = (HttpURLConnection) weather.openConnection();
			cnx.connect();
			// if the connection is ok, read the content from it
			if (cnx.getResponseCode() == 200) {
				BufferedReader io = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
				
				String temp = "";
				while (temp != null) {
					content += temp + "\n";
					temp = io.readLine();			
				}
				
				io.close();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void buildURL(String ZIP, String begin, String end, String unit) throws MalformedURLException {
		String base = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php?";
		base += "zipCodeList=" + ZIP;
		base += "&product=time-series";
		base += "&begin=" + begin;
		base += "&end=" + end;
		base += "&unit=" + unit;
		
		base += "&maxt=maxt"; // max temp
		base += "&mint=mint"; // minimum temp
		base += "&appt=appt"; // apparent temperature
		base += "&pop12=pop12"; // 12-hr probablility of rain
		base += "&sky=sky"; // cloud cover
		base += "&wspd=wspd"; // wind speed
		base += "&wdir=wdir"; // wind direction
		base += "&rh=rh"; // relative humidity
		
		weather = new URL(base);
	}
	
	public URL getURL() {
		return weather;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setURL(String ZIP, String begin, String end, String unit) throws MalformedURLException {
		buildURL(ZIP, begin, end, unit);
	}
	
	public void setContent() {
		grabData();
	}
	
	public String toString() {
		return weather + "\n" + content;
	}
	
	// this is a method to build a timestamp that follows the doc's specifications
	// it may or may not be useful
	public static String buildTimestamp(int year, int month, int day, int hour, int minute) {
		String temp = year + "-";
		if (month < 10)
			temp += "0" + month;
		else
			temp += month;
		
		if (day < 10)
			temp += "0" + day;
		else
			temp += day;
		
		temp += "T";
		
		if (hour < 10)
			temp += "0" + hour;
		else
			temp += hour;
		
		temp += ":00";
		
		return temp;
	}
}