package org.codehaus.griffon.mercurial

import ca.odell.glazedlists.swing.EventTableModel
import ca.odell.glazedlists.gui.TableFormat
import ca.odell.glazedlists.swing.TableComparatorChooser
import ca.odell.glazedlists.gui.AbstractTableComparatorChooser

filePanel = scrollPane {
    table(id: "fileTable", model: new EventTableModel(model.fileList, [
        getColumnCount: { model.columns.size() },
        getColumnName: {index -> model.columns[index] },
        getColumnValue: {object, index ->
            object."${model.columns[index].toLowerCase()}"
        }] as TableFormat))
    def tableSorter = TableComparatorChooser.install(fileTable,
        model.fileList, AbstractTableComparatorChooser.SINGLE_COLUMN)
}
