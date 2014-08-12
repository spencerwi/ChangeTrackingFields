package com.spencerwi.changetrackingfield;

import java.time.LocalDateTime;

public class Change<T> {
    private T value;
    private LocalDateTime timestamp;

    public Change(T value) {
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    public T getValue() { return value; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
