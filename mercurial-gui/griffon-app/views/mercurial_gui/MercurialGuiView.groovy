package mercurial_gui

import java.awt.Dimension
import java.awt.Toolkit
import java.awt.event.ActionListener
import org.jvnet.flamingo.common.JCommandButton
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind
import org.jvnet.flamingo.common.RichTooltip
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon
import org.jvnet.flamingo.ribbon.JRibbonBand
import org.jvnet.flamingo.ribbon.RibbonTask
import org.jvnet.flamingo.ribbon.JRibbonComponent
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryPrimary
import org.jvnet.flamingo.ribbon.RibbonApplicationMenu
import org.jvnet.flamingo.ribbon.RibbonElementPriority
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies
import org.jvnet.flamingo.ribbon.JFlowRibbonBand

application(id: 'mainFrame', title: 'mercurial-gui', size: [900, 600], locationByPlatform: true,
    iconImage: Toolkit.defaultToolkit.getImage('ribbon-main-icon-16.png')) {


    JFlowRibbonBand band = new JFlowRibbonBand('ribbon band',
        ImageWrapperResizableIcon.getIcon(
            Toolkit.defaultToolkit.getImage('griffon-icon-48x48.png'),
            [16, 16] as Dimension))

    band.addFlowComponent new JRibbonComponent(ImageWrapperResizableIcon.getIcon(
        Toolkit.defaultToolkit.getImage('griffon-icon-48x48.png'),
        [16, 16] as Dimension), 'root directory',
        textField(id: 'rootDirectory', columns: 50, text: app.config.mercurial.rootDirectory.absolutePath, editable: true))
    bind(source: rootDirectory, sourceProperty: 'text', target: model, targetProperty: 'rootDirectory')

    JCommandButton button = new JCommandButton("Validate",
        ImageWrapperResizableIcon.getIcon(
            Toolkit.defaultToolkit.getImage('griffon-icon-48x48.png'),
            [16, 16] as Dimension))
    button.addActionListener(controller.&assignRootDirectory as ActionListener)
    band.addFlowComponent button

    current.ribbon.addTask new RibbonTask('Configuration', band)


    RibbonApplicationMenuEntryPrimary amEntryNew = new RibbonApplicationMenuEntryPrimary(
        ImageWrapperResizableIcon.getIcon(
            Toolkit.defaultToolkit.getImage('griffon-icon-48x48.png'),
            [48, 48] as Dimension),
        "New", {event -> println "newMenu??? ${event?.dump()}"} as ActionListener, CommandButtonKind.ACTION_ONLY)

    RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu()
//    applicationMenu.addMenuEntry(amEntryNew)

    current.ribbon.applicationMenu = applicationMenu

		println "[V]model.treePanel=${model.treePanel}"

    splitPane(continuousLayout: true) {
        widget(model.treePanel)
        widget(model.filePanel)
    }
}
