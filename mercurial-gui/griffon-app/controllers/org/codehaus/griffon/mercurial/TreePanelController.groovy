package org.codehaus.griffon.mercurial

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode

class TreePanelController {
    def model
    def view
    def parentController

    void mvcGroupInit(Map args) {
        parentController = args.parentController
    }

    def updateRootDirectory = {event ->
        doOutside {
            DefaultTreeModel tree = prepareTreeModel(app.config.mercurial.rootDirectory)
            edt {
                model.tree = tree
            }
        }
    }
    def nodeSelected = {selectionEvent ->
        doOutside {
            parentController.nodeSelected(getSelectedPath(view.tree.lastSelectedPathComponent))
        }
    }

    private def getSelectedPath(DefaultMutableTreeNode node) {
        def currentNode = node
        def path = ''
        while( !currentNode.isRoot() ) {
            path = "${currentNode.userObject}${File.separator}$path"
            currentNode = currentNode.parent
        }
        return path
    }

    private def prepareTreeModel(File rootFile) {
        File mercurialRoot = new File(rootFile, ".hg")
        if( !mercurialRoot.exists() )
            return new DefaultTreeModel(new DefaultMutableTreeNode(app.config.mercurial.rootDirectory.path), true)
        return new DefaultTreeModel(prepareTreeNode(rootFile, true), true)
    }

    private def prepareTreeNode(File file, usePath = false) {
        def node = new DefaultMutableTreeNode(usePath ? file.path : file.name)
        file.eachDir {directory ->
            if( directory.name != '.hg' ) {
                node.add prepareTreeNode(directory)
            }
        }
        return node
    }
}

