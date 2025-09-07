package dev.gerardomarquez.utils;

/*
 * Clase que contiene informacion del usuario, es un singleton
 */
public class UserInformation {

    /*
     * Token del usuario
     */
    private String token;

    /*
     * Instancia unica
     */
    private static UserInformation instancia;

    /*
     * Constructor privado evita que otros creen instancias
     */
    private UserInformation() { }

    /*
     * Método público para obtener la instancia
     */
    public static UserInformation getInstancia() {
        if (instancia == null) {
            instancia = new UserInformation();
        }
        return instancia;
    }

    /*
     * Para obtener el token
     * @return Token
     */
    public String getToken(){
        return this.token;
    }

    /*
     * Para setear el token
     * @param token
     */
    public void setToke(String token){
        this.token = token;
    }
}
