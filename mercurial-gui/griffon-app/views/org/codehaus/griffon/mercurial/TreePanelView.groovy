package org.codehaus.griffon.mercurial

import javax.swing.event.TreeSelectionListener
import javax.swing.tree.TreeSelectionModel

treePanel = scrollPane {
//    gridBagLayout()
    tree(id: "tree", model: bind {model.tree}, /*fill: BOTH, weightx: 1.0, weighty: 1.0*/)
    tree.addTreeSelectionListener([valueChanged: controller.&nodeSelected] as TreeSelectionListener)
    tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
}
