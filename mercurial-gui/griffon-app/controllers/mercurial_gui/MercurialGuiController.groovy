package mercurial_gui

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode
import org.codehaus.griffon.mercurial.service.MercurialService

class MercurialGuiController {
    def model
    def view

    def treePanelController
    def filePanelController

    void mvcGroupInit(Map args) {
				println "====MercurialGuiController::mvcGroupInit=="
        def (treePanelModel, treePanelView, treePanelController) = createMVCGroup ('TreePanel', [parentController: this])
        def (filePanelModel, filePanelView, filePanelController) = createMVCGroup ('FilePanel', [parentController: this])

        // wire in created panel elements
        //view.treePanel = treePanelView.treePanel
        //view.filePanel = filePanelView.filePanel
        model.treePanel = treePanelView.treePanel
        model.filePanel = filePanelView.filePanel
				println "[C]model.treePanel=${model.treePanel}"

        this.treePanelController = treePanelController
        this.filePanelController = filePanelController
    }


    def assignRootDirectory = {event ->
        def status
        doOutside {
            app.config.mercurial.rootDirectory = new File(model.rootDirectory)
            edt { treePanelController.updateRootDirectory() }
            nodeSelected()
        }
    }

    def nodeSelected = {path ->
        doOutside {
            def status = app.config.mercurial.status(path)
            edt {filePanelController.updateStatus(status)}
        }
    }
}
