package weather_app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import com.google.gson.Gson;
import dao.Location;
import dao.TrackedLocations;
import api.open_meteo_api.OpenMeteoApi;
import api.open_meteo_api.OpenMeteoApiResults;
import api.open_meteo_api.OpenMeteoVariables;
import util.CreationException;

public class Main {

    private static int licznik = 0;
    private static Location loc;
    private static TrackedLocations locat;
    private static int licz;

    public static void main(String[] args) {

        Gson gson = new Gson();

        locat = new TrackedLocations();

        List<OpenMeteoVariables> list = new ArrayList<>();
        list.add(OpenMeteoVariables.TEMPERATURE);
        list.add(OpenMeteoVariables.RELATIVE_HUMIDITY);
        list.add(OpenMeteoVariables.SEA_LEVEL_PRESSURE);
        list.add(OpenMeteoVariables.WIND_SPEED);
        list.add(OpenMeteoVariables.WIND_DIRECTION);

        OpenMeteoApi openMeteoApi = new OpenMeteoApi(52.2298,21.0118,list);

        try {
            OpenMeteoApiResults result = openMeteoApi.getAllData();

            Arrays.stream(result.getHourly().getTime()).sequential().forEach(System.out::println);
            Arrays.stream(result.getHourly().getTemperature_2m()).sequential().forEach(System.out::println);
            Arrays.stream(result.getHourly().getRelative_humidity_2m()).sequential().forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true) {
            chooseFromMenu();
        }

    }

    private static void chooseFromMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Wybierz akcję: " +
                "\ndodaj miejsce (1)" +
                "\nusuń miejsce (2) " +
                "\npokaż wszystkie lokalizacje (3)");
        Consumer<? super Location> streamConsumer =  new Consumer<Location>() {

            @Override
            public void accept(Location t) {
                licznik++;
                System.out.println(t);
                System.out.println("");
            }

        };
        int akcja = sc.nextInt();

        switch(akcja) {
            case 1:
                System.out.println("Podaj długość geograficzną:");
                double longtitude = sc.nextDouble();
                System.out.println("Podaj szerokość geograficzną:");
                double latitude = sc.nextDouble();
                System.out.println("Podaj nazwę miasta:");
                String city = sc.next();
                System.out.println("Podaj region:");
                String region = sc.next();
                System.out.println("Podaj kraj:");
                String country = sc.next();
                try {
                    loc = new Location(null, longtitude, latitude, city, region, country);
                    System.out.println("Założono nową lokalizację " + loc.toString().toString());
                    locat.addLocation(loc);
                } catch (CreationException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Podaj nazwę miasta:");
                String cityToRemove = sc.next();
                locat.removeLocation(cityToRemove);
                break;
            case 3:
                locat.getLocations()
                        .stream()
                        .map(Location::toString)
                        .peek(System.out::println)
                        .forEach(System.out::println);
                break;
            default:
                long l = locat.getLocations().stream()
                        .peek(streamConsumer)
                        .count();
        }
    }

}