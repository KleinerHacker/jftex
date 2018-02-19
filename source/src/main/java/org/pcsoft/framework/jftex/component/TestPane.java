package org.pcsoft.framework.jftex.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class TestPane extends SplitPane {

    // prevents controller for GC clean
    @SuppressWarnings("pmd:UnusedPrivateField")
    private final TestPaneView controller;
    private final TestPaneViewModel viewModel;

    public TestPane() {
        final ViewTuple<TestPaneView, TestPaneViewModel> viewTuple = FluentViewLoader.fxmlView(TestPaneView.class).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();
    }

    public TestPane(final Node node, final Object model, final String packageName) {
        this();
        setNode(node);
        setModel(model);
        setPackageName(packageName);
    }

    public Node getNode() {
        return viewModel.getNode();
    }

    public ObjectProperty<Node> nodeProperty() {
        return viewModel.nodeProperty();
    }

    public void setNode(Node node) {
        viewModel.setNode(node);
    }

    public Object getModel() {
        return viewModel.getModel();
    }

    public ObjectProperty<Object> modelProperty() {
        return viewModel.modelProperty();
    }

    public void setModel(Object model) {
        viewModel.setModel(model);
    }

    public String getPackageName() {
        return viewModel.getPackageName();
    }

    public StringProperty packageNameProperty() {
        return viewModel.packageNameProperty();
    }

    public void setPackageName(String packageName) {
        viewModel.setPackageName(packageName);
    }

    public void updateModelView() {
        controller.updateModelView();
    }
}
