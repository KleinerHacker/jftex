package org.pcsoft.framework.jftex.component;

import org.pcsoft.framework.jftex.ExtendedApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.Test;
import org.pcsoft.framework.jftex.component.TestPane;

public class TestPaneTest extends ExtendedApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Test Pane Test");
        stage.setResizable(false);
        stage.setOpacity(0);

        stage.setScene(new Scene(new TestPane(new Label("Hello"), "Hello", "java.lang"), 800, 600));
        stage.show();
    }

    @Test
    public void test() {

    }
}