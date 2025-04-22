import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BanCo extends JPanel implements Cloneable {
    public int size=90;
     int cols=8;
     int rows=8;
    private boolean rotateBoard = false;
    ArrayList<QuanCo> quanCos=new ArrayList<>();
    Input input = new Input(this);
    public QuanCo quancochon;
    public int enPassantTile=-1;
    public boolean isWhitetoMove=true;
    public boolean GameOver=false;
    private JButton backButton;
    public boolean bot;
    CheckScanner checkScanner= new CheckScanner(this);
    public ArrayList<QuanCo> getAllPieces() {
        return new ArrayList<>(quanCos);
    }
    public ArrayList<QuanCo> getAllPieces1(boolean isWhitetoMove) {
        ArrayList<QuanCo> quanCos1=new ArrayList<>();
        for (QuanCo quanCo:quanCos){
            if (quanCo.isWhite==isWhitetoMove){
                quanCos1.add(quanCo);
            }
        }
        return quanCos1;
    }
    public ArrayList<Move> getAllLegalMoves(boolean isWhiteTurn) {
        ArrayList<Move> allLegalMoves = new ArrayList<>();

        for (QuanCo quanCo : quanCos) {
            if (quanCo.isWhite == isWhiteTurn) {
                for (int i=0;i<8;i++){
                    for (int j=0;j<8;j++){
                        Move move=new Move(this,quanCo,i,j);
                        if(DiChuyenHopLe(move)){
                            allLegalMoves.add(move);
                        }
                    }
                }
            }
        }
        return allLegalMoves;
    }
    public ArrayList<Move> getMoveForQuanCo(QuanCo quanCo){
        ArrayList<Move> allLegalMoves = new ArrayList<>();
            for (int i=0;i<8;i++){
                for (int j=0;j<8;j++){
                    Move move=new Move(this,quanCo,i,j);
                    if(DiChuyenHopLe(move)){
                        allLegalMoves.add(move);
                    }
                }
        }
            return allLegalMoves;
    }


    public BanCo(JFrame frame,boolean bot) throws IOException {
        this.setLayout(new BorderLayout());
        this.bot=bot;
        this.rotateBoard = !bot;
        backButton = new JButton("Trở Về Trang Chủ");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new TrangChu(frame));
            frame.revalidate();
            frame.repaint();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);

        JPanel chessBoardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                Graphics2D graphics2D= (Graphics2D) g;
                if (rotateBoard && !isWhitetoMove) {
                    graphics2D.rotate(Math.PI, getWidth()/2, getHeight()/2);
                }
                for(int i=0;i<rows;i++){
                    for (int j=0;j<cols;j++){
                        graphics2D.setColor((i+j)%2==0 ? Color.green :Color.WHITE);
                        graphics2D.fillRect(i*size,j*size,size,size);
                    }
                }
                if (quancochon!=null)
                    for (int i=0;i<rows;i++){
                        for (int j=0;j<cols;j++){
                            if (DiChuyenHopLe(new Move(BanCo.this,quancochon,j,i))){
                                graphics2D.setColor(Color.cyan);
                                graphics2D.fillRect(j*size,i*size,size,size);
                            }
                        }
                    }


                for (QuanCo quanCo :quanCos){
                    quanCo.paint(graphics2D);
                }
            }
        };
        chessBoardPanel.setPreferredSize(new Dimension(cols * size, rows * size));
        chessBoardPanel.addMouseListener(input);
        chessBoardPanel.addMouseMotionListener(input);

        // 4. Thêm các panel vào layout chính
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(chessBoardPanel, BorderLayout.CENTER);

        // 5. Khởi tạo quân cờ
        add();
    }
    public void setRotateBoard(boolean rotate) {
        this.rotateBoard = rotate;
        repaint();
    }

    public BanCo(BanCo oldBoard) throws CloneNotSupportedException {
        this.size = oldBoard.size;
        this.cols = oldBoard.cols;
        this.rows = oldBoard.rows;
        this.enPassantTile = oldBoard.enPassantTile;
        this.isWhitetoMove = oldBoard.isWhitetoMove;
        this.GameOver = oldBoard.GameOver;

        this.quanCos = new ArrayList<>();
        for (QuanCo q : oldBoard.quanCos) {
            QuanCo cloned = (QuanCo) q.clone();
            cloned.banCo = this;
            this.quanCos.add(cloned);
        }
    }
    public QuanCo getVitri(int col, int row){

        for(QuanCo quanCo: quanCos){
            if(quanCo.col==col&&quanCo.row==row){
                return quanCo;
            }
        }

        return null;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        BanCo cloned = (BanCo) super.clone();
        cloned.quanCos = new ArrayList<>();

        System.out.println("Original pieces: " + this.quanCos.size());
        for (QuanCo q : this.quanCos) {
            QuanCo qClone = (QuanCo) q.clone();
            qClone.banCo = cloned;
            cloned.quanCos.add(qClone);
        }
        System.out.println("Cloned pieces: " + cloned.quanCos.size());

        return cloned;
    }

    public void DiChuyen(Move move) throws IOException {


        if(move.quanCo.name.equals("Tot")){
            DiChuyenTot(move);
        }else if(move.quanCo.name.equals("Vua"))
        {
            DiChuyenVua(move);
        }
            move.quanCo.col=move.newCol;
            move.quanCo.row=move.newRow;
            move.quanCo.x=move.newCol*size;
            move.quanCo.y=move.newRow*size;
            move.quanCo.dichuyenbatbuoc=false;
            capture(move.quanCo2);

            this.isWhitetoMove=!this.isWhitetoMove;
        if (!bot) {
            setRotateBoard(!isWhitetoMove);
        }
            repaint();
            updateGameState();

    }
    public void undoMove(Move move) {
        if (move == null) return;

        move.quanCo.col = move.oldCol;
        move.quanCo.row = move.oldRow;
        move.quanCo.x=move.oldCol*size;
        move.quanCo.y=move.oldRow*size;
        if (move.quanCo2 != null && !quanCos.contains(move.quanCo2)) {
            quanCos.add(move.quanCo2);
        }
        isWhitetoMove = !isWhitetoMove;

    }


    private void updateGameState() {
        QuanCo vua = TimVua(isWhitetoMove);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (checkScanner.GameOver(vua)) {
            if (checkScanner.CheckedVua(new Move(this, vua, vua.col, vua.row))) {
                String winner = isWhitetoMove ? "Black" : "White";
                showGameOverDialog(parentFrame, winner);
            } else if (isStaleMate()) {
                showGameOverDialog(parentFrame, "Hòa do hết nước đi");
            }
        } else if (KiemTraSl(true) && KiemTraSl(false)) {
            showGameOverDialog(parentFrame, "Hòa do không đủ quân chiếu hết");
        }
    }

    private void showGameOverDialog(JFrame parent, String result) {
        GameOverDialog dialog = new GameOverDialog(parent, result,
                e -> resetGame(), // Xử lý chơi lại
                e -> returnToMainMenu(parent) // Xử lý về trang chủ
        );
        dialog.setVisible(true);
    }

    private void resetGame() {
        quanCos.clear();
        isWhitetoMove = true;
        GameOver = false;
        quancochon = null;
        enPassantTile = -1;

        try {
            add(); // Khởi tạo lại quân cờ
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private void returnToMainMenu(JFrame parent) {
        parent.getContentPane().removeAll();
        parent.add(new TrangChu(parent));
        parent.revalidate();
        parent.repaint();
    }
    private boolean KiemTraSl(boolean isWhite) {
        ArrayList<QuanCo> pieces = getAllPieces1(isWhite);

        // Nếu chỉ còn Vua
        if (pieces.size() == 1) return true;

        // Nếu còn Vua + 1 Mã hoặc 1 Tượng
        if (pieces.size() == 2) {
            for (QuanCo p : pieces) {
                if (p.name.equals("Ma") || p.name.equals("Tuong")) {
                    return true;
                }
            }
        }

        // Nếu còn Vua + 2 Mã (trường hợp hiếm có thể hòa)
        if (pieces.size() == 3) {
            int maCount = 0;
            for (QuanCo p : pieces) {
                if (p.name.equals("Ma")) maCount++;
            }
            if (maCount == 2) return true;
        }

        return false;
    }

    private void DiChuyenVua(Move move) {
        if(Math.abs(move.quanCo.col-move.newCol)==2){
            QuanCo xe;
            if(move.quanCo.col<move.newCol){
                xe = getVitri(7, move.quanCo.row);
            } else {
                xe = getVitri(0, move.quanCo.row);
            }


            if(xe == null || !xe.name.equals("Xe") || xe.dichuyenbatbuoc) {
                return;
            }

            if(move.quanCo.col<move.newCol){
                xe.col=5;
            } else {
                xe.col=3;
            }
            xe.x = xe.col*size;
        }
    }

    public void DiChuyenTot(Move move) throws IOException {
        int colerIndex=move.quanCo.isWhite?1:-1;
        if(getTileNum(move.newCol,move.newRow)==enPassantTile){
            move.quanCo2=getVitri(move.newCol,move.newRow+colerIndex);
        }
        if(Math.abs(move.quanCo.row-move.newRow)==2){
            enPassantTile=getTileNum(move.newCol,move.newRow+colerIndex);
        }else {
            enPassantTile=-1;
        }

        colerIndex=move.quanCo.isWhite?0:7;
        if (move.newRow==colerIndex){
            phongTot(move);
        }


    }

    private void phongTot(Move move) throws IOException {
        quanCos.add(new Hau(this,move.newCol,move.newRow,move.quanCo.isWhite));
        capture(move.quanCo);
    }

    public void capture(QuanCo quanCo){
        quanCos.remove(quanCo);
    }

    public boolean DiChuyenHopLe(Move move) {
        if (GameOver) return false;
        if (move.quanCo.isWhite != isWhitetoMove) return false;
        if (sameTeam(move.quanCo, move.quanCo2)) return false;
        if (!move.quanCo.dichuyenhople(move.newCol, move.newRow)) return false;
        if (move.quanCo.dichuyenvacham(move.newCol, move.newRow)) return false;


        if (move.quanCo.name.equals("Vua") && Math.abs(move.quanCo.col - move.newCol) == 2) {

            if (move.quanCo.dichuyenbatbuoc) return false;
            if (checkScanner.CheckedVua(new Move(this, move.quanCo, move.quanCo.col, move.quanCo.row))) {
                return false;
            }

            int huong = move.newCol > move.quanCo.col ? 1 : -1;
            for (int c = move.quanCo.col + huong; c != (huong == 1 ? 7 : 0); c += huong) {
                if (getVitri(c, move.quanCo.row) != null) return false;
                if (checkScanner.CheckedVua(new Move(this, move.quanCo, c, move.quanCo.row))) {
                    return false;
                }
            }

            QuanCo xe = getVitri(huong == 1 ? 7 : 0, move.quanCo.row);
            if (xe == null || !xe.name.equals("Xe") || xe.dichuyenbatbuoc) {
                return false;
            }
        }
        return !checkScanner.CheckedVua(move);
    }
    public boolean sameTeam(QuanCo q1, QuanCo q2){
        if(q2==null||q1==null){
            return false;
        }
        return q1.isWhite==q2.isWhite;
    }

    public QuanCo TimVua(boolean isWhite){
        for (QuanCo quanCo: quanCos){
            if(isWhite==quanCo.isWhite && quanCo.name.equals("Vua")){
                return quanCo;
            }
        }
        return null;
    }
    public boolean isInCheck(boolean isWhite) {
        QuanCo vua = TimVua(isWhite);
        ArrayList<Move> nuocDiDoiPhuong = getAllLegalMoves(!isWhite);

        for (Move move : nuocDiDoiPhuong) {
            if (move.newRow == vua.row && move.newCol == vua.col) {
                return true;
            }
        }
        return false; // Vua an toàn
    }
    public boolean isCheckMate(boolean isWhite) {
        QuanCo vua = TimVua(isWhite);
        if (vua == null || !isInCheck(isWhite)) {
            return false;
        }
        ArrayList<Move> cacNuocDiHopLe = getAllLegalMoves(isWhite);

        for (Move move : cacNuocDiHopLe) {
            try {
                BanCo banCoSao = makeMove(move);
                if (banCoSao != null && !banCoSao.isInCheck(isWhite)) {
                    return false;
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    public boolean isStaleMate() {
        // Không phải là hòa nếu đang bị chiếu
        if (isInCheck(isWhitetoMove)) {
            return false;
        }

        // Kiểm tra xem có nước đi hợp lệ nào không
        ArrayList<Move> legalMoves = getAllLegalMoves(isWhitetoMove);
        return legalMoves.isEmpty();
    }

    public int getTileNum(int col,int row){
        return row*rows+col;
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
        if (quancochon!=null)
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                if (DiChuyenHopLe(new Move(this,quancochon,j,i))){
                    graphics2D.setColor(Color.cyan);
                    graphics2D.fillRect(j*size,i*size,size,size);
                }
            }
        }


        for (QuanCo quanCo :quanCos){
            quanCo.paint(graphics2D);
        }
    }
    public boolean isRotated() {
        return !isWhitetoMove && !bot; // Xoay khi là lượt đối phương và chơi với bạn
    }
    public BanCo makeMove(Move move) throws CloneNotSupportedException {
        BanCo newBoard = new BanCo(this);
        if(DiChuyenHopLe(move)){
            try {
                this.DiChuyen(move);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else{
            return null;
        }
        return newBoard;
    }
    public boolean isUnderAttack(int row, int col, boolean isWhite) {
        for (Move move : getAllLegalMoves(!isWhite)) {
            if (move.newRow == row && move.newCol == col) {
                return true;
            }
        }
        return false;
    }



}
