package mercurial_gui

import groovy.beans.Bindable

class MercurialGuiModel {
    @Bindable String rootDirectory
		def treePanel
		def filePanel
}
