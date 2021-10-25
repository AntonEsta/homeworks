package chat.messanger;

/**
 * Interface describes the elementary behavior of the messenger.
 * @param <T> specifies the class that plays the role of the message package.
 */
public interface Messenger<T> {

    /**
     * Sends message
     * @param message the message for send.
     */
    void sendMessage(T message);

    /**
     * Receive message.
     * @return the message packet
     */
    T getMessage();

}
