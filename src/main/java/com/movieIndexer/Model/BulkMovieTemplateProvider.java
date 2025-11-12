package com.movieIndexer.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Responsável por gerar um template determinístico de filmes fictícios,
 * utilizado para popular rapidamente o arquivo {@code filmes.json} em cenários
 * de testes de desempenho ou demonstração.
 */
public final class BulkMovieTemplateProvider {

    private static final String[] TITULOS_BASE = {
            "Aurora", "Nebula", "Horizonte", "Pioneiros", "Ecos", "Fragmentos",
            "Odisseia", "Resiliencia", "Vanguarda", "Luz do Norte", "Maré Alta",
            "Ventania", "Alvorada", "Constelação", "Interludio", "Labirinto",
            "Monolito", "Auriflama", "Estação Zero", "Transeunte"
    };

    private static final String[] DIRETORES_BASE = {
            "A. Silva", "B. Santos", "C. Oliveira", "D. Almeida", "E. Ferreira",
            "F. Castro", "G. Menezes", "H. Martins", "I. Gomes", "J. Braga",
            "K. Figueiredo", "L. Azevedo", "M. Moura", "N. Lima", "O. Vasconcelos"
    };

    private static final String[] SINOPSES_BASE = {
            "Uma jornada inesperada revela segredos de familia.",
            "Heróis improváveis enfrentam dilemas morais em um futuro distópico.",
            "Um romance histórico que desafia tradições centenárias.",
            "A investigação de um crime conecta vidas distantes.",
            "Exploradores espaciais buscam uma nova esperança para a Terra.",
            "Uma cidade costeira enfrenta as forças da natureza.",
            "Documentário que celebra a resistência cultural de um povo.",
            "Mistério psicológico sobre memória e identidade."
    };

    private static final int ANO_MIN = 1950;
    private static final int ANO_MAX = 2024;
    private static final int DURACAO_MIN = 80;
    private static final int DURACAO_MAX = 190;

    private BulkMovieTemplateProvider() {}

    public static List<Filme> gerarFilmes(int quantidade) {
        if (quantidade <= 0) return List.of();

        List<Filme> filmes = new ArrayList<>(quantidade);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 1; i <= quantidade; i++) {
            Filme filme = new Filme();
            filme.setId(i);
            filme.setTitulo(criarTitulo(i));
            filme.setDiretor(DIRETORES_BASE[i % DIRETORES_BASE.length]);
            filme.setSinopse(SINOPSES_BASE[i % SINOPSES_BASE.length]);
            filme.setAnoLancamento(ANO_MIN + (i % (ANO_MAX - ANO_MIN + 1)));
            filme.setDuracaoEmMinutos(random.nextInt(DURACAO_MIN, DURACAO_MAX + 1));
            filmes.add(filme);
        }
        return filmes;
    }

    private static String criarTitulo(int indice) {
        String base = TITULOS_BASE[indice % TITULOS_BASE.length];
        int volume = (indice / TITULOS_BASE.length) + 1;
        return base + " " + volume;
    }
}

