package br.com.nannygo.app;

/**
 * Created by 16165824 on 16/05/2017.
 */

public class FaleConosco {

    private static String comentario;
    private static String id_faleconosco;

    public static String getComentario(){
        return comentario;
    }

    public static void setComentario(String comentario) {
        FaleConosco.comentario = comentario;
    }

    public static String getId_faleconosco(){
        return id_faleconosco;
    }

    public static void setId_faleconosco(String id_faleconosco) {
        FaleConosco.id_faleconosco = id_faleconosco;
    }
}
