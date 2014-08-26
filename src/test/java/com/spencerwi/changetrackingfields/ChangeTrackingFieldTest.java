package com.spencerwi.changetrackingfields;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.spencerwi.hamcrestJDK8Time.matchers.IsBetween.between;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ChangeTrackingFieldTest {

    private ChangeTrackingField<Integer> sut;

    @Test
    public void canBeCreated(){
        Integer initialValue = 4;
        sut = new ChangeTrackingField<>(initialValue);
        
        assertThat(sut.getCurrentValue(), is(initialValue));
    }

    @Test
    public void canBeUpdated(){
        sut = new ChangeTrackingField<>(4);
        Integer newValue = 5;

        sut.update(newValue);
        
        assertThat(sut.getCurrentValue(), is(newValue));
    }
    
    @Test
    public void tracksWhenChanged(){
        sut = new ChangeTrackingField<>(4);

        sut.update(5);
        
        assertThat(sut.hasChanged(), is(true));
    }
    
    @Test
    public void keepsAListOfChanges(){
        sut = new ChangeTrackingField<>(4);

        assertThat(sut.getChanges(), is(emptyList()));

        LocalDateTime beforeFirstChange = LocalDateTime.now();
        sut.update(5);
        LocalDateTime afterFirstChange = LocalDateTime.now();
        sut.update(6);
        LocalDateTime afterSecondChange = LocalDateTime.now();

        List<ChangeTrackingField.Change<Integer>> changes = sut.getChanges();
        assertThat(changes, hasSize(2));

        ChangeTrackingField.Change<Integer> firstChange = changes.get(0);
        assertThat(firstChange.getValue(), is(5));
        assertThat(firstChange.getTimestamp(), is(between(beforeFirstChange, afterFirstChange)));

        ChangeTrackingField.Change<Integer> secondChange = changes.get(1);
        assertThat(secondChange.getValue(), is(6));
        assertThat(secondChange.getTimestamp(), is(between(afterFirstChange, afterSecondChange)));
    }
    
    @Test
    public void canResetChanges(){
        final int initialValue = 4;
        sut = new ChangeTrackingField<>(initialValue);

        sut.update(5);
        assertThat(sut.hasChanged(),      is(true));
        assertThat(sut.getChanges(),      hasSize(1));
        assertThat(sut.getCurrentValue(), is(5));

        sut.reset();
        assertThat(sut.hasChanged(),      is(false));
        assertThat(sut.getChanges(),      is(emptyList()));
        assertThat(sut.getCurrentValue(), is(initialValue));
    }
}
