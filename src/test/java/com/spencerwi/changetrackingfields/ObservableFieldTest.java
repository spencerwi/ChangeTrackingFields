package com.spencerwi.changetrackingfields;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class ObservableFieldTest {
    private ObservableField<Integer> sut;

    @Mock private Consumer<Integer> callback;

    @Before
    public void setUp() throws Exception { MockitoAnnotations.initMocks(this); }

    @Test
    public void canBeCreated(){
        final int initialValue = 4;
        sut = new ObservableField<>(initialValue);

        assertThat(sut.getCurrentValue(), is(initialValue));
    }
    
    @Test
    public void canBeUpdated(){
        final int initialValue = 4,
                      newValue = 5;
        sut = new ObservableField<>(initialValue);
        assertThat(sut.getCurrentValue(), is(initialValue));

        sut.update(newValue);

        assertThat(sut.getCurrentValue(), is(newValue));
    }
    
    @Test
    public void canRegisterAnOnUpdateCallback(){
        sut = new ObservableField<>(4);
        assertThat(sut.onUpdateCallbacks, is(emptyList()));

        sut.onUpdate(callback);
        assertThat(sut.onUpdateCallbacks, contains(callback));
    }
    
    @Test
    public void notifiesCallbacksOnUpdate(){
        sut = new ObservableField<>(4);
        sut.onUpdate(callback);

        int newValue = 5;
        sut.update(newValue);

        verify(callback).accept(newValue);
    }
    
    @Test
    public void canUnregisterACallback(){
        sut = new ObservableField<>(4);
        Consumer<Integer> callback = (value -> System.out.println(value));
        sut.onUpdate(callback);
        assertThat(sut.onUpdateCallbacks, contains(callback));

        sut.unregister(callback);
        
        assertThat(sut.onUpdateCallbacks, not(contains(callback)));
    }
}