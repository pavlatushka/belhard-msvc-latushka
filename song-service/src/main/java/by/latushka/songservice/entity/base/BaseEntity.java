package by.latushka.songservice.entity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> implements Cloneable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
}
