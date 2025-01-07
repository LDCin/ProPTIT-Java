import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private Cell[][] grid;
    private int size;
    public ArrayList<Ship> shipList;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

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

    public void showOwnBoard() {
        System.out.println("Your Board:");
        System.out.print("   ");
        System.out.println("|  0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 |");
        for (int i = 0; i < this.size; i++) {
            System.out.printf("%2s ", (char) (i + 'a'));
            for (int j = 0; j < this.size; j++) {
                if (grid[i][j].occupied()) {
                    if (grid[i][j].status()) {
                        System.out.print("| \u274C "); // ❌
                    } else {
                        System.out.print("| \uD83D\uDEA2 "); // 🚢
                    }
                } else if (grid[i][j].status()) {
                    System.out.print("| \uD83D\uDCA5 "); // 💥
                } else {
                    System.out.print("| \uD83C\uDF0A "); // 🌊
                }
            }
            System.out.println("|"); // Đóng hàng
        }
    }


    public void showEnemyBoard(){
        System.out.println("Enemy's Board:");
        System.out.print("   ");
        System.out.println("|  0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 |");
        for (int i = 0; i < this.size; i++) {
            System.out.printf("%2s ", (char) (i + 'a'));
            for (int j = 0; j < this.size; j++) {
                if (grid[i][j].status()) {
                    if (grid[i][j].occupied()) System.out.print("| \u274C "); // ❌
                    else System.out.print("| \uD83D\uDCA5 "); // 💥
                } else {
                    System.out.print("| \uD83C\uDF0A "); // 🌊
                }
            }
            System.out.println("|");
        }
    }

    public boolean isAttacked(int x, int y){
        if (grid[x][y].status()){
            System.out.println("This cell has already been hit. Please enter a different coordinate!");
            return false;
        }
        grid[x][y].isHit(true);
        if (grid[x][y].occupied()){
            System.out.println("Result: Hit!");
            for (Ship ship : shipList){
                if (ship.getPositionList().contains(grid[x][y])){
                    ship.hitCount();
                    break;
                }
            }
        }
        else System.out.println("Result: Miss!");
        return grid[x][y].status();
    }

    public boolean checkSunk(){
        for (Ship ship : shipList){
            Iterator<Ship> iterator = shipList.iterator();
            while (iterator.hasNext()) {
                ship = iterator.next();
                if (ship.isSunk()) {
                    System.out.println(ship.getType() + " is sunk!");
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

}
