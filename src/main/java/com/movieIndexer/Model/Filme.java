package com.movieIndexer.Model;

import java.time.Duration;

public class Filme {
    private String titulo;
    private String sinopse;
    private String diretor;
    private int anoLancamento;
    private Duration duracao;

    public Filme(String titulo, String sinopse, String diretor, int anoLancamento, Duration duracao) {
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.duracao = duracao;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { this.diretor = diretor; }

    public int getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }

    public Duration getDuracao() { return duracao; }
    public void setDuracao(Duration duracao) { this.duracao = duracao; }

}
