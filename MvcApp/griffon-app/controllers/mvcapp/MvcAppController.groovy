package mvcapp

import javax.swing.*
import javax.swing.event.*
import javax.swing.tree.*

class MvcAppController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
			 doOutside {
	      def contents = new DefaultMutableTreeNode("hudson")
	      (1..10).each {
	        def node = new DefaultMutableTreeNode("job-${it}")
	        contents.add(node)
	      }
	      doLater {
	        view.jobTree.model = new DefaultTreeModel(contents)
	        view.jobTree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
	        view.jobTree.addTreeSelectionListener( treeSelectionListener )

	        view.buildList.mouseClicked = listClickedListener
	      }
	    }
    }


		def treeSelectionListener = { evt ->
	    def path = evt.path
	    if (path.pathCount == 2) {
	      doLater {
	        def node = view.jobTree.lastSelectedPathComponent
	        if( !node ) return
	        def listModel = new DefaultListModel()
	        (1..10).each {
	          listModel.addElement("${node.userObject}::build-${it}")
	        }
	        view.buildList.model = listModel
	      }
	    }
	  } as TreeSelectionListener

	  def listClickedListener = { evt ->
	    if (evt.getClickCount() != 2) return
	    def idx = view.buildList.locationToIndex(evt.point)
	    if (idx < 0) return
	    def item = view.buildList.model.elementAt(idx)
	    createMVCGroup('DetailPanel', item, [rootPane:view.tab, item:item])
	  }

    /*
    def action = { evt = null ->
    }
    */
}