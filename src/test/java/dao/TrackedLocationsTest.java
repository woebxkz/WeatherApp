package dao;

import org.junit.jupiter.api.Test;
import util.CreationException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrackedLocationsTest {

    TrackedLocations trackedLocations = new TrackedLocations();

    @Test
    public void testAddLocation() throws CreationException {
        Location location = new Location(null, 20, 30, "New York", "NY", "USA");
        trackedLocations.addLocation(location);

        List<Location> locations = trackedLocations.getLocations();
        assertEquals(1, locations.size());
        assertEquals(location, locations.get(0));
    }

    @Test
    public void testRemoveLocationById() throws CreationException {
        Location location1 = new Location(null, -150, 88, "Los Angeles", "CA", "USA");
        Location location2 = new Location(null, -30, 44,"San Francisco", "CA", "USA");

        trackedLocations.addLocation(location1);
        trackedLocations.addLocation(location2);

        UUID idToRemove = location1.getId();
        trackedLocations.removeLocation(idToRemove);

        List<Location> locations = trackedLocations.getLocations();
        assertEquals(1, locations.size());
        assertFalse(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testRemoveLocationByName() throws CreationException {
        Location location1 = new Location(null, 58, 33, "Paris", "Île-de-France", "France");
        Location location2 = new Location(null, -44, 11, "Rome", "Lazio", "Italy");

        trackedLocations.addLocation(location1);
        trackedLocations.addLocation(location2);

        trackedLocations.removeLocation("Paris");

        List<Location> locations = trackedLocations.getLocations();
        assertEquals(1, locations.size());
        assertFalse(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }
}