import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tot extends QuanCo{
    public Tot(BanCo banCo,int col, int row,boolean isWhile) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhile=isWhile;
        this.name="Tot";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhile ?"image/tottrang.png":"image/totden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
}
