package org.codehaus.griffon.mercurial

import groovy.beans.Bindable
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode

class TreePanelModel {
    @Bindable DefaultTreeModel tree = new DefaultTreeModel(new DefaultMutableTreeNode("Sample"), true)
}
