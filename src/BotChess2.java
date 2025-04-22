import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotChess2 {
    private BanCo banCo;
    public boolean isWhite; // Máy chơi quân trắng hay đen
    private int doKho; // Độ khó (càng cao càng thông minh nhưng chậm hơn)

    public BotChess2(BanCo banCo, boolean isWhite, int doKho) {
        this.banCo = banCo;
        this.isWhite = isWhite;
        this.doKho = doKho;
    }

    public void thucHienNuocDi() {
        System.out.println("Bot đang tính toán nước đi...");
        System.out.println("isWhitetoMove: " + banCo.isWhitetoMove);
        System.out.println("Bot isWhite: " + isWhite);
        if (banCo.isWhitetoMove != isWhite || banCo.GameOver) {
            return;
        }

        try {
            Move bestMove = timNuocDiTotNhat();
            if (bestMove != null) {
                // Tạo move mới với bàn cờ hiện tại (không dùng bản clone)
                Move realMove = new Move(banCo, banCo.getVitri(bestMove.quanCo.col, bestMove.quanCo.row),
                        bestMove.newCol, bestMove.newRow);

                if (banCo.DiChuyenHopLe(realMove)) {
                    banCo.DiChuyen(realMove);
                    SwingUtilities.invokeLater(() -> banCo.repaint());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Move timNuocDiTotNhat() throws CloneNotSupportedException {
        List<Move> cacNuocDiHopLe = banCo.getAllLegalMoves(isWhite);

        // Chỉ lọc nước đi khi KHÔNG đang bị chiếu
        if (!banCo.isInCheck(isWhite)) {
            cacNuocDiHopLe.removeIf(move -> {
                try {
                    BanCo banCoSao = (BanCo) banCo.clone();
                    QuanCo quanCo = banCoSao.getVitri(move.quanCo.col, move.quanCo.row);
                    Move moveSao = new Move(banCoSao, quanCo, move.newCol, move.newRow);

                    if (banCoSao.DiChuyenHopLe(moveSao)) {
                        banCoSao.DiChuyen(moveSao);
                        return banCoSao.isInCheck(isWhite); // Loại bỏ nước đi gây nguy hiểm
                    }
                    return true;
                } catch (Exception e) {
                    return true;
                }
            });
        }
        if (cacNuocDiHopLe.isEmpty()) {
            return null;
        }

        Move nuocDiTotNhat = null;
        int giaTriTotNhat = Integer.MIN_VALUE;

        for (Move nuocDi : cacNuocDiHopLe) {
            try {
                BanCo banCoMoi = (BanCo) banCo.clone();
                // Sửa lại - tạo move mới với banCoMoi
                Move moveMoi = new Move(banCoMoi, banCoMoi.getVitri(nuocDi.quanCo.col, nuocDi.quanCo.row),
                        nuocDi.newCol, nuocDi.newRow);

                if (banCoMoi.DiChuyenHopLe(moveMoi)) {
                    banCoMoi.DiChuyen(moveMoi);
                    if (banCoMoi.isInCheck(isWhite)) continue;
                    int giaTriNuocDi = minimax(banCoMoi, doKho - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

                    if (giaTriNuocDi > giaTriTotNhat || nuocDiTotNhat == null) {
                        giaTriTotNhat = giaTriNuocDi;
                        nuocDiTotNhat = nuocDi;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (giaTriTotNhat == Integer.MIN_VALUE) {
            Random rand = new Random();
            return cacNuocDiHopLe.get(rand.nextInt(cacNuocDiHopLe.size()));
        }

        return nuocDiTotNhat;
    }

    private int minimax(BanCo banCo, int doSau, int alpha, int beta, boolean laNguoiChoiMax)
            throws CloneNotSupportedException {
        if (banCo.isInCheck(laNguoiChoiMax ? isWhite : !isWhite)) {
            return laNguoiChoiMax ? Integer.MIN_VALUE + 1 : Integer.MAX_VALUE - 1;
        }
        if (doSau == 0 || banCo.GameOver) {
            return danhGiaBanCo(banCo);
        }

        boolean mauHienTai = laNguoiChoiMax ? isWhite : !isWhite;
        List<Move> cacNuocDiHopLe = banCo.getAllLegalMoves(mauHienTai);

        if (laNguoiChoiMax) {
            int maxDanhGia = Integer.MIN_VALUE;
            for (Move nuocDi : cacNuocDiHopLe) {
                BanCo banCoMoi;
                try {
                    banCoMoi = (BanCo) banCo.clone();
                    banCoMoi.DiChuyen(nuocDi);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                int danhGia = minimax(banCoMoi, doSau - 1, alpha, beta, false);
                maxDanhGia = Math.max(maxDanhGia, danhGia);
                alpha = Math.max(alpha, danhGia);
                if (beta <= alpha) {
                    break; // Cắt tỉa beta
                }
            }
            return maxDanhGia;
        } else {
            int minDanhGia = Integer.MAX_VALUE;
            for (Move nuocDi : cacNuocDiHopLe) {
                BanCo banCoMoi;
                try {
                    banCoMoi = (BanCo) banCo.clone();
                    banCoMoi.DiChuyen(nuocDi);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                int danhGia = minimax(banCoMoi, doSau - 1, alpha, beta, true);
                minDanhGia = Math.min(minDanhGia, danhGia);
                beta = Math.min(beta, danhGia);
                if (beta <= alpha) {
                    break; // Cắt tỉa alpha
                }
            }
            return minDanhGia;
        }
    }

    private int danhGiaBanCo(BanCo banCo) {
        int diemSo = 0;

        // Điểm vật chất (giá trị quân cờ)
        for (QuanCo quanCo : banCo.quanCos) {
            int giaTriQuanCo = layGiaTriQuanCo(quanCo);
            diemSo += quanCo.isWhite == isWhite ? giaTriQuanCo : -giaTriQuanCo;
        }
        if (banCo.isWhitetoMove == isWhite && banCo.isInCheck(isWhite)) {
            diemSo -= 500; // Giảm điểm nhẹ hơn để bot ưu tiên thoát hiểm
        }

        // Điểm khả năng di chuyển
        int khaNangDiChuyen = banCo.getAllLegalMoves(isWhite).size() -
                banCo.getAllLegalMoves(!isWhite).size();
        diemSo += khaNangDiChuyen * 0.1;

        // An toàn của vua (phiên bản đơn giản)
        QuanCo vuaCuaToi = banCo.TimVua(isWhite);
        QuanCo vuaDoiPhuong = banCo.TimVua(!isWhite);
        if (vuaCuaToi != null) {
            diemSo -= banCo.isUnderAttack(vuaCuaToi.row, vuaCuaToi.col, isWhite) ? 50 : 0;
        }
        if (vuaDoiPhuong != null) {
            diemSo += banCo.isUnderAttack(vuaDoiPhuong.row, vuaDoiPhuong.col, !isWhite) ? 50 : 0;
        }

        // Phát hiện chiếu hết/hòa
        if (banCo.isCheckMate(!isWhite)) {
            return Integer.MAX_VALUE; // Máy thắng
        }
        if (banCo.isCheckMate(isWhite)) {
            return Integer.MIN_VALUE; // Đối thủ thắng
        }
        if (banCo.isStaleMate()) {
            return 0; // Hòa
        }


        return diemSo;
    }

    private int layGiaTriQuanCo(QuanCo quanCo) {
        switch (quanCo.name) {
            case "Vua": return 10000;
            case "Hau": return 900;
            case "Xe": return 500;
            case "Tuong": return 300;
            case "Ma": return 300;
            case "Tot": return 100;
            default: return 0;
        }
    }
}