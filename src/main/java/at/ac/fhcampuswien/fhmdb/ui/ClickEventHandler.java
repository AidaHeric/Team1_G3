package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;

@FunctionalInterface
public interface ClickEventHandler<T> {
    void onClick(T item) throws DatabaseException;
}
