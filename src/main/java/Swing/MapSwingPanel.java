package Swing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapSwingPanel extends JPanel {
    private JXMapViewer mapViewer;
    private List<Painter<JXMapViewer>> painters;
    private Set<GeoPosition> allPositions = new HashSet<>();

    private boolean forceDefaultZoom = false;

    public void setForceDefaultZoom(boolean force) {
        this.forceDefaultZoom = force;
    }

    public MapSwingPanel() {
        setLayout(new BorderLayout());
        mapViewer = new JXMapViewer();

        // Configure tile factory with proper user agent
        OSMTileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);
        mapViewer.setTileFactory(tileFactory);

        // Enable panning and zooming controls
        mapViewer.setPanEnabled(true);
        //mapViewer.setZoomEnabled(true);
        mapViewer.setZoom(11); // Default zoom level

        // Center on Sri Lanka initially
        GeoPosition sriLanka = new GeoPosition(7.8731, 80.7718);
        mapViewer.setAddressLocation(sriLanka);
        mapViewer.setZoom(11);
        add(mapViewer, BorderLayout.CENTER);

        // Add mouse wheel listener for zooming
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        // Add mouse listeners for panning
        // Proper mouse interaction handlers
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

// Enable zoom with mouse wheel centered on cursor
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));


        painters = new ArrayList<>();
        add(mapViewer, BorderLayout.CENTER);
    }

    public void addWaypointPainter(Painter<JXMapViewer> painter) {
        painters.add(painter);
        updateOverlayPainter();
        mapViewer.repaint();
    }

    public void addPosition(GeoPosition position) {
        allPositions.add(position);
    }

    public void zoomToFitAllMarkers() {
        if (forceDefaultZoom) {
            SwingUtilities.invokeLater(() -> {
                mapViewer.setAddressLocation(new GeoPosition(7.8731, 80.7718));
                mapViewer.setZoom(11);
            });
            return;
        }
        if (!allPositions.isEmpty()) {
            // Calculate bounds that contain all markers with padding
            double minLat = Double.MAX_VALUE;
            double maxLat = -Double.MAX_VALUE;
            double minLon = Double.MAX_VALUE;
            double maxLon = -Double.MAX_VALUE;

            for (GeoPosition pos : allPositions) {
                minLat = Math.min(minLat, pos.getLatitude());
                maxLat = Math.max(maxLat, pos.getLatitude());
                minLon = Math.min(minLon, pos.getLongitude());
                maxLon = Math.max(maxLon, pos.getLongitude());
            }

            // Add padding (10% of the range)
            double latPadding = (maxLat - minLat) * 0.1;
            double lonPadding = (maxLon - minLon) * 0.1;

            minLat -= latPadding;
            maxLat += latPadding;
            minLon -= lonPadding;
            maxLon += lonPadding;

            // Center point of all markers
            GeoPosition center = new GeoPosition(
                    (minLat + maxLat) / 2,
                    (minLon + maxLon) / 2
            );

            // Calculate zoom level based on the area to cover
            int zoom = calculateOptimalZoomLevel(minLat, maxLat, minLon, maxLon);

            // Set the view
            SwingUtilities.invokeLater(() -> {
                mapViewer.setAddressLocation(center);
                mapViewer.setZoom(zoom);
            });
        }
    }

    private int calculateOptimalZoomLevel(double minLat, double maxLat, double minLon, double maxLon) {
        double latDiff = maxLat - minLat;
        double lonDiff = maxLon - minLon;
        double maxDiff = Math.max(latDiff, lonDiff);

        // More precise zoom level calculation
        if (maxDiff < 0.02) return 12;  // Very zoomed in
        if (maxDiff < 0.05) return 11;
        if (maxDiff < 0.1) return 10;
        if (maxDiff < 0.2) return 9;
        if (maxDiff < 0.5) return 8;
        if (maxDiff < 1.0) return 7;    // Good for city-level
        if (maxDiff < 2.0) return 6;    // Good for regional view
        if (maxDiff < 4.0) return 5;    // Good for country view
        return 4;                       // Very zoomed out
    }

    private void updateOverlayPainter() {
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(compoundPainter);
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }
}