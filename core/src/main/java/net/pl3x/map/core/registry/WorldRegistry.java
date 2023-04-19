package net.pl3x.map.core.registry;

import javax.management.openmbean.KeyAlreadyExistsException;
import net.pl3x.map.core.world.World;

public class WorldRegistry extends Registry<World> {
    public World register(World world) {
        if (has(world.getName())) {
            throw new KeyAlreadyExistsException("World already registered: " + world.getName());
        }
        return register(world.getName(), world);
    }

    @Override
    public World unregister(String id) {
        World world = this.entries.remove(id);
        if (world != null) {
            world.getMarkerTask().cancel();
            world.getRegionFileWatcher().interrupt();
        }
        return world;
    }
}