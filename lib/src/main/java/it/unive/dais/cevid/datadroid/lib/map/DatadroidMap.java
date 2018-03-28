package it.unive.dais.cevid.datadroid.lib.map;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import it.unive.dais.cevid.datadroid.lib.cluster.ClusterItemAdapter;
import it.unive.dais.cevid.datadroid.lib.cluster.ClusterRenderer;
import it.unive.dais.cevid.datadroid.lib.cluster.DatadroidClusterManager;
import it.unive.dais.cevid.datadroid.lib.cluster.DatadroidClusterManagerBuilder;
import it.unive.dais.cevid.datadroid.lib.parser.AppaltiParser;
import it.unive.dais.cevid.datadroid.lib.util.MapItem;


/**
 * Created by giacomo on 28/03/18.
 */

public class DatadroidMap implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private OnMapReadyCallback customOnMapReady;
    private DatadroidClusterManagerBuilder dClusterManagerBuilder;
    private DatadroidClusterManager dClusterManager;
    private Context context;
    private SupportMapFragment supportMapFragment;

    public DatadroidMap(Context context, SupportMapFragment mapFragment, DatadroidClusterManagerBuilder clusterManagerBuilder) {
        this.context = context;
        this.supportMapFragment = mapFragment;
        this.dClusterManagerBuilder = clusterManagerBuilder;
    }

    public GoogleMap getGMap(){
        return googleMap;
    }

    public void getMapAsync(){
        supportMapFragment.getMapAsync(this);
    }

    public DatadroidClusterManager getdClusterManager(){
        return dClusterManager;
    }
    public void setCustomOnMapReady(OnMapReadyCallback customOnMapReady){
        this.customOnMapReady = customOnMapReady;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.i("MapBuilder", "onMapReady!");
        dClusterManager = dClusterManagerBuilder.create(context, googleMap);
        dClusterManager.cluster();
        if(customOnMapReady!=null)
            customOnMapReady.onMapReady(googleMap);

    }

    public static class Builder{

        private OnMapReadyCallback customOnMapReady;
        private SupportMapFragment mapFragment;
        private GoogleMap.OnMapClickListener onMapClick;
        private GoogleMap.OnCameraMoveStartedListener onCameraMoveStarted;
        private GoogleMap.OnMapLongClickListener onMapLongClick;
        private ClusterRenderer clusterRenderer;
        private ClusterManager.OnClusterClickListener<ClusterItem> clusterClickListener;
        private ClusterManager.OnClusterItemClickListener<ClusterItem> clusterItemClickListener;
        private ClusterManager.OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener;
        private float markerColor = BitmapDescriptorFactory.HUE_RED;
        private boolean clusterActivated = true;
        private List<MapItem> mapItems = new ArrayList<>();

        private Context context;

        private DatadroidClusterManagerBuilder clusterManagerBuilder;
        private DatadroidMap datadroidMap;


        public Builder withOnMapReadyCallback(OnMapReadyCallback customOnMapReady){
            this.customOnMapReady = customOnMapReady;
            return this;
        }

        public Builder withOnMapClickListener(GoogleMap.OnMapClickListener onMapClick){
            this.onMapClick = onMapClick;
            return this;
        }
        public Builder withOnCameraMoveStartedListener(GoogleMap.OnCameraMoveStartedListener onCamerMoveStarted){
            this.onCameraMoveStarted = onCamerMoveStarted;
            return this;
        }
        public Builder withOnMapLongClickListener(GoogleMap.OnMapLongClickListener onMapLongClickListener){
            this.onMapLongClick = onMapLongClickListener;
            return this;
        }
        public Builder withOnClusterClickListener(ClusterManager.OnClusterClickListener<ClusterItem> clusterClickListener) {
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
        public Builder withOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<ClusterItem> clusterItemClickListener){
            this.clusterItemClickListener = clusterItemClickListener;
            return this;
        }
        public Builder withclusterItemInfoWindowClickListener(ClusterManager.OnClusterItemInfoWindowClickListener clusterItemInfoWindowClickListener){
            this.clusterItemInfoWindowClickListener = clusterItemInfoWindowClickListener;
            return this;
        }
        public Builder withClusterItems(List<MapItem> clusterItems){
            mapItems = clusterItems;
            return this;
        }

        public Builder withMarkerColor(float markerColor){
            this.markerColor = markerColor;
            return this;
        }

        public DatadroidMap create(Context context, SupportMapFragment mapFragment) {
            clusterManagerBuilder = new DatadroidClusterManagerBuilder().
                    withClusterActivated(clusterActivated).
                    withClusterItems(mapItems).
                    withOnClusterClickListener(clusterClickListener).
                    withRenderer(clusterRenderer).
                    withOnClusterItemClickListener(clusterItemClickListener).
                    withclusterItemInfoWindowClickListener(clusterItemInfoWindowClickListener).
                    withMarkerColor(markerColor);

            DatadroidMap ddm =  new DatadroidMap(context, mapFragment, clusterManagerBuilder);
            ddm.setCustomOnMapReady(customOnMapReady);
            return ddm;
        }
        public DatadroidMap createAndGetMapAsync(Context context, SupportMapFragment mapFragment) {
            DatadroidMap ddm = create(context, mapFragment);
            ddm.getMapAsync();
            return ddm;
        }
    }
}
