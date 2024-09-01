package by.latushka.resourceservice.service;

import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;

import java.io.InputStream;
import java.util.Set;

public interface ResourceService {
    byte[] findById(Long id) throws ResourceNotFoundException;

    Long save(InputStream is)throws InvalidResourceException;

    Set<Long> deleteAll(Set<Long> ids);
}
