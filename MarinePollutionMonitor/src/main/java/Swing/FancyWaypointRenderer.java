package Swing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;

public class FancyWaypointRenderer implements WaypointRenderer<Waypoint> {

    private final BufferedImage markerImage;

    public FancyWaypointRenderer() {
        try {
            URL imageUrl = getClass().getResource("/Images/LocationMarker.png");
            markerImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Marker image not found", e);
        }
    }

    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
        GeoPosition pos = wp.getPosition();
        Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());

        int x = (int) point.getX() - markerImage.getWidth() / 2;
        int y = (int) point.getY() - markerImage.getHeight();

        g.drawImage(markerImage, x, y, null);
    }
}
