package org.pcsoft.framework.jftex.component.cell;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ModelContainerCellPaneView implements FxmlView<ModelContainerCellPaneViewModel>, Initializable {

    @FXML
    private VBox pnlRoot;
    @FXML
    private Label lblFieldName;
    @FXML
    private Label lblClassName;

    @InjectViewModel
    private ModelContainerCellPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblFieldName.textProperty().bind(viewModel.fieldNameProperty());
        lblClassName.textProperty().bind(viewModel.classNameProperty());
        pnlRoot.opacityProperty().bind(Bindings.when(viewModel.nullProperty()).then(.3d).otherwise(1d));
    }
}
