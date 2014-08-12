package com.spencerwi.changetrackingfields;

public class DirtyCheckingField<T> {
    protected T currentValue;
    private boolean hasChanged = false;

    public DirtyCheckingField(T initialValue) { this.currentValue = initialValue; }

    public T getCurrentValue() { return currentValue; }
    public void update(T newValue) {
        this.currentValue = newValue;
        this.hasChanged = true;
    }

    public boolean hasChanged() { return this.hasChanged; }

    public void reset() { this.hasChanged = false; }
}
