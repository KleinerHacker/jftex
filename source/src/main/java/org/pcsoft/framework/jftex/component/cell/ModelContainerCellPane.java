package org.pcsoft.framework.jftex.component.cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;
import org.pcsoft.framework.jfex.mvvm.Fxml;
import org.pcsoft.framework.jftex.type.ModelContainer;

public class ModelContainerCellPane extends VBox {
    private final ObjectProperty<ModelContainer> modelContainer = new SimpleObjectProperty<>();

    // prevents controller for GC clean
    @SuppressWarnings("pmd:UnusedPrivateField")
    private final ModelContainerCellPaneView controller;

    public ModelContainerCellPane() {
        final Fxml.FxmlTuple<ModelContainerCellPaneView, ModelContainerCellPaneViewModel> viewTuple =
                Fxml.from(ModelContainerCellPaneView.class).withRoot(this).load();
        controller = viewTuple.getViewController();

        modelContainer.addListener(o -> viewTuple.getViewModel().updateProperties(modelContainer.get()));
    }

    public ModelContainerCellPane(final ModelContainer modelContainer) {
        this();
        setModelContainer(modelContainer);
    }

    public ModelContainer getModelContainer() {
        return modelContainer.get();
    }

    public ObjectProperty<ModelContainer> modelContainerProperty() {
        return modelContainer;
    }

    public void setModelContainer(ModelContainer modelContainer) {
        this.modelContainer.set(modelContainer);
    }
}
