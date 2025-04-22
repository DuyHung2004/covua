public class CheckScanner {

    BanCo banCo;

    public CheckScanner(BanCo banCo) {
        this.banCo = banCo;
    }

    public boolean CheckedVua(Move move){
        QuanCo vua=banCo.TimVua(move.quanCo.isWhite);
        assert vua!=null;
        int vuacol= vua.col;
        int vuarow=vua.row;
        if(move.quanCo.name.equals("Vua")){
            vuacol= move.newCol;
            vuarow= move.newRow;
        }
        if(banCo.quancochon!=null && banCo.quancochon.name.equals("Vua")){
            vuacol= move.newCol;
            vuarow= move.newRow;
        }

        return XeChieu(move.newCol,move.newRow,vua,vuacol,vuarow,0,1)||
                XeChieu(move.newCol,move.newRow,vua,vuacol,vuarow,1,0)||
                XeChieu(move.newCol,move.newRow,vua,vuacol,vuarow,0,-1)||
                XeChieu(move.newCol,move.newRow,vua,vuacol,vuarow,-1,0)||
                TuongChieu(move.newCol,move.newRow,vua,vuacol,vuarow,-1,-1)||
                TuongChieu(move.newCol,move.newRow,vua,vuacol,vuarow,1,-1)||
                TuongChieu(move.newCol,move.newRow,vua,vuacol,vuarow,1,1)||
                TuongChieu(move.newCol,move.newRow,vua,vuacol,vuarow,-1,1)||

                MaChieu(move.newCol,move.newRow,vua,vuacol,vuarow)||
                TotChieu(move.newCol,move.newRow,vua,vuacol,vuarow)||
                VuaChieu(vua,vuacol,vuarow);
    }
    private boolean XeChieu(int col, int row,QuanCo vua,int vuacol, int vuarow,int colval,int rowval){
        for(int i=1;i<8;i++){
            if(vuacol+(i*colval)==col&&vuarow+(i*rowval)==row){
                break;
            }

            QuanCo quanCo=banCo.getVitri(vuacol+(i*colval),vuarow+(i*rowval));
            if(quanCo!=null && quanCo!=banCo.quancochon){
                if(!banCo.sameTeam(quanCo,vua)&& ((quanCo.name.equals("Xe")||quanCo.name.equals("Hau")))){
                    return true;
                }
                break;
            }

        }
        return false;
    }
    private boolean TuongChieu(int col, int row,QuanCo vua,int vuacol, int vuarow,int colval,int rowval){
        for(int i=1;i<8;i++){
            if(vuacol-(i*colval)==col&&vuarow-(i*rowval)==row){
                break;
            }

            QuanCo quanCo=banCo.getVitri(vuacol-(i*colval),vuarow-(i*rowval));
            if(quanCo!=null && quanCo!=banCo.quancochon){
                if(!banCo.sameTeam(quanCo,vua)&&((quanCo.name.equals("Tuong")||quanCo.name.equals("Hau")))){
                    return true;
                }
                break;
            }

        }
        return false;
    }

    private boolean MaChieu(int col, int row,QuanCo vua,int vuacol, int vuarow){
        return CheckMa(banCo.getVitri(vuacol-1,vuarow-2),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol+1,vuarow-2),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol+2,vuarow-1),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol+2,vuarow+1),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol+1,vuarow+2),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol-1,vuarow+2),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol-2,vuarow+1),vua,col,row) ||
                CheckMa(banCo.getVitri(vuacol-2,vuarow-1),vua,col,row) ;

    }
    private boolean CheckMa(QuanCo p, QuanCo k, int col, int row){
        return p!=null && !banCo.sameTeam(p,k) && p.name.equals("Ma")&& !(p.col==col&&p.row==row);

    }
    private boolean VuaChieu(QuanCo vua, int vuacol, int vuarow){
        return Checkvua(banCo.getVitri(vuacol-1,vuarow-1),vua)||
                Checkvua(banCo.getVitri(vuacol+1,vuarow-1),vua)||
                Checkvua(banCo.getVitri(vuacol,vuarow-1),vua)||
                Checkvua(banCo.getVitri(vuacol-1,vuarow),vua)||
                Checkvua(banCo.getVitri(vuacol+1,vuarow),vua)||
                Checkvua(banCo.getVitri(vuacol-1,vuarow+1),vua)||
                Checkvua(banCo.getVitri(vuacol+1,vuarow+1),vua)||
                Checkvua(banCo.getVitri(vuacol,vuarow+1),vua);
    }

    private boolean Checkvua(QuanCo p,QuanCo k){
        return p!=null && !banCo.sameTeam(p,k) && p.name.equals("Vua");
    }
    private boolean TotChieu(int col, int row,QuanCo vua,int vuacol, int vuarow){
        int colorVal= vua.isWhite?-1:1;
        return CheckTot(banCo.getVitri(vuacol+1,vuarow+colorVal),vua,col,row)||
                CheckTot(banCo.getVitri(vuacol-1,vuarow+colorVal),vua,col,row);
    }
    private boolean CheckTot(QuanCo p,QuanCo k,int col, int row){
        return p!=null&&!banCo.sameTeam(p,k)&&p.name.equals("Tot");
    }

    public boolean GameOver(QuanCo vua){
        for (QuanCo quanCo: banCo.quanCos){
            if (banCo.sameTeam(quanCo,vua)){
                 banCo.quancochon=quanCo== vua?vua:null;
                 for(int row=0;row<banCo.rows;row++){
                     for (int col=0;col<banCo.cols;col++){
                         Move move= new Move(banCo,quanCo,col,row);
                         if (banCo.DiChuyenHopLe(move)){
                             return false;
                         }
                     }

                 }
            }
        }
        return true;
    }

}
