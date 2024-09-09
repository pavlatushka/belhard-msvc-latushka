package by.latushka.resourceservice.client;

public interface StorageClient {
    void put(String key, byte[] object);

    byte[] get(String key);

    void delete(String key);
}
