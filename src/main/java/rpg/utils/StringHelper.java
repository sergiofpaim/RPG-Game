package rpg.utils;

public class StringHelper {
    public static String centerWithPad(String text, int width, char padChar) {
        int visualLength = getVisualWidth(text);
        int totalPadding = width - visualLength;

        if (totalPadding <= 0)
            return text;

        int left = totalPadding / 2;
        int right = totalPadding - left;

        return repeatChar(padChar, left) + text + repeatChar(padChar, right);
    }

    public static String repeatChar(char ch, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(ch);
        }
        return builder.toString();
    }

    public static int getVisualWidth(String str) {
        int width = 0;
        for (int i = 0; i < str.length();) {
            int codePoint = str.codePointAt(i);
            width += isWideChar(codePoint) ? 2 : 1;
            i += Character.charCount(codePoint);
        }
        return width;
    }

    public static boolean isWideChar(int codePoint) {
        return (codePoint >= 0x1F300 && codePoint <= 0x1FAFF)
                || (codePoint >= 0x1100 && codePoint <= 0x115F)
                || (codePoint >= 0x2E80 && codePoint <= 0xA4CF);
    }
}