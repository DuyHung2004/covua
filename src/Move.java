public class Move {
    int oldCol;
    int oldRow;
    int newCol;
    int newRow;

    QuanCo quanCo;
    QuanCo quanCo2;
    public Move(BanCo banCo,QuanCo quanCo,int newCol, int newRow){
        this.oldCol=quanCo.col;
        this.oldRow=quanCo.row;
        this.newCol=newCol;
        this.newRow=newRow;
        this.quanCo=quanCo;
        this.quanCo2= banCo.getVitri(newCol,newRow);
    }

    @Override
    public String toString() {
        return "Move{" + quanCo.name+
                "oldCol=" + oldCol +
                ", oldRow=" + oldRow +
                ", newCol=" + newCol +
                ", newRow=" + newRow +
                '}';
    }
}
