import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Tot extends QuanCo{
    public Tot(BanCo banCo,int col, int row,boolean isWhite) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhite=isWhite;
        this.name="Tot";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhite ?"image/tottrang.png":"image/totden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
    public boolean dichuyenhople(int col, int row){
       int colorIndex= isWhite?1:-1;
        if(this.col==col&&row==this.row-colorIndex&&banCo.getVitri(col,row)==null)
            return true;

        if(dichuyenbatbuoc&& this.col==col&&row==this.row-colorIndex*2&&banCo.getVitri(col,row)==null&&banCo.getVitri(col,row+colorIndex)==null)
            return true;

        if (col==this.col-1&&row==this.row-colorIndex&&banCo.getVitri(col,row)!=null)
            return true;
        if (col==this.col+1&&row==this.row-colorIndex&&banCo.getVitri(col,row)!=null)
            return true;
        if(banCo.getTileNum(col,row)==banCo.enPassantTile&&col==this.col-1&&row==this.row-colorIndex&&banCo.getVitri(col,row+colorIndex)!=null){
            return true;
        }
        if(banCo.getTileNum(col,row)==banCo.enPassantTile&&col==this.col+1&&row==this.row-colorIndex&&banCo.getVitri(col,row+colorIndex)!=null){
            return true;
        }
       return false;
    }
    @Override
    public Tot clone() throws CloneNotSupportedException {
        return (Tot) super.clone();
    }

}
