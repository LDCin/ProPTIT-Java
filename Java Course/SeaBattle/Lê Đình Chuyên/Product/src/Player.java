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
        System.out.println(name + " hãy đặt tàu của bạn!");
        int[] shipSizes = new int[]{5, 4, 3, 2, 2};
        String[] shipTypes = new String[]{"BATTLE SHIP", "DESTROYER BOAT", "SUBMARINE", "PATROL BOAT 1", "PATROL BOAT 2"};
        int stt = -1;
        for (int size : shipSizes){
            stt++;
            boolean placed = false;
            while (!placed){
                String shipType = shipTypes[stt];
                System.out.println("Đặt " + shipType + " có kích thước " + size + ":");
                this.ownBoard.showOwnBoard();
                System.out.print("Nhập tọa độ bắt đầu (ví dụ: A1): ");
                String input = ScannerManager.scanner.nextLine();
                if (input.length() != 2) placed = false;
                else{
                    System.out.print("Đặt ngang? (true/false): ");
                    boolean isHorizontal = Boolean.parseBoolean(ScannerManager.scanner.nextLine());
                    int x = input.charAt(0) - 65;
                    if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') x = toUpperCase(input.charAt(0)) - 65;
                    int y = Integer.parseInt(input.substring(1)) - 1 + 1;
                    if (isHorizontal) System.out.print("Nhập hướng đặt (Left/ Right): ");
                    else System.out.print("Nhập hướng đặt (Up/ Down): ");
                    String direction = ScannerManager.scanner.nextLine();
                    placed = this.ownBoard.placeShip(x, y, shipType, size, isHorizontal, direction);
                }
                if (!placed) {
                    System.out.println("Không thể đặt tàu ở vị trí này. Thử lại!");
                }
            }
        }
    }

    public boolean attack(Player enemy){
        System.out.print("Nhập tọa độ (ví dụ: A1): ");
        String input = ScannerManager.scanner.nextLine();
        int x = input.charAt(0) - 65;
        if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') x = toUpperCase(input.charAt(0)) - 65;
        int y = Integer.parseInt(input.substring(1)) - 1 + 1;
        return enemy.getOwnBoard().isAttacked(x, y);
    }

//    public boolean attack(Board board){
//        System.out.print("Nhập tọa độ (ví dụ: A1): ");
//        String input = ScannerManager.scanner.nextLine();
//        int x = input.charAt(0) - 65;
//        if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') x = toUpperCase(input.charAt(0)) - 65;
//        int y = Integer.parseInt(input.substring(1)) - 1 + 1;
//        return board.isAttacked(x, y);
//    }

    public void showOwnBoard(){
        ownBoard.showOwnBoard();
    }

    public void showEnemyBoard(){
        ownBoard.showEnemyBoard();
    }
}
