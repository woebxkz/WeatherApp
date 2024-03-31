package console;

import api.accuweather_api.AccuWeatherApi;
import api.accuweather_api.AccuWeatherApiLocationResponse;
import api.open_meteo_api.OpenMeteoApi;
import api.open_meteo_api.OpenMeteoApiResults;
import api.open_meteo_api.OpenMeteoVariables;
import chart.Chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private AccuWeatherApiLocationResponse searchingLocation;


    private  AccuWeatherApiLocationResponse getLocalization(Scanner scanner){

        System.out.print("Podaj nazwę miasta dla którego chcesz sprawdzić pogodę: ");
        String city = scanner.nextLine();

        AccuWeatherApi accuWeatherApi = new AccuWeatherApi();

        while (true) {
            AccuWeatherApiLocationResponse[] responses;
            try {
                responses = accuWeatherApi.getLocationsByCityName(city);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (responses.length == 0) {
                System.out.print("Nie znaleziono miasta, podaj jeszcze raz: ");
                city = scanner.nextLine();
            } else {
                return responses[0];
            }
        }
    }
    public void startApp() {

        Scanner scanner = new Scanner(System.in);

        searchingLocation = getLocalization(scanner);


        List<OpenMeteoVariables> list = new ArrayList<>();
        list.add(OpenMeteoVariables.TEMPERATURE);
        list.add(OpenMeteoVariables.RELATIVE_HUMIDITY);
        list.add(OpenMeteoVariables.SEA_LEVEL_PRESSURE);
        list.add(OpenMeteoVariables.WIND_SPEED);
        list.add(OpenMeteoVariables.WIND_DIRECTION);


        OpenMeteoApi openMeteoApi = new OpenMeteoApi(searchingLocation.getGeoPosition().getLatitude(),
                searchingLocation.getGeoPosition().getLongitude(),
                list);

        try {
            OpenMeteoApiResults result = openMeteoApi.getAllData();
            if(result != null){



                String betaTitle = "city: " + searchingLocation.getLocalizedName()
                        + ", country: " + searchingLocation.getCountry().getLocalizedName()
                        + ", region: " +  searchingLocation.getRegion().getLocalizedName()+ " - ";

                showChart(OpenMeteoVariables.TEMPERATURE.getUserInfo(),
                        betaTitle +OpenMeteoVariables.TEMPERATURE.getUserInfo(),
                        result.getHourly().getTime(),
                        OpenMeteoVariables.TEMPERATURE.getUnit(),
                        result.getHourly().getTemperature_2m()
                );

                showChart(OpenMeteoVariables.RELATIVE_HUMIDITY.getUserInfo(),
                        betaTitle +OpenMeteoVariables.RELATIVE_HUMIDITY.getUserInfo(),
                        result.getHourly().getTime(),
                        OpenMeteoVariables.RELATIVE_HUMIDITY.getUnit(),
                        result.getHourly().getRelative_humidity_2m()
                );

                showChart(OpenMeteoVariables.SEA_LEVEL_PRESSURE.getUserInfo(),
                        betaTitle +OpenMeteoVariables.SEA_LEVEL_PRESSURE.getUserInfo(),
                        result.getHourly().getTime(),
                        OpenMeteoVariables.SEA_LEVEL_PRESSURE.getUnit(),
                        result.getHourly().getPressure_msl()
                );


                showChart(OpenMeteoVariables.WIND_SPEED.getUserInfo(),
                        betaTitle +OpenMeteoVariables.WIND_SPEED.getUserInfo(),
                        result.getHourly().getTime(),
                        OpenMeteoVariables.WIND_SPEED.getUnit(),
                        result.getHourly().getWind_speed_10m()
                );

                showChart(OpenMeteoVariables.WIND_DIRECTION.getUserInfo(),
                        betaTitle +OpenMeteoVariables.WIND_DIRECTION.getUserInfo(),
                        result.getHourly().getTime(),
                        OpenMeteoVariables.WIND_DIRECTION.getUnit(),
                        result.getHourly().getWind_direction_10m()
                );



            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Chart showChart(String fileTitle, String chartTitle, String[] x, String unit, Double[] y){

        Chart chart = new Chart(fileTitle);
        chart.addDataset(x,y,unit);
        chart.showChart(chartTitle);

        return chart;
    }

}