package com.example.transportsystem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Converts .properties files to ASCII with \\uXXXX escapes.
 *
 * This is the most reliable way to avoid "???" on different OS/JVM encodings.
 */
public final class PropertiesUnicodeEscaper {

    private PropertiesUnicodeEscaper() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: PropertiesUnicodeEscaper <file> [<charset>] [--in-place]");
            System.err.println("Example: PropertiesUnicodeEscaper src/main/resources/messages_kk.properties UTF-8 --in-place");
            System.exit(2);
        }

        Path file = Path.of(args[0]);
        Charset charset = StandardCharsets.UTF_8;
        boolean inPlace = false;

        for (int i = 1; i < args.length; i++) {
            String a = args[i];
            if ("--in-place".equalsIgnoreCase(a)) {
                inPlace = true;
            } else {
                charset = Charset.forName(a);
            }
        }

        String converted = convert(file, charset);
        if (inPlace) {
            Files.writeString(file, converted, StandardCharsets.ISO_8859_1);
        } else {
            System.out.print(converted);
        }
    }

    public static String convert(Path file, Charset inputCharset) throws IOException {
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, inputCharset))) {

            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(convertLine(line)).append('\n');
            }
            return out.toString();
        }
    }

    // Keeps comments/empty lines. Escapes only the value part (and key if it contains non-ASCII).
    private static String convertLine(String line) {
        if (line.isEmpty()) return line;
        char first = line.charAt(0);
        if (first == '#' || first == '!') return line;

        int sep = findSeparator(line);
        if (sep < 0) {
            return escapeNonAscii(line);
        }

        String keyPart = line.substring(0, sep);

        int valueStart = sep;
        while (valueStart < line.length()) {
            char c = line.charAt(valueStart);
            if (c == '=' || c == ':') {
                valueStart++;
                break;
            }
            if (!Character.isWhitespace(c)) {
                break;
            }
            valueStart++;
        }

        // preserve original separator spacing
        String sepPart = line.substring(sep, valueStart);
        String valuePart = line.substring(valueStart);

        return escapeNonAscii(keyPart) + sepPart + escapeNonAsciiPreserveEscapes(valuePart);
    }

    private static int findSeparator(String line) {
        boolean escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '=' || c == ':') {
                return i;
            }
        }
        // whitespace separator
        escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (Character.isWhitespace(c)) {
                return i;
            }
        }
        return -1;
    }

    private static String escapeNonAscii(String s) {
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c <= 0x7E) {
                b.append(c);
            } else {
                b.append(String.format("\\\\u%04X", (int) c));
            }
        }
        return b.toString();
    }

    // Escapes non-ascii but doesn't double-escape existing \\uXXXX sequences.
    private static String escapeNonAsciiPreserveEscapes(String s) {
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 5 < s.length() && (s.charAt(i + 1) == 'u' || s.charAt(i + 1) == 'U')) {
                // keep \\uXXXX as-is
                b.append('\\').append(s.charAt(i + 1));
                for (int j = 0; j < 4; j++) {
                    b.append(s.charAt(i + 2 + j));
                }
                i += 5;
                continue;
            }
            if (c <= 0x7E) {
                b.append(c);
            } else {
                b.append(String.format("\\\\u%04X", (int) c));
            }
        }
        return b.toString();
    }
}
