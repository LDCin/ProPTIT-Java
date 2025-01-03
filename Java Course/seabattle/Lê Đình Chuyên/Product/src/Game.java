public class Game {
    private Player player1;
    private Player player2;
    private int currentTurn;
    private boolean endGameCondition;
//    public MenuGame menuGame;

    public Game(String player1Name, String player2Name, int boardSize){
        this.endGameCondition = false;
        this.currentTurn = 2;
        this.player1 = new Player(player1Name, boardSize, 1);
        this.player2 = new Player(player2Name, boardSize, 2);
        this.player1.setEnemyBoard(this.player2.getOwnBoard());
        this.player2.setEnemyBoard(this.player1.getOwnBoard());
    }

    public void switchTurn(int currentTurn){
        if (currentTurn == 1) this.currentTurn = 2;
        else this.currentTurn = 1;
    }

    public void attack(Player player1, Player player2){
        player2.showEnemyBoard();
        if (player1.attack(player2)) {
            if (player2.getOwnBoard().checkSunk()) {
                System.out.println("Tàu đã bị chìm!");
            }
        }
        else attack(player1, player2);
    }

//    public void attack(Player player1, Player player2){
//        player1.showEnemyBoard();
//        if (player1.attack(player2.getOwnBoard())) {
//            if (player2.getOwnBoard().checkSunk()) {
//                System.out.println("Tàu đã bị chìm!");
//            }
//        }
//        else attack(player1, player2);
//        player1.setEnemyBoard(player2.getOwnBoard());
//    }

    public boolean checkGameOver(){
        if (player1.getOwnBoard().shipList.size() == 0){
            System.out.println(player1.getName() + " đã bị thua!");
            return true;
        }
        if (player2.getOwnBoard().shipList.size() == 0){
            System.out.println(player2.getName() + " đã bị thua!");
            return true;
        }
        return false;
    }

    public void playTurn(Player player1, Player player2){
        System.out.println("Đây là lượt của " + player1.getName());
        MenuGame.playMenu();
        int playChoice = Integer.parseInt(ScannerManager.scanner.nextLine());
        if (playChoice == 1) {
            player1.showOwnBoard();
            playTurn(player1, player2);
        }
        else if (playChoice == 2) {
            if (this.player1.getId() == player1.getId()) attack(this.player1, this.player2);
            else attack(this.player2, this.player1);
            if (checkGameOver()) this.endGameCondition = true;
        }
    }

    public void playGame(){
        MenuGame.printPhase("PreBattle Phase");
        MenuGame.clearScreen();
        System.out.print("Đây là lượt của " + player1.getName() + ". ");
        player1.placeShips();
        MenuGame.clearScreen();
//        System.out.println("\n------------------------------------------------------------------\n");
        System.out.print("Đây là lượt của " + player2.getName() + ". ");
        player2.placeShips();
        MenuGame.clearScreen();
        player1.setEnemyBoard(player2.getOwnBoard());
        player2.setEnemyBoard(player1.getOwnBoard());

        MenuGame.printPhase("Battle Phase");
        while (!this.endGameCondition) {
            switchTurn(this.currentTurn);
            if (this.currentTurn == 1) {
                playTurn(this.player1, this.player2);
                MenuGame.clearScreen();
                player1.setEnemyBoard(player2.getOwnBoard());
            }
            else{
                playTurn(this.player2, this.player1);
                MenuGame.clearScreen();
                player2.setEnemyBoard(player1.getOwnBoard());
            }
        }
        System.out.println("                                  Game Over!");
        System.out.println("Bạn có muốn chơi lại không?");
        System.out.println("1. Có\n 2. Không");
        int choiceRestart = Integer.parseInt(ScannerManager.scanner.nextLine());
        MenuGame.clearScreen();
        if (choiceRestart == 1) playGame();
        else startGame();
    }

    public void startGame(){
        MenuGame.showIntro();
        MenuGame.showMenu();
        int choice = Integer.parseInt(ScannerManager.scanner.nextLine());
        if (choice == 2){
            MenuGame.printQuitGame();
            return;
        }
        else playGame();
    }
}