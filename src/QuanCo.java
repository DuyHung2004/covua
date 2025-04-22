import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class QuanCo implements Cloneable{

    public int col,row;
    public int x,y;
    public boolean isWhite;
    public String name;
    public int value;
    Image image;
    BanCo banCo;

    public QuanCo(BanCo banCo) {
        this.banCo = banCo;
    }

    public boolean dichuyenbatbuoc =true;
    public boolean dichuyenhople(int col, int row){
        return true;
    }
    public boolean dichuyenvacham(int col,int row){
        return false;
    }
    public void paint(Graphics2D graphics2D){
        graphics2D.drawImage(image,x,y,null);
    }

    @Override
    public String toString() {
        return "QuanCo{" +
                "col=" + col +
                ", row=" + row +
                ", x=" + x +
                ", y=" + y +
                ", isWhite=" + isWhite +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", image=" + image +
                ", banCo=" + banCo +
                ", dichuyenbatbuoc=" + dichuyenbatbuoc +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
