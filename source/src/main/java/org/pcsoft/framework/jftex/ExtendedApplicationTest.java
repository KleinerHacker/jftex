package org.pcsoft.framework.jftex;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.testfx.framework.junit.ApplicationTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * An extended application test class, bassed on TestFX test class, with method to manipulate UI without direct input simulation.
 */
public abstract class ExtendedApplicationTest extends ApplicationTest {

    protected ExtendedApplicationTest fireMenuItemFX(String query, String... menuItemIds) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof MenuButton) {
                final ObservableList<MenuItem> menuItems = ((MenuButton) node).getItems();
                final boolean success = findAndFire(menuItems, menuItemIds, 0);
                if (!success) {
                    throw new IllegalStateException("Cannot find menu item path: " + Arrays.toString(menuItemIds));
                }
            }
        }

        sleep(500);
        return this;
    }

    private boolean findAndFire(final List<MenuItem> menuItems, String[] menuItemIds, final int level) {
        if (level >= menuItemIds.length) {
            return false;
        }

        for (final MenuItem menuItem : menuItems) {
            if (menuItem.getId().equals(menuItemIds[level])) {
                if (level == menuItemIds.length - 1) {
                    Platform.runLater(menuItem::fire);
                    return true;
                } else if (menuItem instanceof Menu) {
                    return findAndFire(((Menu) menuItem).getItems(), menuItemIds, level + 1);
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public ExtendedApplicationTest fireFX(String query) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof Button) {
                Platform.runLater(((Button) node)::fire);
            } else if (node instanceof ToggleButton) {
                Platform.runLater(((ToggleButton) node)::fire);
            } else if (node instanceof MenuButton) {
                Platform.runLater(((MenuButton) node)::fire);
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest clickOnFX(String query) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof Button) {
                Platform.runLater(((Button) node)::fire);
            } else {
                Platform.runLater(() -> {
                    MouseEvent.fireEvent(
                            node::buildEventDispatchChain,
                            new MouseEvent(MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null)
                    );
                    MouseEvent.fireEvent(
                            node::buildEventDispatchChain,
                            new MouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null)
                    );
                });
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest selectFX(String query, Object selectedValue) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).getSelectionModel().select(selectedValue));
            } else if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).getSelectionModel().select(selectedValue));
            } else if (node instanceof ListView) {
                Platform.runLater(() -> ((ListView) node).getSelectionModel().select(selectedValue));
            } else if (node instanceof TableView) {
                Platform.runLater(() -> ((TableView) node).getSelectionModel().select(selectedValue));
            } else if (node instanceof DatePicker) {
                Platform.runLater(() -> ((DatePicker) node).setValue((LocalDate) selectedValue));
            } else if (node instanceof Spinner) {
                Platform.runLater(() -> ((Spinner)node).getValueFactory().setValue(selectedValue));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest typeFX(String query, final String text) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof TextField) {
                Platform.runLater(() -> ((TextField) node).setText(text));
            } else if (node instanceof PasswordField) {
                Platform.runLater(() -> ((PasswordField) node).setText(text));
            } else if (node instanceof TextArea) {
                Platform.runLater(() -> ((TextArea) node).setText(text));
            } else if (node instanceof ComboBox && ((ComboBox)node).isEditable()) {
                Platform.runLater(() -> ((ComboBox)node).getEditor().setText(text));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest changeCheckStateFX(String query) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof CheckBox) {
                Platform.runLater(() -> ((CheckBox) node).setSelected(!((CheckBox) node).isSelected()));
            } else if (node instanceof RadioButton) {
                Platform.runLater(() -> ((RadioButton) node).setSelected(!((RadioButton)node).isSelected()));
            } else if (node instanceof ToggleButton) {
                Platform.runLater(() -> ((ToggleButton) node).setSelected(!((ToggleButton)node).isSelected()));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest expandFX(String query) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).show());
            } else if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).show());
            } else if (node instanceof MenuButton) {
                Platform.runLater(() -> ((MenuButton) node).show());
            } else if (node instanceof SplitMenuButton) {
                Platform.runLater(() -> ((SplitMenuButton) node).show());
            } else if (node instanceof TitledPane) {
                Platform.runLater(() -> ((TitledPane) node).setExpanded(true));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest collapseFX(String query) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).hide());
            } else if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).hide());
            } else if (node instanceof MenuButton) {
                Platform.runLater(() -> ((MenuButton) node).hide());
            } else if (node instanceof SplitMenuButton) {
                Platform.runLater(() -> ((SplitMenuButton) node).hide());
            } else if (node instanceof TitledPane) {
                Platform.runLater(() -> ((TitledPane) node).setExpanded(false));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    private IllegalStateException createNotSupportedObject(final Node node) {
        return new IllegalStateException("Not supported object: " + node.getClass().getName());
    }

    public ExtendedApplicationTest insertListValueAt(String query, int index, Object value) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).getItems().add(index, value));
            } else if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).getItems().add(index, value));
            } else if (node instanceof ListView) {
                Platform.runLater(() -> ((ListView) node).getItems().add(index, value));
            } else if (node instanceof TableView) {
                Platform.runLater(() -> ((TableView) node).getItems().add(index, value));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest insertListValue(String query, Object value) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).getItems().add(value));
            } else if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).getItems().add(value));
            } else if (node instanceof ListView) {
                Platform.runLater(() -> ((ListView) node).getItems().add(value));
            } else if (node instanceof TableView) {
                Platform.runLater(() -> ((TableView) node).getItems().add(value));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    protected final Set<Node> findNodes(final String query) {
        final Set<Node> nodes = robotContext().getNodeFinder().lookup(query).queryAll();
        if (nodes.isEmpty()) {
            throw new IllegalStateException("No nodes found!");
        }
        return nodes;
    }

    public ExtendedApplicationTest removeListValueAt(String query, int index) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).getItems().remove(index));
            } else if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).getItems().remove(index));
            } else if (node instanceof ListView) {
                Platform.runLater(() -> ((ListView) node).getItems().remove(index));
            } else if (node instanceof TableView) {
                Platform.runLater(() -> ((TableView) node).getItems().remove(index));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

    public ExtendedApplicationTest removeListValue(String query, Object value) {
        final Set<Node> nodes = findNodes(query);

        for (final Node node : nodes) {
            if (node instanceof ComboBox) {
                Platform.runLater(() -> ((ComboBox) node).getItems().remove(value));
            } else if (node instanceof ChoiceBox) {
                Platform.runLater(() -> ((ChoiceBox) node).getItems().remove(value));
            } else if (node instanceof ListView) {
                Platform.runLater(() -> ((ListView) node).getItems().remove(value));
            } else if (node instanceof TableView) {
                Platform.runLater(() -> ((TableView) node).getItems().remove(value));
            } else {
                throw createNotSupportedObject(node);
            }
        }

        sleep(500);
        return this;
    }

}
