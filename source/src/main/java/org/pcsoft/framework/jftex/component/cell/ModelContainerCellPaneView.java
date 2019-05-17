package org.pcsoft.framework.jftex.component.cell;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.pcsoft.framework.jfex.mvvm.FxmlView;

import java.net.URL;
import java.util.ResourceBundle;

public class ModelContainerCellPaneView extends FxmlView<ModelContainerCellPaneViewModel> {

    @FXML
    private VBox pnlRoot;
    @FXML
    private Label lblFieldName;
    @FXML
    private Label lblClassName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblFieldName.textProperty().bind(viewModel.fieldNameProperty());
        lblClassName.textProperty().bind(viewModel.classNameProperty());
        pnlRoot.opacityProperty().bind(Bindings.when(viewModel.nullProperty()).then(.3d).otherwise(1d));
    }
}
