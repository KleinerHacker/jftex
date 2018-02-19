package org.pcsoft.framework.jftex.component.cell;

import org.pcsoft.framework.jftex.type.ModelContainer;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;

public class ModelContainerCellPane extends VBox {
    private final ObjectProperty<ModelContainer> modelContainer = new SimpleObjectProperty<>();

    // prevents controller for GC clean
    @SuppressWarnings("pmd:UnusedPrivateField")
    private final ModelContainerCellPaneView controller;

    public ModelContainerCellPane() {
        final ViewTuple<ModelContainerCellPaneView, ModelContainerCellPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(ModelContainerCellPaneView.class).root(this).load();
        controller = viewTuple.getCodeBehind();

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
