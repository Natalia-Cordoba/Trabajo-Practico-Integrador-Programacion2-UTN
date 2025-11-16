package main;

import java.util.List;
import java.util.function.Function;
import java.util.Objects;

/**
 * Utility para imprimir tablas ASCII reutilizables.
 * Uso básico:
 *   TablePrinter.printTable(items, "MASCOTAS", headers, extractors);
 * donde `extractors` es una lista de funciones que, dada una fila T, devuelven el valor de cada columna como String para poder ser impresa en la tabla.
 */
public final class TablePrinter {

    private TablePrinter() { }

    public static <T> void printTable(List<T> items, String title, String[] headers, List<Function<T, String>> extractors) {
        Objects.requireNonNull(headers, "headers no puede ser null");
        Objects.requireNonNull(extractors, "extractors no puede ser null");
        if (headers.length != extractors.size()) {
            throw new IllegalArgumentException("El número de headers debe coincidir con el número de extractores");
        }

        int cols = headers.length;
        int total = items == null ? 0 : items.size();
        if (title == null) title = "table";
        System.out.println("Imprimiendo " + total + " registros de la tabla " + title);

        if (total == 0) {
            return;
        }

        // calcular anchos
        int[] widths = new int[cols];
        for (int i = 0; i < cols; i++) widths[i] = headers[i] == null ? 0 : headers[i].length();

        for (T item : items) {
            for (int c = 0; c < cols; c++) {
                String cell = safeApply(extractors.get(c), item);
                widths[c] = Math.max(widths[c], cell.length());
            }
        }

        String sep = buildSep(widths);
        System.out.println(sep);
        System.out.println(formatRow(headers, widths));
        System.out.println(sep);

        for (T item : items) {
            String[] row = new String[cols];
            for (int c = 0; c < cols; c++) row[c] = safeApply(extractors.get(c), item);
            System.out.println(formatRow(row, widths));
            System.out.println(sep);
        }
    }

    private static String safeApply(Function<?, String> fn, Object item) {
        if (fn == null) return "";
        try {
            @SuppressWarnings("unchecked")
            Function<Object, String> f = (Function<Object, String>) fn;
            String r = f.apply(item);
            return r == null ? "" : r;
        } catch (Exception e) {
            return "";
        }
    }

    private static String buildSep(int[] widths) {
        StringBuilder sb = new StringBuilder();
        sb.append('+');
        for (int w : widths) {
            sb.append("-".repeat(w + 2));
            sb.append('+');
        }
        return sb.toString();
    }

    private static String formatRow(String[] cols, int[] widths) {
        StringBuilder sb = new StringBuilder();
        sb.append('|');
        for (int i = 0; i < cols.length; i++) {
            String cell = cols[i] == null ? "" : cols[i];
            sb.append(' ');
            sb.append(padRight(cell, widths[i]));
            sb.append(" |");
        }
        return sb.toString();
    }

    private static String padRight(String s, int width) {
        if (s == null) s = "";
        if (s.length() >= width) return s;
        return String.format("%-" + width + "s", s);
    }

}
