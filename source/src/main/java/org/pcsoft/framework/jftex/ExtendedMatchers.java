package org.pcsoft.framework.jftex;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import org.hamcrest.Matcher;
import org.testfx.matcher.base.GeneralMatchers;

import java.util.Objects;

/**
 * Extended matcher for TestFX with additional useful functions
 */
public final class ExtendedMatchers {

    public static Matcher<Node> isSelected() {
        return GeneralMatchers.<Node>baseMatcher("Is selected", node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof Toggle) {
                return ((Toggle) node).isSelected();
            } else if (node instanceof CheckBox) {
                return ((CheckBox) node).isSelected();
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> isSelected(Object value) {
        return GeneralMatchers.<Node>baseMatcher("Is selected '" + value + "'", node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ChoiceBox) {
                return Objects.equals(((ChoiceBox) node).getValue(), value);
            } else if (node instanceof ComboBox) {
                return Objects.equals(((ComboBox) node).getValue(), value);
            } else if (node instanceof ListView) {
                return Objects.equals(((ListView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TreeView) {
                return Objects.equals(((TreeView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TableView) {
                return Objects.equals(((TableView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TreeTableView) {
                return Objects.equals(((TreeTableView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof Spinner) {
                return Objects.equals(((Spinner) node).getValue(), value);
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> isNotSelected() {
        return GeneralMatchers.<Node>baseMatcher("Is not selected", node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof Toggle) {
                return !((Toggle) node).isSelected();
            } else if (node instanceof CheckBox) {
                return !((CheckBox) node).isSelected();
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> isNotSelected(Object value) {
        return GeneralMatchers.<Node>baseMatcher("Is not selected '" + value + "'", node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ChoiceBox) {
                return !Objects.equals(((ChoiceBox) node).getValue(), value);
            } else if (node instanceof ComboBox) {
                return !Objects.equals(((ComboBox) node).getValue(), value);
            } else if (node instanceof ListView) {
                return !Objects.equals(((ListView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TreeView) {
                return !Objects.equals(((TreeView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TableView) {
                return !Objects.equals(((TableView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof TreeTableView) {
                return !Objects.equals(((TreeTableView) node).getSelectionModel().getSelectedItem(), value);
            } else if (node instanceof Spinner) {
                return !Objects.equals(((Spinner) node).getValue(), value);
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> hasListSizeOf(int listSize) {
        return GeneralMatchers.<Node>baseMatcher("Has list size of " + listSize, node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ListView)
                return ((ListView) node).getItems().size() == listSize;
            else if (node instanceof TableView)
                return ((TableView) node).getItems().size() == listSize;
            else if (node instanceof ComboBox)
                return ((ComboBox) node).getItems().size() == listSize;
            else if (node instanceof ChoiceBox) {
                return ((ChoiceBox) node).getItems().size() == listSize;
            }

            throw createWrongNodeTypeException(node);
        });
    }

    private static IllegalArgumentException createWrongNodeTypeException(final Node node) {
        return new IllegalArgumentException("Wrong node type: " + node.getClass());
    }

    public static Matcher<Node> isListContains(Object value) {
        return GeneralMatchers.<Node>baseMatcher("Is list contains " + value, node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ListView)
                return ((ListView) node).getItems().contains(value);
            else if (node instanceof TableView)
                return ((TableView) node).getItems().contains(value);
            else if (node instanceof ComboBox)
                return ((ComboBox) node).getItems().contains(value);
            else if (node instanceof ChoiceBox) {
                return ((ChoiceBox) node).getItems().contains(value);
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> isNotListContains(Object value) {
        return GeneralMatchers.<Node>baseMatcher("Is not list contains " + value, node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ListView)
                return !((ListView) node).getItems().contains(value);
            else if (node instanceof TableView)
                return !((TableView) node).getItems().contains(value);
            else if (node instanceof ComboBox)
                return !((ComboBox) node).getItems().contains(value);
            else if (node instanceof ChoiceBox) {
                return !((ChoiceBox) node).getItems().contains(value);
            }

            throw createWrongNodeTypeException(node);
        });
    }

    public static Matcher<Node> hasListValueAt(Object value, int index) {
        return GeneralMatchers.<Node>baseMatcher("Is list has value at " + value, node -> {
            if (node == null) {
                throw createNodeIsNullException();
            }

            if (node instanceof ListView)
                return Objects.equals(((ListView) node).getItems().get(index), value);
            else if (node instanceof TableView)
                return Objects.equals(((TableView) node).getItems().get(index), value);
            else if (node instanceof ComboBox)
                return Objects.equals(((ComboBox) node).getItems().get(index), value);
            else if (node instanceof ChoiceBox) {
                return Objects.equals(((ChoiceBox) node).getItems().get(index), value);
            }

            throw createWrongNodeTypeException(node);
        });
    }

    private static IllegalArgumentException createNodeIsNullException() {
        return new IllegalArgumentException("Node is null!");
    }

    private ExtendedMatchers() {
    }
}
