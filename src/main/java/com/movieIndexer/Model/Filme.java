package com.movieIndexer.Model;

public class Filme {
    private int id;
    private String titulo;
    private String sinopse;
    private String diretor;
    private int anoLancamento;
    private int duracaoEmMinutos;

    public Filme() {}

    public Filme(int id, String titulo, String sinopse, String diretor, int anoLancamento, int duracaoEmMinutos) {
        this.id = id;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { this.diretor = diretor; }

    public int getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }

    public int getDuracaoEmMinutos() { return duracaoEmMinutos; }
    public void setDuracaoEmMinutos(int duracaoEmMinutos) { this.duracaoEmMinutos = duracaoEmMinutos; }

    @Override
    public String toString() { return titulo + " (" + anoLancamento + ") [ID: " + id + "]"; }
}
