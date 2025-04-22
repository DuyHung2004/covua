import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Hau extends QuanCo{
    public Hau(BanCo banCo,int col, int row,boolean isWhite) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhite=isWhite;
        this.name="Hau";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhite ?"image/hautrang.png":"image/hauden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
    public boolean dichuyenhople(int col, int row){
        return col==this.col||row==this.row||Math.abs(this.col-col)==Math.abs(this.row-row);
    }
    public boolean dichuyenvacham(int col, int row){
        if (this.col>col&&this.row==row){
            for(int i=this.col-1;i>col;i--){
                if (banCo.getVitri(i,this.row)!=null){
                    return true;
                }
            }
        }
        if (this.col<col&&this.row==row){
            for(int i=this.col+1;i<col;i++){
                if (banCo.getVitri(i,this.row)!=null){
                    return true;
                }
            }
        }
        if (this.row>row&&this.col==col){
            for(int i=this.row-1;i>row;i--){
                if (banCo.getVitri(this.col,i)!=null){
                    return true;
                }
            }
        }
        if (this.row<row&&this.col==col){
            for(int i=this.row+1;i<row;i++){
                if (banCo.getVitri(this.col,i)!=null){
                    return true;
                }
            }
        }
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
    public Hau clone() throws CloneNotSupportedException {
        return (Hau) super.clone();
    }
}
