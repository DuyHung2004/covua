import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Input implements MouseListener, MouseMotionListener {
    BanCo banCo;
    BotChess2 botChess2;

    public Input(BanCo banCo) {
        this.banCo = banCo;
        this.botChess2 = new BotChess2(banCo, false, 3);
    }

    // Chuyển đổi tọa độ chuột khi bàn cờ xoay
    private int[] convertMouseCoordinates(int x, int y) {
        if (banCo.isRotated()) {
            // Đảo ngược tọa độ khi bàn cờ xoay 180 độ
            return new int[]{7 - x/banCo.size, 7 - y/banCo.size};
        }
        return new int[]{x/banCo.size, y/banCo.size};
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] coords = convertMouseCoordinates(e.getX(), e.getY());
        int col = coords[0];
        int row = coords[1];

        QuanCo quanCo = banCo.getVitri(col, row);
        if (quanCo != null && quanCo.isWhite == banCo.isWhitetoMove) {
            banCo.quancochon = quanCo;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Kiểm tra null trước khi xử lý
        if (banCo.quancochon == null) return;

        int[] coords = convertMouseCoordinates(e.getX(), e.getY());
        int col = coords[0];
        int row = coords[1];

        try {
            Move move = new Move(banCo, banCo.quancochon, col, row);

            // Lưu trữ tạm thời quân cờ được chọn
            QuanCo selectedPiece = banCo.quancochon;

            if (banCo.DiChuyenHopLe(move)) {
                banCo.DiChuyen(move);
                selectedPiece.x = move.newCol * banCo.size;
                selectedPiece.y = move.newRow * banCo.size;

                if (banCo.bot) {
                    SwingWorker<Void, Void> aiWorker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            botChess2.thucHienNuocDi();
                            return null;
                        }

                        @Override
                        protected void done() {
                            banCo.quancochon = null;
                            banCo.repaint();
                        }
                    };
                    aiWorker.execute();
                }
            } else {
                selectedPiece.x = selectedPiece.col * banCo.size;
                selectedPiece.y = selectedPiece.row * banCo.size;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            banCo.quancochon = null;
            banCo.repaint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (banCo.quancochon == null) return;

        try {
            if (banCo.isRotated()) {
                banCo.quancochon.x = banCo.getWidth() - e.getX() - banCo.size/2;
                banCo.quancochon.y = banCo.getHeight() - e.getY() - banCo.size/2;
            } else {
                banCo.quancochon.x = e.getX() - banCo.size/2;
                banCo.quancochon.y = e.getY() - banCo.size/2;
            }
            banCo.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Các phương thức khác giữ nguyên
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}