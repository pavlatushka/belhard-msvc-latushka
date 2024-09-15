package by.latushka.songservice.listener;

import java.util.Set;

public interface MessageListener {
    void listenToDelete(Set<Long> ids);
}
