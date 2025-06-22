package Swing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapGetCoordinates extends JPanel {
    private JXMapViewer mapViewer;
    private List<Painter<JXMapViewer>> painters;
    private Set<GeoPosition> allPositions = new HashSet<>();
    private boolean forceDefaultZoom = false;

    private Set<Waypoint> waypoints = new HashSet<>();
    private WaypointPainter<Waypoint> waypointPainter;





    // ✅ Listener interface
    public interface CoordinateClickListener {
        void onCoordinateClicked(double lat, double lon);
    }

    private CoordinateClickListener listener;

    public void setCoordinateClickListener(CoordinateClickListener listener) {
        this.listener = listener;
    }

    public MapGetCoordinates() {
        setLayout(new BorderLayout());
        mapViewer = new JXMapViewer();

        waypointPainter = new WaypointPainter<>();
        waypointPainter.setRenderer(new FancyWaypointRenderer());
        waypointPainter.setWaypoints(waypoints);
        painters = new ArrayList<>();

        painters.add(waypointPainter);
        updateOverlayPainter();



        // Tile factory setup
        OSMTileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);
        mapViewer.setTileFactory(tileFactory);

        // Default view settings
        GeoPosition sriLanka = new GeoPosition(7.8731, 80.7718);
        mapViewer.setZoom(11);
        mapViewer.setAddressLocation(sriLanka);

        // Controls
        mapViewer.setPanEnabled(true);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

        // 🧭 Mouse click to get coordinates
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D worldPos = e.getPoint();
                GeoPosition geo = mapViewer.convertPointToGeoPosition(worldPos);

                // Trigger listener if set ✅
                if (listener != null) {
                    listener.onCoordinateClicked(geo.getLatitude(), geo.getLongitude());
                }

                allPositions.add(geo);

                // 🧹 Clear previous marker
                waypoints.clear();

// ➕ Add new marker
                DefaultWaypoint newWaypoint = new DefaultWaypoint(geo);
                waypoints.add(newWaypoint);

// 🔄 Refresh
                waypointPainter.setWaypoints(waypoints);
                mapViewer.repaint();

            }
        });

        // Painter list
        painters = new ArrayList<>();
        add(mapViewer, BorderLayout.CENTER);
    }

    public void clearMarkers() {
        waypoints.clear();
        allPositions.clear(); // optional, if you want zoom to reset too
        waypointPainter.setWaypoints(waypoints);
        mapViewer.repaint();
    }


    public void addWaypointPainter(Painter<JXMapViewer> painter) {
        painters.add(painter);
        updateOverlayPainter();
        mapViewer.repaint();
    }

    public void addPosition(GeoPosition position) {
        allPositions.add(position);
    }

    public void setForceDefaultZoom(boolean force) {
        this.forceDefaultZoom = force;
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

            double latPadding = (maxLat - minLat) * 0.1;
            double lonPadding = (maxLon - minLon) * 0.1;
            minLat -= latPadding;
            maxLat += latPadding;
            minLon -= lonPadding;
            maxLon += lonPadding;

            GeoPosition center = new GeoPosition(
                    (minLat + maxLat) / 2,
                    (minLon + maxLon) / 2
            );

            int zoom = calculateOptimalZoomLevel(minLat, maxLat, minLon, maxLon);

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

        if (maxDiff < 0.02) return 12;
        if (maxDiff < 0.05) return 11;
        if (maxDiff < 0.1) return 10;
        if (maxDiff < 0.2) return 9;
        if (maxDiff < 0.5) return 8;
        if (maxDiff < 1.0) return 7;
        if (maxDiff < 2.0) return 6;
        if (maxDiff < 4.0) return 5;
        return 4;
    }

    private void updateOverlayPainter() {
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(compoundPainter);
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }
}
