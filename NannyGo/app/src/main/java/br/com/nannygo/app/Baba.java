package br.com.nannygo.app;

public class Baba {

    private int imagem;
    private String nome;
    private String avaliacao;
    private String preco;
    private String hora;

    public Baba(int imagem, String nome, String avalicao, String preco, String hora){
        this.imagem = imagem;
        this.nome = nome;
        this.avaliacao = avalicao;
        this.preco = preco;
        this.hora = hora;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
