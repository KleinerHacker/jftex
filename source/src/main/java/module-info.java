open module org.pcsoft.jftex {
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires javafx.fxml;
    requires org.pcsoft.jfex.mvvm;
    requires org.testfx.junit;
    requires hamcrest.core;
    requires org.testfx;

    exports org.pcsoft.framework.jftex;
    exports org.pcsoft.framework.jftex.component;
}