import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
    public void paint(Graphics2D graphics2D) {
        // Lưu lại transform ban đầu
        AffineTransform oldTransform = graphics2D.getTransform();
        int size= banCo.size;
        // Nếu bàn cờ đang xoay và là lượt của đối phương
        if (banCo.isRotated()) {
            // Tính tọa độ trung tâm của quân cờ
            int centerX = x + size/2;
            int centerY = y + size/2;

            // Áp dụng phép xoay 180 độ quanh tâm quân cờ
            graphics2D.rotate(Math.PI, centerX, centerY);
        }

        // Vẽ quân cờ
        graphics2D.drawImage(image, x, y, size, size, null);

        // Khôi phục transform ban đầu
        graphics2D.setTransform(oldTransform);
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
