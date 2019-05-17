package org.pcsoft.framework.jftex.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import org.pcsoft.framework.jfex.mvvm.FxmlViewModel;

public class TestPaneViewModel implements FxmlViewModel {
    private final ObjectProperty<Node> node = new SimpleObjectProperty<>();
    private final ObjectProperty<Object> model = new SimpleObjectProperty<>();
    private final StringProperty packageName = new SimpleStringProperty();

    public String getPackageName() {
        return packageName.get();
    }

    public StringProperty packageNameProperty() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName.set(packageName);
    }

    public Node getNode() {
        return node.get();
    }

    public ObjectProperty<Node> nodeProperty() {
        return node;
    }

    public void setNode(Node node) {
        this.node.set(node);
    }

    public Object getModel() {
        return model.get();
    }

    public ObjectProperty<Object> modelProperty() {
        return model;
    }

    public void setModel(Object model) {
        this.model.set(model);
    }
}
