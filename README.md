ChangeTrackingFields
====================

[![Build Status](https://travis-ci.org/spencerwi/ChangeTrackingFields.svg?branch=master)](https://travis-ci.org/spencerwi/ChangeTrackingFields)

A set of container "field" classes that are aware of changes and respond in varying ways

Class Hierarchy
---------------

     DirtyCheckingField<T>                  ObservableField<T>
           |
    ChangeTrackingField<T>
           |-> (inner class) Change<T>

`DirtyCheckingField<T>`
---------------------

A field that contains a value and has a "hasChanged" flag that can be reset.

Usage:

```java
DirtyCheckingField<String> firstName = new DirtyCheckingField<>("initial");

firstName.getCurrentValue(); /* "initial" */
firstName.hasChanged(); /* false */
firstName.update("new value");
firstName.getCurrentValue(); /* "new value" */
firstName.hasChanged(); /* true */
firstName.reset();
firstName.hasChanged(); /* false */
firstName.getCurrentValue(); /* "new value" */
```


`ChangeTrackingField<T>`
------------------------

Extends `DirtyCheckingField<T>`, but keeps a timestamped record of changes, and can reset its value to the initial value.

Usage:

```java
ChangeTrackingField<String> firstName = new ChangeTrackingField<>("initial");

firstName.getCurrentValue(); /* "initial" */
firstName.hasChanged(); /* false */
firstName.getChanges(); /* empty list */

firstName.update("new value");
firstName.getCurrentValue(); /* "new value" */
firstName.hasChanged(); /* true */
firstName.getChanges(); /* list: [ Change { timestamp: <change-time>, value: "new value" } ] */

firstName.reset();
firstName.hasChanged(); /* false */
firstName.getCurrentValue(); /* "initial" */
firstName.getChanges(); /* empty list */
```

`Change<T>`
------------

A read-only representation of a single change to a `ChangeTrackingField<T>`.

Has:

* `public T getValue()`
* `public LocalDateTime getTimestamp()`


`ObservableField<T>`
--------------------

A field that accepts `Consumer<T>` callbacks and calls them on updates.

Usage:

```java
ObservableField<Integer> iterations = new ObservableField<>(0);
iterations.onUpdate(newValue -> System.out.println(newValue));

iterations.update(1); /* System.out.println(1) gets called */
iterations.update(2); /* System.out.println(2) gets called */
```
