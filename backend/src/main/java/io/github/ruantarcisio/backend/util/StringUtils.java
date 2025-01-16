package io.github.ruantarcisio.backend.util;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * Extrai a chave após um prefixo específico em uma URL.
     *
     * @param url    A URL de entrada.
     * @param prefix O prefixo que precede a chave.
     * @return A chave extraída ou null se o prefixo não for encontrado.
     */
    public static String extractKey(String url, String prefix) {
        if (url == null || prefix == null) {
            throw new IllegalArgumentException("URL e prefixo não podem ser nulos.");
        }

        int startIndex = url.indexOf(prefix);
        if (startIndex != -1) {
            return url.substring(startIndex + prefix.length());
        } else {
            return null; // Caso o prefixo não seja encontrado
        }
    }

    /**
     * Verifica se uma string corresponde completamente a um padrão de expressão regular.
     *
     * @param input A string de entrada a ser validada.
     * @param regex A expressão regular para validação.
     * @return true se a string corresponder ao padrão, false caso contrário.
     */
    public static boolean matches(String input, String regex) {
        if (input == null || regex == null) {
            throw new IllegalArgumentException("Input e regex não podem ser nulos.");
        }

        return input.matches(regex);
    }

    /**
     * Encontra todas as correspondências de um padrão de expressão regular em uma string.
     *
     * @param input A string de entrada onde o padrão será pesquisado.
     * @param regex A expressão regular para encontrar correspondências.
     * @return Uma lista contendo todas as correspondências encontradas.
     */
    public static List<String> findAllMatches(String input, String regex) {
        if (input == null || regex == null) {
            throw new IllegalArgumentException("Input e regex não podem ser nulos.");
        }

        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            matches.add(matcher.group()); // Adiciona cada correspondência à lista
        }

        return matches;
    }

    /**
     * Extrai o domínio de uma URL.
     *
     * @param url A URL de entrada.
     * @return O domínio extraído ou null se a URL for inválida.
     */
    public static String extractDomain(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL não pode ser nula.");
        }

        try {
            String regex = "https?://([^/]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);

            if (matcher.find()) {
                return matcher.group(1); // Retorna o domínio
            }
        } catch (Exception e) {
            System.err.println("Erro ao extrair domínio: " + e.getMessage());
        }

        return null; // Caso o domínio não seja encontrado
    }

    /**
     * Substitui todas as ocorrências de um padrão em uma string.
     *
     * @param input       A string de entrada.
     * @param regex       O padrão de expressão regular a ser substituído.
     * @param replacement A string de substituição.
     * @return A string resultante após as substituições.
     */
    public static String replaceAll(String input, String regex, String replacement) {
        if (input == null || regex == null || replacement == null) {
            throw new IllegalArgumentException("Input, regex e replacement não podem ser nulos.");
        }

        return input.replaceAll(regex, replacement);
    }
}
