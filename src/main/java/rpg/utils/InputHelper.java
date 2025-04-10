package rpg.utils;

import java.io.IOException;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class InputHelper {

    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    public static char readKey() {
        return IS_WINDOWS ? readKeyWindows() : readKeyUnix();
    }

    public interface Msvcrt extends Library {
        Msvcrt INSTANCE = Native.load("msvcrt", Msvcrt.class);

        int _getch();
    }

    private static char readKeyWindows() {
        return Character.toUpperCase((char) Msvcrt.INSTANCE._getch());
    }

    // Unix/Linux/macOS
    private static char readKeyUnix() {
        try {
            new ProcessBuilder("/bin/sh", "-c", "stty raw -echo < /dev/tty").inheritIO().start().waitFor();

            char c = (char) System.in.read();

            new ProcessBuilder("/bin/sh", "-c", "stty -raw echo < /dev/tty").inheritIO().start().waitFor();

            return Character.toUpperCase(c);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao ler tecla no Unix", e);
        }
    }
}
