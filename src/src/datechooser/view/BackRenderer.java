/*
 * BackRenderer.java
 *
 * Created on 29 Ноябрь 2006 г., 22:53
 *
 */

package datechooser.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

/**
 * Stores and shows background image.<br>
 * Хранит и отображает фоновый рисунок в соответсвии со стилем отображения.
 * @author Androsov Vadim
 * @since 1.0
 */
public class BackRenderer implements Serializable {
    
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_FILL = 1;
    public static final int ALIGN_TILE = 2;
    
    private int style;
    private URL url;
    private transient Image image;
    private transient int imageWidth;
    private transient int imageHeight;
    private transient int timesX;
    private transient int timesY;
    private transient int i;
    private transient int j;
    
    public BackRenderer(int style, String imageURLPath) {
        this.style = style;
        initializeByString(imageURLPath);
    }
    
    public BackRenderer(int style, URL imageURL) {
        this(style, imageURL, null);
    }
    
    private void initializeByString(String URLPath) {
        File file = new File(URLPath);
        if (!file.exists()) {
            setNoImage();
            return;
        }
        try {
            url = file.toURL();
        } catch (MalformedURLException ex) {
            setNoImage();
            return;
        }
        try {setImage(ImageIO.read(url));
        } catch (IOException ex) {
            setNoImage();
        }
        
    }
    
    private void setNoImage() {
        url = null;
        setImage(null);
    }
    
    public BackRenderer(int style, URL imageURL, Image image) {
        if (image == null) {
            try {
                setImage(ImageIO.read(imageURL));
                url = imageURL;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            setImage(image);
            url = imageURL;
        }
        this.style = style;
    }
    
    private void setImage(Image image) {
        this.image = image;
        if (image != null) {
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        }
    }
    
    public void render(Graphics2D g, Rectangle bounds) {
        if (image == null) return;
        switch(style) {
            case ALIGN_CENTER:
                renderCentered(g, bounds);
                break;
            case ALIGN_FILL:
                renderFilled(g, bounds);
                break;
            case ALIGN_TILE:
                renderTiled(g, bounds);
                break;
        }
    }
    
    private void renderCentered(Graphics2D g, Rectangle bounds) {
        g.drawImage(getImage(), (bounds.width - imageWidth) / 2,
                (bounds.height - imageHeight) / 2,
                imageWidth, imageHeight, null);
    }
    
    private void renderFilled(Graphics2D g, Rectangle bounds) {
        g.drawImage(getImage(), 0, 0, bounds.width, bounds.height, null);
    }
    
    private void renderTiled(Graphics2D g, Rectangle bounds) {
        timesX = bounds.width / imageWidth + 1;
        timesY = bounds.height / imageHeight + 1;
        for (int i = 0; i < timesX; i++) {
            for (int j = 0; j < timesY; j++) {
                g.drawImage(getImage(), i * imageWidth, j * imageHeight,
                        imageWidth, imageHeight, null);
            }
        }
    }
    
    public int getStyle() {
        return style;
    }
    
    public Image getImage() {
        return image;
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (image != null) {
            out.writeBoolean(true);
            ImageIO.write((BufferedImage)image, "jpg", out);
        } else {
            out.writeBoolean(false);
        }
    }
    
    private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (in.readBoolean()) {
           setImage(ImageIO.read(in));
        }
    }
    
    public URL getUrl() {
        return url;
    }
    
}
