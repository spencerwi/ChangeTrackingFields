package com.spencerwi.changetrackingfield;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class DirtyCheckingFieldTest {

    private DirtyCheckingField<Integer> sut;

    @Test
    public void canBeCreated(){
        int initialValue = 4;
        sut = new DirtyCheckingField<>(initialValue);

        assertThat(sut, isA(DirtyCheckingField.class));
        assertThat(sut.getCurrentValue(), is(initialValue));
    }

    @Test
    public void canBeUpdated(){
        sut = new DirtyCheckingField<>(4);
        int newValue = 5;

        sut.update(newValue);

        assertThat(sut.getCurrentValue(), is(newValue));
    }

    @Test
    public void tracksWhenChanged(){
        sut = new DirtyCheckingField<>(4);
        assertThat(sut.hasChanged(), is(false));

        sut.update(5);

        assertThat(sut.hasChanged(), is(true));
    }
    
    @Test
    public void canResetChangedFlag(){
        sut = new DirtyCheckingField<>(4);
        assertThat(sut.hasChanged(), is(false));

        sut.update(5);
        assertThat(sut.hasChanged(), is(true));

        sut.reset();
        assertThat(sut.hasChanged(), is(false));
    }
}
