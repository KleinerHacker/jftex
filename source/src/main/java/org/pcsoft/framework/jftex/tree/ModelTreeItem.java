package org.pcsoft.framework.jftex.tree;

import org.pcsoft.framework.jftex.type.ModelContainer;
import javafx.scene.control.TreeItem;

public class ModelTreeItem extends TreeItem<ModelContainer> {

    public ModelTreeItem(ModelContainer value) {
        super(value);
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null)
//            return false;
//        if (!(obj instanceof ModelTreeItem))
//            return false;
//        if (obj == this)
//            return true;
//
//        return ((ModelTreeItem) obj).getValue().equals(this.getValue());
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
