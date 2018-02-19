package org.pcsoft.framework.jftex.component.cell.tree_view;

import org.pcsoft.framework.jftex.component.cell.ModelContainerCellPane;
import org.pcsoft.framework.jftex.type.ModelContainer;
import javafx.scene.control.TreeCell;

public class ModelContainerTreeCell extends TreeCell<ModelContainer> {

    @Override
    protected void updateItem(ModelContainer item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);
        if (item != null && !empty) {
            setGraphic(new ModelContainerCellPane(item));
        }
    }
}
