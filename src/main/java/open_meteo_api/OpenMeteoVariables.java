package open_meteo_api;

public enum OpenMeteoVariables {
    TEMPERATURE("temperature_2m","Temperature (2 m)"),
    RELATIVE_HUMIDITY("relative_humidity_2m","Relative Humidity (2 m)"),
    SEA_LEVEL_PRESSURE("pressure_msl","Sealevel Pressure"),
    WIND_SPEED("wind_speed_10m","Wind Speed (10 m)"),
    WIND_DIRECTION("wind_direction_10m","Wind Direction (10 m)");


    private String urlCode;
    private String userInfo;

    OpenMeteoVariables(String urlCode, String userInfo){
        this.urlCode = urlCode;
        this.userInfo = userInfo;
    }

    public String getUrlCode(){
        return this.urlCode;
    }

    public String getUserInfo(){
        return this.userInfo;
    }
}