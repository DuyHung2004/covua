import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Tuong extends QuanCo{
    public Tuong(BanCo banCo,int col, int row,boolean isWhite) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhite=isWhite;
        this.name="Tuong";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhite ?"image/tuongtrang.png":"image/tuongden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
    public boolean dichuyenhople(int col, int row){
        return Math.abs(this.col-col)==Math.abs(this.row-row);
    }
    public boolean dichuyenvacham(int col, int row){
        if (this.col<col&&this.row<row){
            for (int i=1;i<Math.abs(this.col-col);i++){
                if (banCo.getVitri(this.col+i,this.row+i)!=null){
                    return true;
                }
            }
        }
        if (this.col>col&&this.row>row){
            for (int i=1;i<Math.abs(this.col-col);i++){
                if (banCo.getVitri(this.col-i,this.row-i)!=null){
                    return true;
                }
            }
        }
        if (this.col<col&&this.row>row){
            for (int i=1;i<Math.abs(this.col-col);i++){
                if (banCo.getVitri(this.col+i,this.row-i)!=null){
                    return true;
                }
            }
        }
        if (this.col>col&&this.row<row){
            for (int i=1;i<Math.abs(this.col-col);i++){
                if (banCo.getVitri(this.col-i,this.row+i)!=null){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Tuong clone() throws CloneNotSupportedException {
        return (Tuong) super.clone();
    }

}
