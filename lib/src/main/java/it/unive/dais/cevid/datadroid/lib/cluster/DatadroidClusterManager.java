package it.unive.dais.cevid.datadroid.lib.cluster;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import it.unive.dais.cevid.datadroid.lib.util.MapItem;


/**
 * Created by giacomo on 28/03/18.
 */

public class DatadroidClusterManager extends ClusterManager<ClusterItem> implements GoogleMap.OnCameraIdleListener{
    private List<ClusterItem> clusterItems;
    private float markerColor;
    private ClusterRenderer clusterRenderer;
    protected DatadroidClusterManager(Context context, GoogleMap map, boolean shouldCluster) {
        super(context, map);
        map.setOnCameraIdleListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(this.getMarkerManager());
        setRenderer(new ClusterRenderer(context, map, this));
        if(!shouldCluster)
            disableCluster();
    }

    private DatadroidClusterManager(Context context, GoogleMap map, MarkerManager markerManager, boolean shouldCluster) {
        super(context, map, markerManager);
        map.setOnCameraIdleListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(this.getMarkerManager());
        setRenderer(new ClusterRenderer(context, map, this));
        if(!shouldCluster)
            disableCluster();
    }
    public void setRenderer(ClusterRenderer cr){
        super.setRenderer(cr);
        this.clusterRenderer = cr;
    }
    public void resetMarkers(){
        clearItems();
        addItems(clusterItems);
        cluster();
    }

    public void removeMarkers(){
        clearItems();
        clusterItems = null;
        cluster();
    }
    public void disableCluster(){
        clusterRenderer.shouldCluster(false);
        cluster();
    }
    public void enableCluster(){
        clusterRenderer.shouldCluster(true);
        cluster();
    }

    public void setClusterItems(List<ClusterItem> clusterItems){
        Log.i("DatadroidClusterManager", "setting clusterItems, size: "+clusterItems.size());
        this.clusterItems = clusterItems;
        addItems(clusterItems);
    }

    public void setMarkerColor(float markerColor) {
        Log.d("ClusterManager", "setting color to "+markerColor);
        clusterRenderer.setMarkerColor(markerColor);
    }


}
