package dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrackedLocations {
    private  Map<UUID, Location> locations;
//	private  TrackedLocations instance;

    public TrackedLocations() {
        locations = new Hashtable<>(10);
    }


    public Map<UUID, Location> getLocations() {
        return this.locations;
    }

    public Map<UUID, Location> addLocation(UUID id, Location loc) {
        if(id == null) id = UUID.randomUUID();
        this.locations.put(id, loc);
//		Map<UUID, Location> locmapLoc = getLocations();
//		for (Map.Entry<UUID, Location> entry : this.locations.entrySet()) {
//			UUID key = entry.getKey();
//			Location val = entry.getValue();
//			System.out.println(val.toString());
//		}
        //System.out.println(id+"\n"+getInstance().locations.get(id).toString());
        return this.locations;
    }

    public Map<UUID, Location> removeLocation(UUID id) {
        this.locations.remove(id);
        return this.locations;
    }

    public Map<UUID, Location> removeLocation(String name) {

        Map<UUID, Location> loc = this.locations;

        loc = loc.entrySet().stream().filter( e -> !e.getValue()
                .getCity()
                .equals(name))
                .collect(Collectors
                        .toMap(s -> s.getKey(), s -> s.getValue()));
//		for (Map.Entry<UUID, Location> entry : loc.entrySet()) {
//			UUID key = entry.getKey();
//			Location val = entry.getValue();
//			if (val.getCity().equals(name)) {
//				loc.remove(key);
//				break;
//			}
//		}

        return this.locations;
    }
}
