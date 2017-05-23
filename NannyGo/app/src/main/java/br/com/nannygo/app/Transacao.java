package br.com.nannygo.app;

public class Transacao {

    private static Integer idTransacao;
    private static Integer idUsuario;
    private static Integer idBaba;
    private static String data_transacao;
    private boolean status_aprovado;
    private static String metodo_pagamento;
    private String nome;

    public static Integer getIdTransacao() {
        return idTransacao;
    }

    public static Integer getIdUsuario() {
        return idUsuario;
    }

    public static Integer getIdBaba() {
        return idBaba;
    }

    public static String getData_transacao(){
        return data_transacao;
    }

    public boolean getStatus_aprovado(){
        return status_aprovado;
    }

    public static String getMetodo_pagamento(){
        return metodo_pagamento;
    }

    public String getNome() {
        return nome;
    }
}
