import java.util.ArrayList;

public class Ship {
    private String type;
    private int size;
    private int hit;
    private ArrayList<Cell> positionList;

    public Ship(String type, int size, int hit){
        this.type = type;
        this.size = size;
        this.hit = hit;
        this.positionList = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public int getHit() {
        return hit;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Cell> getPositionList() {
        return this.positionList;
    }

    public void setPositionList(ArrayList<Cell> positionList) {
        this.positionList = positionList;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public void contain(Cell cell){
        this.positionList.add(cell);
    }

    public boolean isSunk(){
        if (this.hit == this.size) return true;
        else return false;
    }

    public void hitCount(){
        setHit(this.hit + 1);
    }

}
