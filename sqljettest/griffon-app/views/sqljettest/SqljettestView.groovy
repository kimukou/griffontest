package sqljettest

import ca.odell.glazedlists.*
import ca.odell.glazedlists.gui.*
import ca.odell.glazedlists.swing.*
import net.miginfocom.swing.MigLayout

import java.awt.*
import javax.swing.*
import java.awt.BorderLayout as BL


frame = application(title: 'sqljettest',
  //size: [320,480],
  pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {

	migLayout(layoutConstraints: "gap 0,insets 0,fill")
	panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)) {
		borderLayout(hgap:0,vgap:0)
		panel(constraints:BL.LINE_START){
			migLayout(layoutConstraints: "gap 0,insets 0,fill")

			java.text.NumberFormat nf = java.text.NumberFormat.getInstance()
			nf.setGroupingUsed(false)//comma del
			nf.setMinimumIntegerDigits(7)  //min figure
			java.text.NumberFormat nf2 = java.text.NumberFormat.getInstance()
			nf2.setGroupingUsed(false)//comma del
			nf2.setMinimumIntegerDigits(5)  //min figure
			nf2.setMaximumFractionDigits(2) //2 few figures

			println griffon.util.ApplicationHolder.application.config.font.name

			//件数
			jxlabel(getMessage("view.count.title"),constraints: "")
			jxlabel(id: "count",text:bind {nf.format(model.count)},constraints: "")
			jxlabel("/",constraints: "")
			jxlabel(id: "result",text:bind {nf.format(model.result)},constraints: "")

			jxlabel(id: 'number_case',getMessage("view.count.unit"),constraints: "wrap")
			//[TODO]not displayed
			jxlabel(id: 'number_case',getMessage("view.count.unit"),constraints: "wrap")
			println "${number_case.font}"

			//検索(分)
			jxlabel(getMessage("view.time_serch.title"),constraints: "")
			jxlabel(id: "time_serch",text:bind {nf2.format(model.time_serch)},constraints: "")
			jxlabel(getMessage("view.time.unit"),constraints: "wrap")
		}
		panel(constraints:BL.CENTER){
			migLayout(constraints: "growx, wrap, gaptop 0")
			toggleButton(id:'clearB',
				text:getMessage("view.btn.clear"),
				actionPerformed:controller.clear,constraints: "wrap",
				foreground: Color.BLUE,
				mouseEntered: {timelineRun.play()},
				mouseExited:  {timelineRun.playReverse()}
			){
				timeline(clearB, id: "timelineRun", duration: 2500) {
					interpolatedProperty("foreground", from: Color.BLUE, to: Color.RED)
				}
			}
			clearB.font = clearB.font.deriveFont(Font.BOLD,16f)

			//page operation panel
			this.registerBeanFactory("jPagingPanel", com.wasurenairoku.swing.JPagingPanel.class)
			jPagingPanel(id:'controlP',currentPage:bind{model.control_page},maxPageSize:bind{model.control_page_max},constraints: "span,growx")
			controlP.addPagingListener([
					actionPerformed: { event ->
						model.getPageList(event.getNewPage())
					}
			] as com.wasurenairoku.swing.PagingListener)
		}
	}
	panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)) {
		scrollPane(id: 'dispTableSc', constraints: "span,growx,growy,wrap",dropTarget:new CsvDropTarget(controller.csvLoad) ) {
			table(id: 'dispTable',dropTarget:new CsvDropTarget(controller.csvLoad)) {
					tableFormat = defaultTableFormat(columnNames: ["dispId", "path"])
					eventTableModel(source: model.dispTableList, format: tableFormat)
					installTableComparatorChooser(source: model.dispTableList)
			}
		}
	}

	view.dispTable.selectionModel.addListSelectionListener([
		valueChanged: { event ->
		   def selectedIndex = view.dispTable.selectedRow
		   if( selectedIndex == -1 )return
		  	selectedIndex = model.dispTableList.getSourceIndex(selectedIndex)
		  	dispId= model.dispTableList[selectedIndex].dispId
				path = model.dispTableList[selectedIndex].path
				println "[${dispId}]:${path}"
				controller.brows(dispId,path)
		}
	 ] as javax.swing.event.ListSelectionListener )


	dispTable.getColumnModel().getColumn(0).minWidth=100
	dispTable.getColumnModel().getColumn(0).maxWidth=100


}


/*
frame.windowOpened={println 'Opened'}//initialize
frame.windowClosing={println 'closing'}//menu closing
frame.windowClosed={println 'closed'}//dispose calling
*/
//normal＝＞min
frame.windowIconified={
	println 'Iconified'
}	
//min＝＞normal
frame.windowDeiconified={
	println 'Deiconified'
}

//active
frame.windowActivated={
	println 'Activated'
}

//not active
frame.windowDeactivated={
	println 'Deactivated'
}


