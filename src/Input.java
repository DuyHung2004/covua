import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener {

    BanCo banCo;
    public Input(BanCo banCo){
        this.banCo=banCo;
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
            if(banCo.DiChuyenSai(move)){
                banCo.NuocCu(move);
            } else{
                banCo.quancochon.x=banCo.quancochon.col* banCo.size;
                banCo.quancochon.y=banCo.quancochon.row* banCo.size;
            }
        }
        banCo.quancochon= null;
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
            banCo.quancochon.y=e.getX()-banCo.size/2;
            banCo.repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }
}
