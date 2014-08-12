package com.spencerwi.changetrackingfield;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableField<T> {
    protected List<Consumer<T>> onUpdateCallbacks = new ArrayList<>();
    private T value;

    public ObservableField(T initialValue) { this.value = initialValue; }

    public T getCurrentValue() { return value; }

    public void update(T newValue) {
        this.value = newValue;
        onUpdateCallbacks.forEach(callback -> callback.accept(newValue));
    }

    public void onUpdate(Consumer<T> callback){
        this.onUpdateCallbacks.add(callback);
    }
}
