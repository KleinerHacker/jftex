package org.pcsoft.framework.jftex.component.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.pcsoft.framework.jfex.mvvm.FxmlViewModel;
import org.pcsoft.framework.jftex.type.ModelContainer;

public class ModelContainerCellPaneViewModel implements FxmlViewModel {
    private final StringProperty fieldName = new SimpleStringProperty(), className = new SimpleStringProperty();
    private final BooleanProperty $null = new SimpleBooleanProperty();

    public String getFieldName() {
        return fieldName.get();
    }

    public StringProperty fieldNameProperty() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName.set(fieldName);
    }

    public String getClassName() {
        return className.get();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public boolean getNull() {
        return $null.get();
    }

    public BooleanProperty nullProperty() {
        return $null;
    }

    public void setNull(boolean $null) {
        this.$null.set($null);
    }

    void updateProperties(ModelContainer modelContainer) {
        setFieldName(modelContainer.getName());
        setClassName(modelContainer.getValueClass().getSimpleName());
        setNull(modelContainer.getValue() == null);
    }
}
