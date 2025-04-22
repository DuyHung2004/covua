import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Input implements MouseListener, MouseMotionListener {

    BanCo banCo;
    BotChess2 botChess2;
    public Input(BanCo banCo){
        this.banCo=banCo;
        botChess2= new BotChess2(banCo,false,3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int col=e.getX()/ banCo.size;
        int row=e.getY()/ banCo.size;

        QuanCo quanCo=banCo.getVitri(col,row);
        if(quanCo!=null){
            banCo.quancochon=quanCo;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col=e.getX()/ banCo.size;
        int row=e.getY()/ banCo.size;

        if(banCo.quancochon!=null){
            Move move= new Move(banCo,banCo.quancochon,col,row);
            if(banCo.DiChuyenHopLe(move)&&e.getX()>0&&e.getX()<8* banCo.size&&e.getY()>0&&e.getY()<8* banCo.size){
                try {
                    banCo.DiChuyen(move);
                    banCo.quancochon= null;
                    if (banCo.bot==true){
                    SwingWorker<Void, Void> aiWorker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            botChess2.thucHienNuocDi();
                            banCo.quancochon= null;
                            banCo.repaint();
                            return null;
                        }

                        @Override
                        protected void done() {
                            banCo.repaint(); // Cập nhật giao diện sau khi AI đi
                        }
                    };
                    aiWorker.execute();}
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else{
                banCo.quancochon.x=banCo.quancochon.col* banCo.size;
                banCo.quancochon.y=banCo.quancochon.row* banCo.size;
                banCo.quancochon= null;
            }
        }
        banCo.repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(banCo.quancochon!=null){
            banCo.quancochon.x=e.getX()-banCo.size/2;
            banCo.quancochon.y=e.getY()-banCo.size/2;
            banCo.repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }
}
