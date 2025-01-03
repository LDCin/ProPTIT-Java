public class Cell {
    private boolean hasShip;
    private boolean isHit;

    public Cell(){
        hasShip = false;
        isHit = false;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    public boolean status(){
        return this.isHit;
    }

    public void isHit(boolean hit){
        setHit(hit);
    }

    public void setShip(boolean hasShip){
        setHasShip(hasShip);
    }

    public boolean occupied(){
        return this.hasShip;
    }
}