package chat.message;

/**
 * Enumeration of valid message formats.
 */
public enum MessageType {
    REGISTRATION_REQUEST,
    REGISTRATION_CONFIRMED,
    REGISTRATION_REFUSED,
    DETACH_USER,
    WHOIS,
    MESSAGE,
    ACKNOWLEDGMENT,
    REQUEST_NEW
}
