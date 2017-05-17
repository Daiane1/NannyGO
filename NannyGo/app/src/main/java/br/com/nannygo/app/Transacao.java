package br.com.nannygo.app;

public class Transacao {

    private Integer idTransacao;
    private Integer idUsuario;
    private Integer idBaba;
    private String data_transacao;
    private boolean status_aprovado;
    private String metodo_pagamento;

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getIdBaba() {
        return idBaba;
    }

    public String getData_transacao(){
        return data_transacao;
    }

    public boolean getStatus_aprovado(){
        return status_aprovado;
    }

    public String getMetodo_pagamento(){
        return metodo_pagamento;
    }
}
