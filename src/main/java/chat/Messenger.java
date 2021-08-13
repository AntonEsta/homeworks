package chat;

public interface Messenger<T> {

    T getMessage();

    void sendMessage(T message);

}
