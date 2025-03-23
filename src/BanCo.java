import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BanCo extends JPanel {
    public int size=90;
     int cols=8;
     int rows=8;
    ArrayList<QuanCo> quanCos=new ArrayList<>();
    Input input = new Input(this);
    public QuanCo quancochon;

    public BanCo() throws IOException {
        this.setPreferredSize(new Dimension(cols*size,rows*size));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        add();
    }
    public QuanCo getVitri(int col, int row){

        for(QuanCo quanCo: quanCos){
            if(quanCo.col==col&&quanCo.row==row){
                return quanCo;
            }
        }

        return null;
    }

    public void NuocCu(Move move){

        move.quanCo.col=move.newCol;
        move.quanCo.row=move.newRow;
        move.quanCo.x=move.newCol*size;
        move.quanCo.y=move.newRow*size;
        capture(move);
    }

    public void capture(Move move){
        quanCos.remove(move.quanCo2);
    }

    public boolean DiChuyenSai(Move move){
        if(sameTeam(move.quanCo,move.quanCo2)){
            return false;
        }

        return true;
    }
    public boolean sameTeam(QuanCo q1, QuanCo q2){
        if(q2==null||q1==null){
            return false;
        }
        return q1.isWhile==q2.isWhile;
    }

    public void add() throws IOException {
        quanCos.add(new Xe(this, 0, 0, false));
        quanCos.add(new Ma(this, 1, 0, false));
        quanCos.add(new Tuong(this, 2, 0, false));
        quanCos.add(new Hau(this, 3, 0, false));
        quanCos.add(new Vua(this, 4, 0, false));
        quanCos.add(new Tuong(this, 5, 0, false));
        quanCos.add(new Ma(this, 6, 0, false));
        quanCos.add(new Xe(this, 7, 0, false));

        quanCos.add(new Tot(this, 0, 1, false));
        quanCos.add(new Tot(this, 1, 1, false));
        quanCos.add(new Tot(this, 2, 1, false));
        quanCos.add(new Tot(this, 3, 1, false));
        quanCos.add(new Tot(this, 4, 1, false));
        quanCos.add(new Tot(this, 5, 1, false));
        quanCos.add(new Tot(this, 6, 1, false));
        quanCos.add(new Tot(this, 7, 1, false));

        quanCos.add(new Xe(this, 0, 7, true));
        quanCos.add(new Ma(this, 1, 7, true));
        quanCos.add(new Tuong(this, 2, 7, true));
        quanCos.add(new Hau(this, 3, 7, true));
        quanCos.add(new Vua(this, 4, 7, true));
        quanCos.add(new Tuong(this, 5, 7, true));
        quanCos.add(new Ma(this, 6, 7, true));
        quanCos.add(new Xe(this, 7, 7, true));

        quanCos.add(new Tot(this, 0, 6, true));
        quanCos.add(new Tot(this, 1, 6, true));
        quanCos.add(new Tot(this, 2, 6, true));
        quanCos.add(new Tot(this, 3, 6, true));
        quanCos.add(new Tot(this, 4, 6, true));
        quanCos.add(new Tot(this, 5, 6, true));
        quanCos.add(new Tot(this, 6, 6, true));
        quanCos.add(new Tot(this, 7, 6, true));
    }
    public void paintComponent(Graphics g){
        Graphics2D graphics2D= (Graphics2D) g;
        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                graphics2D.setColor((i+j)%2==0 ? Color.green :Color.WHITE);
                graphics2D.fillRect(i*size,j*size,size,size);
            }
        }
        for (QuanCo quanCo :quanCos){
            quanCo.paint(graphics2D);
        }
    }

}
