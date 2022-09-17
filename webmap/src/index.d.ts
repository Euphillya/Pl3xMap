import * as L from "leaflet";
import {MarkerLayer} from "./layergroup/MarkerLayer";
import {Player} from "./player/Player";
import {World} from "./world/World";

declare global {
    interface WindowEventMap {
        overlayadded: CustomEvent<MarkerLayer>;
        overlayremoved: CustomEvent<MarkerLayer>;
        playeradded: CustomEvent<Player>;
        playerremoved: CustomEvent<Player>;
        rendererselected: CustomEvent<World>;
        worldadded: CustomEvent<World>;
        worldremoved: CustomEvent<World>;
        worldselected: CustomEvent<World>;
    }
}

module "leaflet" {
    export function ellipse(latLng: L.LatLngExpression, radii: L.PointTuple, tilt: number, options: L.PathOptions): Ellipse;

    interface Ellipse extends L.Path {
        setRadius(radii: L.PointTuple): this;

        getRadius(): L.Point;

        setTilt(tilt: number): this;

        getBounds(): L.LatLngBounds;

        getLatLng(): L.LatLng;

        setLatLng(latLng: L.LatLngExpression): this;
    }
}