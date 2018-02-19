package org.pcsoft.framework.jftex.type;

import java.lang.reflect.Field;
import java.util.Objects;

public class ModelContainer {
    private final Object value;
    private final Class valueClass;
    private final String name;

    public ModelContainer(Object value, Field field) {
        this(value, field, null);
    }

    public ModelContainer(Object value, Field field, String append) {
        this(value, value == null ? field.getType() : value.getClass(), field.getName() + (append == null ? "" : append));
    }

    public ModelContainer(Object value, Class valueClass, String name) {
        this.value = value;
        this.valueClass = valueClass;
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public Class getValueClass() {
        return valueClass;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelContainer that = (ModelContainer) o;
        return Objects.equals(valueClass, that.valueClass) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueClass, name);
    }

    @Override
    public String toString() {
        return "ModelContainer{" +
                "name='" + name + '\'' +
                ", valueClass=" + valueClass +
                '}';
    }
}
