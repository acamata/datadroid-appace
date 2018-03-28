package it.unive.dais.cevid.datadroid.lib.cluster;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

public class ClusterRenderer extends DefaultClusterRenderer<ClusterItem> {
    protected final float mDensity;
    protected ShapeDrawable mColoredCircleBackground;
    protected SparseArray<BitmapDescriptor> mIcons = new SparseArray();
    protected int mMinClusterSize = 5;
    protected IconGenerator mIconGenerator;
    protected float markerColor;
    protected boolean shouldCluster = true;
    public ClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mIconGenerator = new IconGenerator(context);
        this.mIconGenerator.setBackground(this.makeClusterBackground());
        this.mIconGenerator.setContentView(this.makeSquareTextView(context));
        this.mIconGenerator.setTextAppearance(com.google.maps.android.R.style.amu_ClusterIcon_TextAppearance);
        this.markerColor = BitmapDescriptorFactory.HUE_RED;
    }
    public void setMarkerColor(float markerColor){
        this.markerColor = markerColor;
    }
    public int getMinClusterSize(){
        return mMinClusterSize;
    }
    @Override
    protected void onBeforeClusterRendered(Cluster<ClusterItem> cluster, MarkerOptions markerOptions) {
        //Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        int bucket = this.getBucket(cluster);
        BitmapDescriptor descriptor = (BitmapDescriptor)this.mIcons.get(bucket);
        if(descriptor == null) {
            this.mColoredCircleBackground.getPaint().setColor(this.getColor(bucket));
            descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(String.valueOf(cluster.getSize()))); //real numbers
            //descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(this.getClusterText(bucket))); //number +
            this.mIcons.put(bucket, descriptor);
        }

        markerOptions.icon(descriptor);
    }

    protected void onBeforeClusterItemRendered(ClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor));

    }
    private LayerDrawable makeClusterBackground() {
        this.mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(-2130706433);
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, this.mColoredCircleBackground});
        int strokeWidth = (int)(this.mDensity * 3.0F);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        squareTextView.setLayoutParams(layoutParams);
        squareTextView.setId(com.google.maps.android.R.id.amu_text);
        int twelveDpi = (int)(12.0F * this.mDensity);
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
        return squareTextView;
    }

    protected boolean shouldRenderAsCluster(Cluster<ClusterItem> cluster) {
        if(shouldCluster)
            return cluster.getSize() > this.mMinClusterSize;
        else
            return false;
    }


    public void shouldCluster(boolean b) {
        shouldCluster = b;
    }
}