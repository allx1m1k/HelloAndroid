package ua.org.ua2012.weather;

/**
 * Created by dima on 1/5/2015.
 */
public class WeatherObservations {
    private Number lat; //
    private Number lng; //
    private Number elevation; //
    private String observation; //
    private String ICAO; //
    private String datetime; //
    private String countryCode; //
    private String temperature; //
    private String dewPoint; //
    private Number humidity; //
    private Number windDirection;
    private Number hectoPascAltimeter; //
    private String stationName; //
    private String weatherConditionCode;
    private String weatherCondition; //
    private String cloudsCode; //
    private String clouds; //

    public Number getLat() {
        return lat;
    }

    public void setLat(Number lat) {
        this.lat = lat;
    }

    public Number getLng() {
        return lng;
    }

    public void setLng(Number lng) {
        this.lng = lng;
    }

    public Number getElevation() {
        return elevation;
    }

    public void setElevation(Number elevation) {
        this.elevation = elevation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Number getHumidity() {
        return humidity;
    }

    public void setHumidity(Number humidity) {
        this.humidity = humidity;
    }

    public Number getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Number windDirection) {
        this.windDirection = windDirection;
    }

    public Number getHectoPascAltimeter() {
        return hectoPascAltimeter;
    }

    public void setHectoPascAltimeter(Number hectoPascAltimeter) {
        this.hectoPascAltimeter = hectoPascAltimeter;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWeatherConditionCode() {
        return weatherConditionCode;
    }

    public void setWeatherConditionCode(String weatherConditionCode) {
        this.weatherConditionCode = weatherConditionCode;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getCloudsCode() {
        return cloudsCode;
    }

    public void setCloudsCode(String cloudsCode) {
        this.cloudsCode = cloudsCode;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }
}
