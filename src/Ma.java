import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ma extends QuanCo{
    public Ma(BanCo banCo,int col, int row, boolean isWhile) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhile=isWhile;
        this.name="Ma";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhile ? "image/matrang.png" : "image/maden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
}
