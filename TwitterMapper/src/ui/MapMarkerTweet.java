package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import twitter4j.Status;
import util.ImageCache;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MapMarkerTweet extends MapMarkerCircle {
    public static final double defaultMarkerSize = 20.0;
    private final int borderSize = 5;
    private final Status status;
    private final Image image;
    private final Color color;

    public MapMarkerTweet(Layer layer, Coordinate coord, Color color, Status status) {
        super(layer, null, coord, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        this.status = status;
        this.color = color;
        this.image = ImageCache.getInstance().getImage(status.getUser().getProfileImageURL());
        setBackColor(color);
        setColor(color);
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public void paint(Graphics g, Point position, int radius) {
        int sizeImage = (int)defaultMarkerSize;
        int halfSizeImage = sizeImage / 2;
        int xImage = position.x - halfSizeImage;
        int yImage = position.y - halfSizeImage;

        int size = sizeImage + borderSize * 2;
        int x = xImage - borderSize;
        int y = yImage - borderSize;

        g.setClip(new Ellipse2D.Float(x, y, size, size));
        g.setColor(color);
        g.fillOval(x, y, size, size);

        g.setClip(new Ellipse2D.Float(xImage, yImage, sizeImage, sizeImage));
        g.drawImage(image, xImage, yImage, sizeImage, sizeImage, null);

    }
}
