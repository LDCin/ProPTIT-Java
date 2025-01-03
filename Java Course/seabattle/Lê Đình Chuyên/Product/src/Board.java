import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private Cell[][] grid;
    private int size;
    public ArrayList<Ship> shipList;

    public Board(int size){
        this.size = size;
        shipList = new ArrayList<>();
        grid = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }

    // Đặt tàu
    public boolean placeShip(int x, int y, String type, int size, boolean isHorizontal, String direction){
        Ship ship = new Ship(type, size, 0);
        // Check trùng
        if (isHorizontal){
            if (direction.equals("Right") || direction.equals("right")){
                if (size + y > this.size) return false;
                for (int i = y; i < size + y; i++){
                    if (grid[x][i].occupied()) return false;
                }
                for (int i = y; i < size + y; i++){
                    grid[x][i].setShip(true);
                    ship.contain(grid[x][i]);
                }
            }
            else if (direction.equals("Left") || direction.equals("left")){
                if (y - size < 0) return false;
                for (int i = y - size + 1; i <= y; i++){
                    if (grid[x][i].occupied()) return false;
                }
                for (int i = y - size + 1; i <= y; i++){
                    grid[x][i].setShip(true);
                    ship.contain(grid[x][i]);
                }
            }
            else return false;
        }
        else{
            if (direction.equals("Down") || direction.equals("down")){
                if (size + x > this.size) return false;
                for (int i = x; i < size + x; i++){
                    if (grid[i][y].occupied()) return false;
                }
                for (int i = x; i < size + x; i++){
                    grid[i][y].setShip(true);
                    ship.contain(grid[i][y]);
                }
            }
            else if (direction.equals("Up") || direction.equals("up")){
                if (x - size < 0) return false;
                for (int i = x - size + 1; i <= x; i++){
                    if (grid[x][i].occupied()) return false;
                }
                for (int i = x - size + 1; i <= x; i++){
                    grid[i][y].setShip(true);
                    ship.contain(grid[i][y]);
                }
            }
            else return false;
        }
        shipList.add(ship);
        return true;
    }

    public void showOwnBoard(){
        System.out.println("Bảng chơi của bản thân:");
        System.out.print("|\\|");
        for (int i = 0; i < this.size; i++){
            System.out.print("|" + i + "|");
        }
        System.out.print("\n");
        for (int i = 0; i < this.size; i++){
            System.out.print("|" + (char)(i + 'a') + "|");
            for (int j = 0; j < this.size; j++){
                if (grid[i][j].occupied()){
                    if (grid[i][j].status()) System.out.print("|O|");
                    else System.out.print("|S|");
                }
                else if (grid[i][j].status()) System.out.print("|X|");
                else System.out.print("|~|");
            }
            System.out.print("\n");
        }
    }

    public void showEnemyBoard(){
        System.out.println("Bảng chơi của đối thủ:");
        System.out.print("|\\|");
        for (int i = 0; i < this.size; i++){
            System.out.print("|" + i + "|");
        }
        System.out.print("\n");
        for (int i = 0; i < this.size; i++){
            System.out.print("|" + (char)(i + 'a') + "|");
            for (int j = 0; j < this.size; j++){
                if (grid[i][j].status()){
                    if (grid[i][j].occupied()){
                        System.out.print("|O|");
                    }
                    else System.out.print("|X|");
                }
                else System.out.print("|~|");
            }
            System.out.print("\n");
        }
    }

    public boolean isAttacked(int x, int y){
        if (grid[x][y].status()){
            System.out.println("Ô này đã bị bắn rồi. Vui lòng nhập lại tọa độ khác!");
            return false;
        }
        grid[x][y].isHit(true);
        if (grid[x][y].occupied()){
            System.out.println("Bạn đã bắn trúng!");
            for (Ship ship : shipList){
                if (ship.getPositionList().contains(grid[x][y])){
                    ship.hitCount();
                    break;
                }
            }
        }
        else System.out.println("Bạn đã bắn trượt!");
        return grid[x][y].status();
    }

    public boolean checkSunk(){
        for (Ship ship : shipList){
            Iterator<Ship> iterator = shipList.iterator();
            while (iterator.hasNext()) {
                ship = iterator.next();
                if (ship.isSunk()) {
                    System.out.println("Tàu " + ship.getType() + " đã bị chìm!");
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

}