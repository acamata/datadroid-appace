package it.unive.dais.cevid.datadroid.lib.cluster;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import it.unive.dais.cevid.datadroid.lib.util.MapItem;

/**
 * Created by giacomo on 28/03/18.
 */

public class DatadroidClusterManagerBuilder {

    private ClusterRenderer clusterRenderer;
    private ClusterManager.OnClusterClickListener<ClusterItem> clusterClickListener;
    private ClusterManager.OnClusterItemClickListener<ClusterItem> clusterItemClickListener;
    private ClusterManager.OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener;
    private List<ClusterItem> clusterItems = new ArrayList<>();
    private float markerColor = BitmapDescriptorFactory.HUE_RED;
    private boolean clusterActivated = true;

    public DatadroidClusterManagerBuilder withOnClusterClickListener(ClusterManager.OnClusterClickListener<ClusterItem> clusterClickListener) {
        this.clusterClickListener = clusterClickListener;
        return this;
    }
    public DatadroidClusterManagerBuilder withRenderer(ClusterRenderer clusterRenderer){
        this.clusterRenderer = clusterRenderer;
        return this;
    }
    public DatadroidClusterManagerBuilder withClusterActivated(boolean b){
        this.clusterActivated = b;
        return this;
    }
    public DatadroidClusterManagerBuilder withOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<ClusterItem> clusterItemClickListener){
        this.clusterItemClickListener = clusterItemClickListener;
        return this;
    }
    public DatadroidClusterManagerBuilder withclusterItemInfoWindowClickListener(ClusterManager.OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener){
        this.clusterItemInfoWindowClickListener = clusterItemInfoWindowClickListener;
        return this;
    }
    public DatadroidClusterManagerBuilder withClusterItems(List<MapItem> clusterItems){
        Log.i("MapBuilder", ""+clusterItems.size());
        for(MapItem mI : clusterItems)
            this.clusterItems.add(new ClusterItemAdapter(mI));
        return this;
    }

    public DatadroidClusterManagerBuilder withMarkerColor(float markerColor){
        this.markerColor = markerColor;
        return this;
    }
    public DatadroidClusterManager create(Context context, GoogleMap map) {
        DatadroidClusterManager cm =  new DatadroidClusterManager(context, map, clusterActivated);
        if(clusterRenderer!=null){
            cm.setRenderer(clusterRenderer);
        }
        if(clusterClickListener!=null){
            cm.setOnClusterClickListener(clusterClickListener);
        }
        if(clusterItemInfoWindowClickListener!=null){
            cm.setOnClusterItemInfoWindowClickListener(clusterItemInfoWindowClickListener);
        }
        if(clusterItemClickListener!=null){
            cm.setOnClusterItemClickListener(clusterItemClickListener);
        }
        if(clusterItems!=null) {
            cm.setClusterItems(clusterItems);
        }
        cm.setMarkerColor(markerColor);

        return cm;
    }

}
