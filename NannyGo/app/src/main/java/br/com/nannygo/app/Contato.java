package br.com.nannygo.app;

//Classe modelo de Contato
public class Contato {

    private int imagem;
    private String nome;
    private String avaliacao;

    public Contato(int imagem, String nome, String avalicao){
        this.imagem = imagem;
        this.nome = nome;
        this.avaliacao = avalicao;
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
}
