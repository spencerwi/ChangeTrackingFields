package com.spencerwi.changetrackingfields;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChangeTrackingField<T> extends DirtyCheckingField<T> {

    protected List<Change<T>> changes = new ArrayList<>();
    protected T initialValue;

    public ChangeTrackingField(T initialValue) {
        super(initialValue);
        this.initialValue = initialValue;
    }

    @Override
    public void update(T newValue) {
        super.update(newValue);
        changes.add(new Change<>(newValue));
    }

    public List<Change<T>> getChanges() { return changes; }

    @Override
    public void reset() {
        super.reset();
        this.changes = new ArrayList<>();
        this.currentValue = this.initialValue;
    }

    public static class Change<T> {
        private T value;
        private LocalDateTime timestamp;

        private Change(T value) {
            this.value = value;
            this.timestamp = LocalDateTime.now();
        }

        public T getValue() { return value; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
