package mvcapp

import javax.swing.*
import javax.swing.tree.*
import javax.swing.event.*

class DetailPanelController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
				doOutside {
		      def contents = new DefaultMutableTreeNode(args.item)
		      (1..20).each {
		        def node = new DefaultMutableTreeNode("${args.item}::test-${it}")
		        contents.add(node)
		      }
		      doLater {
		        view.testTree.model = new DefaultTreeModel(contents)
		        view.testTree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
		        view.testTree.addTreeSelectionListener( treeSelectionListener )
		      }
		    }
    }

		def treeSelectionListener = { evt ->
	    def path = evt.path
	    if (path.pathCount == 2) {
	      doLater {
	        def node = view.testTree.lastSelectedPathComponent
	        if( !node ) return
	        def writer = new StringWriter()
	        (1..100).each {
	          writer << "${node.userObject}::console-${it}\n"
	        }
	        view.consoleText.text = writer.toString()
	      }
	    }
	  } as TreeSelectionListener

	  def closeTab = {
	    def tabIndex = view.tab.selectedIndex
	    if (tabIndex < 0) return
	    view.tab.remove(tabIndex)
	    view.tab.selectedIndex = 0
	  }


    /*
    def action = { evt = null ->
    }
    */
}