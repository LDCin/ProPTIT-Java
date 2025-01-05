import java.io.IOException;

public class MenuGame {
    public static void printTextWithDot(String text){
        try {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i <= 3; i++) {
                    System.out.print("\033[2K\r                                                                                                         " + text + ".".repeat(i));
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Error!");
        }
    }

    public static void showIntro() {

        String[] introText = {
                "                                                                        ██████╗  █████╗ ████████╗████████╗██╗     ███████╗    ███████╗███████╗ █████╗ ",
                "                                                                        ██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝    ██╔════╝██╔════╝██╔══██╗",
                "                                                                        ██████╔╝███████║   ██║      ██║   ██║     █████╗      ███████╗█████╗  ███████║",
                "                                                                        ██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝      ╚════██║██╔══╝  ██╔══██║",
                "                                                                        ██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗    ███████║███████╗██║  ██║",
                "                                                                        ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝    ╚══════╝╚══════╝╚═╝  ╚═╝",
                "                                                                               "
        };

        for (String line : introText) {
            System.out.println(line);
        }

        try {
            Thread.sleep(1000); // Chờ 1 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printTextWithDot("Game Starting");
        System.out.print("\n");
    }

    public static void printQuitGame(){
        printTextWithDot("Exiting Game");
    }

    public static void showMenu(){
        System.out.print("                                                                                                             ");
        System.out.println("Menu Game");
        System.out.print("                                                                                                           ");
        System.out.println("1. Play Game");
        System.out.print("                                                                                                           ");
        System.out.println("2. Quit Game");
    }

    public static String getPhase(String phaseName) {
        switch (phaseName) {
            case "PreBattle Phase":
                return
                                "                                                       ██████  ██████  ███████ ██████   █████  ████████ ████████ ██      ███████     ██████  ██   ██  █████  ███████ ███████ \n" +
                                "                                                       ██   ██ ██   ██ ██      ██   ██ ██   ██    ██       ██    ██      ██          ██   ██ ██   ██ ██   ██ ██      ██      \n" +
                                "                                                       ██████  ██████  █████   ██████  ███████    ██       ██    ██      █████       ██████  ███████ ███████ ███████ █████   \n" +
                                "                                                       ██      ██   ██ ██      ██   ██ ██   ██    ██       ██    ██      ██          ██      ██   ██ ██   ██      ██ ██      \n" +
                                "                                                       ██      ██   ██ ███████ ██████  ██   ██    ██       ██    ███████ ███████     ██      ██   ██ ██   ██ ███████ ███████ \n";

            case "Battle Phase":
                return
                                "                                                       ██████   █████  ████████ ████████ ██      ███████     ██████  ██   ██  █████  ███████ ███████ \n" +
                                "                                                       ██   ██ ██   ██    ██       ██    ██      ██          ██   ██ ██   ██ ██   ██ ██      ██      \n" +
                                "                                                       ██████  ███████    ██       ██    ██      █████       ██████  ███████ ███████ ███████ █████   \n" +
                                "                                                       ██   ██ ██   ██    ██       ██    ██      ██          ██      ██   ██ ██   ██      ██ ██      \n" +
                                "                                                       ██████  ██   ██    ██       ██    ███████ ███████     ██      ██   ██ ██   ██ ███████ ███████ \n";
            default:
                return "Unknown phase.";
        }
    }

    public static void printPhase(String phase){
        for (int i = 0; i < 3; i++) System.out.print("\n");
        System.out.println(getPhase(phase));
        System.out.print("                                                       ");
        if (phase.equals("PreBattle Phase")) System.out.print("---------------------------------------------------------------------------------------------------------------------");
        else if (phase.equals("Battle Phase")) System.out.print("---------------------------------------------------------------------------------------------");
        System.out.print("\n\n");
    }

    public void switchPhase(String phaseName){
        getPhase(phaseName);
    }

    public static void playMenu(){
        System.out.println("1. View your own board");
        System.out.println("2. Attack");
        System.out.println("3. End your turn");
    }

    public static void clearScreen(){

        try {
            Thread.sleep(1500); // Dừng 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
