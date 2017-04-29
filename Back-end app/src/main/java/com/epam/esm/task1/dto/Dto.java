package com.epam.esm.task1.dto;

import java.io.Serializable;

/**
 * Abstract class that is extended by the application DTO classes.
 *
 * @author Uladzislau Kastsevich
 */
public abstract class Dto<K extends Serializable> implements Serializable {
    private static final long serialVersionUID = 2134722856194625476L;

    protected K id;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
