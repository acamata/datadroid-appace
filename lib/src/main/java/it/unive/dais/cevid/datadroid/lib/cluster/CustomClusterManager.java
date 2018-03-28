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

public class CustomClusterManager extends ClusterManager<ClusterItem> implements GoogleMap.OnCameraIdleListener{
    private List<ClusterItem> clusterItems;
    private float markerColor;
    private ClusterRenderer clusterRenderer;
    private CustomClusterManager(Context context, GoogleMap map, boolean shouldCluster) {
        super(context, map);
        map.setOnCameraIdleListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(this.getMarkerManager());
        setRenderer(new ClusterRenderer(context, map, this));
        if(!shouldCluster)
            disableCluster();
    }

    private CustomClusterManager(Context context, GoogleMap map, MarkerManager markerManager, boolean shouldCluster) {
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
        Log.i("CustomClusterManager", "setting clusterItems, size: "+clusterItems.size());
        this.clusterItems = clusterItems;
        addItems(clusterItems);
    }

    public void setMarkerColor(float markerColor) {
        Log.d("ClusterManager", "setting color to "+markerColor);
        clusterRenderer.setMarkerColor(markerColor);
    }

    public static class Builder{

        private ClusterRenderer clusterRenderer;
        private OnClusterClickListener<ClusterItem> clusterClickListener;
        private OnClusterItemClickListener<ClusterItem> clusterItemClickListener;
        private OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener;
        private List<ClusterItem> clusterItems = new ArrayList<>();
        private float markerColor = BitmapDescriptorFactory.HUE_RED;
        private boolean clusterActivated = true;

        public Builder withOnClusterClickListener(OnClusterClickListener<ClusterItem> clusterClickListener) {
            this.clusterClickListener = clusterClickListener;
            return this;
        }
        public Builder withRenderer(ClusterRenderer clusterRenderer){
            this.clusterRenderer = clusterRenderer;
            return this;
        }
        public Builder withClusterActivated(boolean b){
            this.clusterActivated = b;
            return this;
        }
        public Builder withOnClusterItemClickListener(OnClusterItemClickListener<ClusterItem> clusterItemClickListener){
            this.clusterItemClickListener = clusterItemClickListener;
            return this;
        }
        public Builder withclusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener){
            this.clusterItemInfoWindowClickListener = clusterItemInfoWindowClickListener;
            return this;
        }
        public Builder withClusterItems(List<MapItem> clusterItems){
            for(MapItem mI : clusterItems)
                this.clusterItems.add(new ClusterItemAdapter(mI));
            return this;
        }

        public Builder withMarkerColor(float markerColor){
            this.markerColor = markerColor;
            return this;
        }
        public CustomClusterManager create(Context context, GoogleMap map) {
            CustomClusterManager cm =  new CustomClusterManager(context, map, clusterActivated);
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
}
