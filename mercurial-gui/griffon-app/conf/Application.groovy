application {
	title="MercurialGui"
	startupGroups=["mercurial_gui"]
	autoShutdown=true
	frameClass="org.jvnet.flamingo.ribbon.JRibbonFrame"
}
mvcGroups {
	FilePanel {
		model="org.codehaus.griffon.mercurial.FilePanelModel"
		controller="org.codehaus.griffon.mercurial.FilePanelController"
		view="org.codehaus.griffon.mercurial.FilePanelView"
	}
	TreePanel {
		model="org.codehaus.griffon.mercurial.TreePanelModel"
		controller="org.codehaus.griffon.mercurial.TreePanelController"
		view="org.codehaus.griffon.mercurial.TreePanelView"
	}
	'mercurial_gui' {
		model="mercurial_gui.MercurialGuiModel"
		controller="mercurial_gui.MercurialGuiController"
		view="mercurial_gui.MercurialGuiView"
	}
}

def mercurial
