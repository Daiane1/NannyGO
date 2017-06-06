package br.com.nannygo.app;

//Classe modelo Babá
public class Baba {

    private String nome;
    private String preco;
    private String horaInicio;
    private String horaFim;
    private String diasDisponiveis;
    private Integer idBaba;
    private Integer idUsuario;
    private double distancia;
    private double distanciaKm;
    private String login;
    private String sexo;


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
        double km = distancia / 1000;
        return km;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
