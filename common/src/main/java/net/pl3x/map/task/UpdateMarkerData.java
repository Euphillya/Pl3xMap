package net.pl3x.map.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.pl3x.map.JsonArrayWrapper;
import net.pl3x.map.Key;
import net.pl3x.map.markers.marker.Marker;
import net.pl3x.map.util.FileUtil;
import net.pl3x.map.world.World;
import org.jetbrains.annotations.NotNull;

public class UpdateMarkerData implements Runnable {
    private final Gson gson = new GsonBuilder()
            //.setPrettyPrinting()
            //.disableHtmlEscaping()
            .serializeNulls()
            .registerTypeHierarchyAdapter(Marker.class, new Adapter())
            .setLenient()
            .create();

    private final World world;
    private final Map<Key, Long> lastUpdated = new HashMap<>();

    public UpdateMarkerData(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        Map<String, Integer> layers = new HashMap<>();

        this.world.getLayerRegistry().entries().forEach((key, layer) -> {
            layers.put(key.toString(), layer.getUpdateInterval());

            long now = System.currentTimeMillis() / 1000;
            long lastUpdate = this.lastUpdated.getOrDefault(key, 0L);

            if (now - lastUpdate > layer.getUpdateInterval()) {
                FileUtil.write(this.gson.toJson(layer.getMarkers()), this.world.getMarkersDir().resolve(key + ".json"));
                this.lastUpdated.put(key, now);
            }
        });

        FileUtil.write(this.gson.toJson(layers), this.world.getTilesDir().resolve("markers.json"));
    }

    private static class Adapter implements JsonSerializer<Marker<?>> {
        @Override
        @NotNull
        public JsonElement serialize(@NotNull Marker<?> marker, @NotNull Type type, @NotNull JsonSerializationContext context) {
            JsonArrayWrapper wrapper = new JsonArrayWrapper();
            wrapper.add(marker.getType());
            wrapper.add(marker);
            if (marker.getOptions() != null) {
                wrapper.add(marker.getOptions());
            }
            return wrapper.getJsonArray();
        }
    }
}
