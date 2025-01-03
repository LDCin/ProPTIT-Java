public class Main {
    public static void main(String[] args) {
        System.out.print("Nhập vào tên người chơi 1: ");
        String player1Name = ScannerManager.scanner.nextLine();
        System.out.print("Nhập vào tên người chơi 2: ");
        String player2Name = ScannerManager.scanner.nextLine();
        Game game = new Game(player1Name, player2Name, 10);
        game.startGame();
    }
}
/*
A1 true Right: a1 a2 a3 a4 a5
A0 false Down: a0 b0 c0 d0
I5 true Left: i3 i4 i5
E3 true Left: e2 e3
H0 true Right: h0 h1

B0 false Down: b0 c0 d0 e0 f0
F7 true Left: f4 f5 f6 f7
H8 false Down: h8 i8 j8
I3 true Right: i3 i4
D9 true Left: d8 d9
 */