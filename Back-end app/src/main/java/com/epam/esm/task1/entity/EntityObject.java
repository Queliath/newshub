package com.epam.esm.task1.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract class that is extended by the application entities.
 *
 * @author Uladzislau Kastsevich
 */
@MappedSuperclass
public abstract class EntityObject<K extends Serializable> {
    protected K id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityObject<?> that = (EntityObject<?>) o;

        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
