import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Vua extends QuanCo{
    public Vua(BanCo banCo,int col, int row,boolean isWhite) throws IOException {
        super(banCo);
        this.col=col;
        this.row=row;
        this.x=col* banCo.size;
        this.y=row*banCo.size;
        this.isWhite=isWhite;
        this.name="Vua";
        this.image = ImageIO.read(ClassLoader.getSystemResourceAsStream(isWhite ?"image/vuatrang.png":"image/vuaden.png")).getScaledInstance(banCo.size,banCo.size, BufferedImage.SCALE_SMOOTH);
    }
    public boolean dichuyenhople(int col, int row){
        return Math.abs((this.col-col)*(this.row-row))==1||Math.abs(this.col-col)+Math.abs(this.row-row)==1||canCastle(col, row);
    }
    private boolean canCastle(int col, int row){
        if(this.row==row){{
            if (col == 6) {
                QuanCo xe= banCo.getVitri(7,row);
                if(xe!=null&&xe.dichuyenbatbuoc&&dichuyenbatbuoc){
                    return banCo.getVitri(5,row)==null&&
                            banCo.getVitri(6,row)==null&&
                            !banCo.checkScanner.CheckedVua(new Move(banCo,this,5,row));
                }
            } else if (col == 2) {
                QuanCo xe= banCo.getVitri(0,row);
                if(xe!=null&&xe.dichuyenbatbuoc&&dichuyenbatbuoc){
                    return banCo.getVitri(3,row)==null&&
                            banCo.getVitri(2,row)==null&&
                            banCo.getVitri(1,row)==null&&
                            !banCo.checkScanner.CheckedVua(new Move(banCo,this,3,row));
                }
            }
        }}

        return false;
    }
    @Override
    public Vua clone() throws CloneNotSupportedException {
        return (Vua) super.clone();
    }

}
