package ua.org.ua2012.weather;

import java.util.List;

public class WeatherObservationList{
   	private List<WeatherObservations> weatherObservations;

 	public List<WeatherObservations> getWeatherObservations(){
		return this.weatherObservations;
	}
	public void setWeatherObservation(List<WeatherObservations> weatherObservations){
		this.weatherObservations = weatherObservations;
	}
}
