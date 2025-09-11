package dev.gerardomarquez.responses;

/*
 * Record que se devolvera en el caso de que se obtengan las peticiones de conversacion
 * de un usuario "target"
 */
public record RequestConversationReceivedResponse (
     /*
     * Nombre de usuario de quien llega la peticion
     */
    String requesterUserName,

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
        RequestConversationReceivedResponse myRecord = (RequestConversationReceivedResponse) o;
        return id == myRecord.id;
    }
}
