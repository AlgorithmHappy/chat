package dev.gerardomarquez.responses;

/*
 * Record que debera de llenarse en el caso de que el post de la peticion de conversacion
 * haya sido exitosa
 */
public record RequestConversationCreatedResponse(
    /*
     * Nombre de usuario a quien se mando la peticion
     */
    String targetUserName,
    /*
     * Estatus actual
     */
    String status,
    /*
     * Fecha en formato dd/mm/yyyy HH:mm:ss
     */
    String date,
    /*
     * Id de la peticion de conversacion
     */
    String id
) {
    /*
     * Metodo para que el set valide correctamente si una peticion de conversacion es igual a otra
     * o no. Que valide solo el id
     * @param Object objeto cualquiera para comparar
     * @return Validar si es igual o no
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestConversationCreatedResponse miRecord = (RequestConversationCreatedResponse) o;
        return id == miRecord.id;
    }
}
