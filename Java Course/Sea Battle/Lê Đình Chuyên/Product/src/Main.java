public class Main {
    public static void main(String[] args) {
        System.out.print("Enter Player 1's name: ");
        String player1Name = ScannerManager.scanner.nextLine();
        System.out.print("Enter Player 2's name: ");
        String player2Name = ScannerManager.scanner.nextLine();
        Game game = new Game(player1Name, player2Name, 10);
        game.startGame();
    }
}