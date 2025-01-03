import java.io.IOException;

public class MenuGame {
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
        System.out.print("\n                                                                                                     ");
        System.out.println("Press Enter to start...");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("\n                                                                                                         ");
        System.out.println("Game Starting...");
    }

    public static void showMenu(){
        System.out.print("                                                                                                             ");
        System.out.println("Menu Game");
        System.out.print("                                                                                                           ");
        System.out.println("1. Play Game");
        System.out.print("                                                                                                           ");
        System.out.println("2. Quit Game");
    }

    public static void printQuitGame(){
        boolean isRunning = true;
        boolean finalIsRunning = isRunning;
        Thread blinkingThread = new Thread(() -> {
            String baseText = "Đang thoát game";
            String[] dots = {"", ".", "..", "..."};
            int i = 0;

            while (finalIsRunning) {
                System.out.print("\r" + baseText + dots[i]);
                i = (i + 1) % dots.length;

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        blinkingThread.start();

        try {
            Thread.sleep(3700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = false;
        blinkingThread.interrupt();
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
        System.out.println("1. Xem bảng của bản thân");
        System.out.println("2. Tấn công");
        System.out.println("3. Kết thúc lượt");
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