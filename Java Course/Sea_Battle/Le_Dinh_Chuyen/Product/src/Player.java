import static java.lang.Character.toUpperCase;

public class Player {
    private int id;
    private String name;
    private int sizeBoard;
    private Board ownBoard;
    private Board enemyBoard;

    public String getName() {
        return name;
    }

    public Board getOwnBoard() {
        return ownBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public int getId() {
        return id;
    }

    public Player(String name, int sizeBoard, int id){
        this.id = id;
        this.name = name;
        this.sizeBoard = sizeBoard;
        this.ownBoard = new Board(sizeBoard);
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = new Board(enemyBoard.getSize());
    }

    public void placeShips(){
        System.out.println(name + " place your ship!");
        int[] shipSizes = new int[]{5, 4, 3, 2, 2};
        String[] shipTypes = new String[]{"BATTLE SHIP", "DESTROYER BOAT", "SUBMARINE", "PATROL BOAT 1", "PATROL BOAT 2"};
        int stt = -1;
        for (int size : shipSizes){
            stt++;
            boolean placed = false;
            while (!placed){
                String shipType = shipTypes[stt];
                System.out.println("Place the " + shipType + " with size " + size + ":");
                this.ownBoard.showOwnBoard();
                System.out.print("Enter the starting coordinates (e.g. A1): ");
                String input = ScannerManager.scanner.nextLine();
                if (input.length() != 2) placed = false;
                else{
                    System.out.print("Place horizontally? (true/false): ");
                    boolean isHorizontal = Boolean.parseBoolean(ScannerManager.scanner.nextLine());
                    int x = input.charAt(0) - 65;
                    if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') x = toUpperCase(input.charAt(0)) - 65;
                    int y = Integer.parseInt(input.substring(1)) - 1 + 1;
                    if (isHorizontal) System.out.print("Enter the placement direction (Left/ Right): ");
                    else System.out.print("Enter the placement direction (Up/ Down): ");
                    String direction = ScannerManager.scanner.nextLine();
                    placed = this.ownBoard.placeShip(x, y, shipType, size, isHorizontal, direction);
                }
                if (!placed) {
                    System.out.println("Cannot place the ship at this position. Try again!");
                }
            }
        }
    }

    public boolean attack(Player enemy){
        System.out.print("Enter the coordinates (e.g. A1): ");
        String input = ScannerManager.scanner.nextLine();
        if (input.length() != 2) return false;
        else{
            int x = input.charAt(0) - 65;
            if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') x = toUpperCase(input.charAt(0)) - 65;
            int y = Integer.parseInt(input.substring(1)) - 1 + 1;
            return enemy.getOwnBoard().isAttacked(x, y);
        }
    }

    public void showOwnBoard(){
        ownBoard.showOwnBoard();
    }

    public void showEnemyBoard(){
        ownBoard.showEnemyBoard();
    }
}
