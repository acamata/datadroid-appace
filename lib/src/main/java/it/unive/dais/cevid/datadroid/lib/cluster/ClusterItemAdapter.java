package it.unive.dais.cevid.datadroid.lib.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import it.unive.dais.cevid.datadroid.lib.util.MapItem;

/**
 * Created by giacomo on 28/03/18.
 */

public class ClusterItemAdapter implements ClusterItem {
    private MapItem mapItem;
    public ClusterItemAdapter(MapItem mapItem){
        this.mapItem = mapItem;
    }
    @Override
    public LatLng getPosition() {
        try {
            return mapItem.getPosition();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            return mapItem.getTitle();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getSnippet() {
        try {
            return mapItem.getDescription();
        } catch (Exception e) {
            return null;
        }
    }
}
