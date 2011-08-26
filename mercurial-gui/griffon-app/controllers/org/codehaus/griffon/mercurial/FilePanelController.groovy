package org.codehaus.griffon.mercurial

import javax.swing.table.TableRowSorter
import javax.swing.table.JTableHeader

class FilePanelController {
    def model
    def view
    def parentController

    void mvcGroupInit(Map args) {
        parentController = args.parentController
    }

    def updateStatus = {status ->
        println "\n\nupdateStatus${status?.dump()}"

        model.fileList.clear()
        model.fileList.addAll status.values()
    }
}
