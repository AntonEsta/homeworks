package chat;

public enum MessageType {

    NONE,
    REGISTRATION_REQUEST,
    REGISTRATION_CONFIRMED,
    REGISTRATION_REFUSED,
    DETACH_USER,
    WHOIS,
    MESSAGE,
    ACKNOWLEDGMENT,
    REQUEST_NEW;

    public boolean isAcknowledgment() {
        return this.equals(MessageType.ACKNOWLEDGMENT);
    }

}
