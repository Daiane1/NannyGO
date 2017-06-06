package br.com.nannygo.app;

//Classe modelo Cidade
public class Cidade
{
    private String idCidade;
    private String cidade;
    private String estado;
    private String uf;


    public String getIdCidade()
    {
        return idCidade;
    }

    public void setIdCidade(String idCidade)
    {
        this.idCidade = idCidade;
    }

    public String getCidade()
    {
        return cidade;
    }

    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public String getUf()
    {
        return uf;
    }

    public void setUf(String uf)
    {
        this.uf = uf;
    }
}
