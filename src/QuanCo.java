import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QuanCo {

    public int col,row;
    public int x,y;
    public boolean isWhile;
    public String name;
    public int value;
    Image image;
    BanCo banCo;

    public QuanCo(BanCo banCo) {
        this.banCo = banCo;
    }
    public void paint(Graphics2D graphics2D){
        graphics2D.drawImage(image,x,y,null);
    }
}
