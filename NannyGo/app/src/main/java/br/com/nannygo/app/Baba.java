package br.com.nannygo.app;

public class Baba {

    private String imagem;
    private String nome;
    private String preco;
    private String horaInicio;
    private String horaFim;
    private String diasDisponiveis;
    private Integer idBaba;
    private Integer idUsuario;
    private double distancia;
    private double distanciaKm;


    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }

    public String getPreco() {
        return preco;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getDiasDisponiveis() {
        return diasDisponiveis;
    }

    public Integer getIdBaba() {
        return idBaba;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }


    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getDistanciaKm() {
        double km = distancia/1000;
        return km;
    }

}
