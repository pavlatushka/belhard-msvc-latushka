package by.latushka.resourceservice.client;

public interface MessagePublisher {
    void uploadMessage(Object message);

    void deleteMessage(Object message);
}
