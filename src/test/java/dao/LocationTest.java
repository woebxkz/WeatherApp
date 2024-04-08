package dao;

import org.junit.jupiter.api.Test;
import util.CreationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void testConstructorValidValues() throws CreationException {
        UUID id = UUID.randomUUID();
        double longitude = 10.5;
        double latitude = 20.7;
        String city = "New York";
        String region = "NY";
        String country = "USA";

        Location location = new Location(id, longitude, latitude, city, region, country);

        assertEquals(id, location.getId());
        assertEquals(longitude, location.getLongtitude());
        assertEquals(latitude, location.getLatitude());
        assertEquals(city, location.getCity());
        assertEquals(region, location.getRegion());
        assertEquals(country, location.getCountry());
    }

    @Test
    public void testConstructorNullIdGeneratesRandomId() throws CreationException {
        double longitude = 10.5;
        double latitude = 20.7;
        String city = "Los Angeles";
        String region = "CA";
        String country = "USA";

        Location location = new Location(null, longitude, latitude, city, region, country);

        assertNotNull(location.getId());
        assertEquals(longitude, location.getLongtitude());
        assertEquals(latitude, location.getLatitude());
        assertEquals(city, location.getCity());
        assertEquals(region, location.getRegion());
        assertEquals(country, location.getCountry());
    }

    @Test
    public void testInvalidLongitudeThrowsCreationException() {
        UUID id = UUID.randomUUID();
        double longitude = 190.0;

        assertThrows(CreationException.class, () -> {
            new Location(id, longitude, 30.0, "City", "Region", "Country");
        });
    }

    @Test
    public void testInvalidLatitudeThrowsCreationException() {
        UUID id = UUID.randomUUID();
        double latitude = 100.0;

        assertThrows(CreationException.class, () -> {
            new Location(id, 10.0, latitude, "City", "Region", "Country");
        });
    }

    @Test
    public void testEmptyCityNameThrowsCreationException() {
        UUID id = UUID.randomUUID();

        assertThrows(CreationException.class, () -> {
            new Location(id, 10.0, 20.0, "", "Region", "Country");
        });
    }

    @Test
    public void testEmptyCountryNameThrowsCreationException() {
        UUID id = UUID.randomUUID();

        assertThrows(CreationException.class, () -> {
            new Location(id, 10.0, 20.0, "City", "Region", "");
        });
    }

}