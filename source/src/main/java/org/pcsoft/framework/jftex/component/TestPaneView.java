package org.pcsoft.framework.jftex.component;

import org.pcsoft.framework.jftex.component.cell.tree_view.ModelContainerTreeCell;
import org.pcsoft.framework.jftex.tree.ModelTreeItem;
import org.pcsoft.framework.jftex.type.ModelContainer;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.PropertyEditor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class TestPaneView implements FxmlView<TestPaneViewModel>, Initializable {

    @FXML
    private TreeView<ModelContainer> tvModel;
    @FXML
    private PropertySheet pnlProperties;
    @FXML
    private BorderPane pnlContent;

    @InjectViewModel
    private TestPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnlContent.centerProperty().bind(viewModel.nodeProperty());
        viewModel.modelProperty().addListener(o -> updateTreeView());
        viewModel.packageNameProperty().addListener(o -> updateTreeView());

        pnlProperties.setPropertyEditorFactory(new DefaultPropertyEditorFactory() {
            @Override
            public PropertyEditor<?> call(PropertySheet.Item item) {
                if (item.getType() == Boolean.class || item.getType() == boolean.class) {
                    final ChoiceBox<Boolean> choiceBox = new ChoiceBox<>();
                    choiceBox.getItems().setAll(null, Boolean.TRUE, Boolean.FALSE);
                    choiceBox.setConverter(new StringConverter<Boolean>() {
                        private static final String NOT_SET = "<not set>";

                        @Override
                        public String toString(Boolean object) {
                            return object == null ? NOT_SET : Boolean.toString(object);
                        }

                        @Override
                        public Boolean fromString(String string) {
                            if (string.equals(NOT_SET))
                                return null;

                            return Boolean.parseBoolean(string);
                        }
                    });

                    return new AbstractPropertyEditor<Boolean, ChoiceBox<Boolean>>(item, choiceBox) {
                        @Override
                        protected ObservableValue<Boolean> getObservableValue() {
                            return getEditor().valueProperty();
                        }

                        @Override
                        public void setValue(Boolean value) {
                            getEditor().setValue(value);
                        }
                    };
                } else if (Number.class.isAssignableFrom(item.getType()) || item.getType() == byte.class || item.getType() == short.class ||
                        item.getType() == int.class || item.getType() == long.class || item.getType() == float.class || item.getType() == double.class) {
                    final TextField textField = new TextField();
                    textField.addEventHandler(KeyEvent.KEY_TYPED, event -> {
                        if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE ||
                                event.getCode().isNavigationKey())
                            return;

                        if (!event.getCharacter().matches("[0-9]+")) {
                            event.consume();
                        } else if (((TextField) event.getSource()).getText() != null && ((TextField) event.getSource()).getText().length() >= 8) {
                            event.consume();
                        }
                    });

                    return new AbstractPropertyEditor<Number, TextField>(item, textField) {
                        @Override
                        protected ObservableValue<Number> getObservableValue() {
                            return Bindings.<Number>createObjectBinding(() -> {
                                try {
                                    return getEditor().getText() == null || getEditor().getText().isEmpty() ? null : Long.parseLong(getEditor().getText());
                                } catch (NumberFormatException e) {
                                    try {
                                        return getEditor().getText() == null || getEditor().getText().isEmpty() ? null : Double.parseDouble(getEditor().getText());
                                    } catch (NumberFormatException e1) {
                                        System.err.println("Unable to parse number");
                                        return null;
                                    }
                                }
                            }, getEditor().textProperty());
                        }

                        @Override
                        public void setValue(Number value) {
                            getEditor().setText(value == null ? null : value.toString());
                        }
                    };
                } else
                    return super.call(item);
            }
        });

        tvModel.setCellFactory(param -> new ModelContainerTreeCell());
        tvModel.getSelectionModel().selectedItemProperty().addListener(o -> updateProperties());

        updateTreeView();
    }

    //region Update Model in View
    void updateModelView() {
        updateModelView(tvModel.getSelectionModel().getSelectedItem());
    }

    private void updateModelView(final TreeItem<ModelContainer> selectedItem) {
        final boolean expanded = selectedItem != null && selectedItem.isExpanded();

        updateTreeView();

        if (selectedItem == null)
            return;

        final TreeItem<ModelContainer> treeItem = findTreeItem(tvModel.getRoot(), selectedItem);
        if (treeItem == null) {
            System.err.println("Unable to find old selected tree item: " + selectedItem.getValue().toString());
//            for (final TreeItem<ModelContainer> child : tvModel.getRoot().getChildren()) {
//                System.err.println("\t" + child.getValue().toString());
//            }
            return;
        }

        TreeItem<ModelContainer> current = treeItem.getParent();
        while (current != null) {
            current.setExpanded(true);
            current = current.getParent();
        }
        treeItem.setExpanded(expanded);
        tvModel.getSelectionModel().select(treeItem);
        tvModel.scrollTo(tvModel.getSelectionModel().getSelectedIndex());
    }

    private TreeItem<ModelContainer> findTreeItem(final TreeItem<ModelContainer> root, final TreeItem<ModelContainer> search) {
        if (root.getValue().equals(search.getValue())) {
            return root;
        }

        for (final TreeItem<ModelContainer> child : root.getChildren()) {
            final TreeItem<ModelContainer> treeItem = findTreeItem(child, search);
            if (treeItem != null)
                return treeItem;
        }

        return null;
    }
    //endregion

    private void updateProperties() {
        pnlProperties.getItems().clear();
        if (tvModel.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        final ModelContainer modelContainer = tvModel.getSelectionModel().getSelectedItem().getValue();

        final List<Field> fieldList = getFields(modelContainer.getValueClass());
        for (final Field field : fieldList) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()))
                continue;

            pnlProperties.getItems().add(new PropertySheet.Item() {
                @Override
                public Class<?> getType() {
                    return getValue() == null ? field.getType() : getValue().getClass();
                }

                @Override
                public String getCategory() {
                    return null;
                }

                @Override
                public String getName() {
                    return field.getName();
                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public Object getValue() {
                    try {
                        return modelContainer.getValue() == null ? null : field.get(modelContainer.getValue());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void setValue(Object value) {
                    if (modelContainer.getValue() == null)
                        return;

                    try {
                        field.set(modelContainer.getValue(), value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public boolean isEditable() {
                    return false;
                }

                @Override
                public Optional<ObservableValue<? extends Object>> getObservableValue() {
                    return modelContainer.getValue() == null ? Optional.empty() :
                            Optional.of(new SimpleObjectProperty<>(modelContainer.getValue()));
                }
            });
        }
    }

    private void updateTreeView() {
        if (viewModel.getModel() == null) {
            tvModel.setRoot(null);
            return;
        }

        final TreeItem<ModelContainer> root = new ModelTreeItem(new ModelContainer(viewModel.getModel(), viewModel.getModel().getClass(), "root"));
        updateTreeView(viewModel.getModel(), viewModel.getModel().getClass(), root);

        tvModel.setRoot(root);
    }

    private void updateTreeView(final Object model, final Class modelClass, final TreeItem<ModelContainer> root) {
        final List<Field> fieldList = getFields(modelClass);
        for (final Field field : fieldList) {
            if ((viewModel.getPackageName() == null || field.getType().getPackage() == null ||
                    !field.getType().getPackage().getName().startsWith(viewModel.getPackageName()) || field.getType().isEnum()) &&
                    !Collection.class.isAssignableFrom(field.getType()) && !Map.class.isAssignableFrom(field.getType()))
                continue;

            try {
                field.setAccessible(true);
                final Object value = model == null ? null : field.get(model);

                if (Collection.class.isAssignableFrom(field.getType()) && value != null) {
                    final Collection collection = (Collection) value;
                    int counter = 0;
                    for (final Object item : collection) {
                        if (item == null)
                            continue;

                        final TreeItem<ModelContainer> child = new ModelTreeItem(new ModelContainer(
                                item, item.getClass(), field.getName() + "; Index " + counter
                        ));
                        updateTreeView(item, item.getClass(), child);

                        root.getChildren().add(child);
                        counter++;
                    }
                } else if (Map.class.isAssignableFrom(field.getType()) && value != null) {
                    final Map map = (Map) value;
                    for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
                        if (entry.getValue() == null)
                            return;

                        final TreeItem<ModelContainer> child = new ModelTreeItem(new ModelContainer(
                                entry.getValue(), entry.getValue().getClass(), field.getName() + "; Key '" + entry.getKey() + "'"
                        ));
                        updateTreeView(entry.getValue(), entry.getValue().getClass(), child);

                        root.getChildren().add(child);
                    }
                } else {
//                    System.err.println(field.getDeclaringClass().getName() + "#" + field.getName() + " " + field.getType().getName());
                    final TreeItem<ModelContainer> child = new ModelTreeItem(new ModelContainer(value, field));
                    updateTreeView(value, value == null ? field.getType() : value.getClass(), child);

                    root.getChildren().add(child);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static List<Field> getFields(final Class clazz) {
        final List<Field> fieldList = new ArrayList<>();

        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null && clazz != Object.class) {
            fieldList.addAll(getFields(clazz.getSuperclass()));
        }

        return fieldList;
    }
}
