import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Ma extends QuanCo{
    public Ma(BanCo banCo,int col, int row, boolean isWhite) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhite=isWhite;
        this.name="Ma";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhite ? "image/matrang.png" : "image/maden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
    public boolean dichuyenhople(int col, int row){
        return Math.abs(col-this.col)*Math.abs(row-this.row)==2;
    }
    @Override
    public Ma clone() throws CloneNotSupportedException {
        return (Ma) super.clone();
    }

}
