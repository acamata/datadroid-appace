package it.unive.dais.cevid.datadroid.lib.cluster;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

import java.util.ArrayList;


/**
 * Created by giacomo on 28/03/18.
 */

public class CustomClusterManager<T extends ClusterItem> extends ClusterManager<T> implements GoogleMap.OnCameraIdleListener{
    private ArrayList<T> clusterItems;
    private CustomClusterManager(Context context, GoogleMap map) {
        super(context, map);
    }

    private CustomClusterManager(Context context, GoogleMap map, MarkerManager markerManager) {
        super(context, map, markerManager);
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

    public void setClusterItems(ArrayList<T> clusterItems){
        Log.i("CustomClusterManager", "setting clusterItems, size: "+clusterItems.size());
        this.clusterItems = clusterItems;
        addItems(clusterItems);
    }
    public static class Builder<T extends ClusterItem> {

        private ClusterRenderer<T> clusterRenderer;
        private OnClusterClickListener<T> clusterClickListener;
        private OnClusterItemClickListener<T> clusterItemClickListener;
        private OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener;
        private ArrayList<T> clusterItems;

        public Builder withOnClusterClickListener(OnClusterClickListener<T> clusterClickListener) {
            this.clusterClickListener = clusterClickListener;
            return this;
        }
        public Builder withRenderer(ClusterRenderer<T> clusterRenderer){
            this.clusterRenderer = clusterRenderer;
            return this;
        }
        public Builder withOnClusterItemClickListener(OnClusterItemClickListener<T> clusterItemClickListener){
            this.clusterItemClickListener = clusterItemClickListener;
            return this;
        }
        public Builder withclusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener){
            this.clusterItemInfoWindowClickListener = clusterItemInfoWindowClickListener;
            return this;
        }
        public Builder withClusterItems(ArrayList<T> clusterItems){
            this.clusterItems = clusterItems;
            return this;

        }
        public CustomClusterManager create(Context context, GoogleMap map) {
            CustomClusterManager<T> cm =  new CustomClusterManager<>(context, map);
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
            return cm;
        }

    }
}
