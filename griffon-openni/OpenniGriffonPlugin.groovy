class OpenniGriffonPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Griffon the plugin is designed for
    def griffonVersion = '0.9.4 > *' 
    // the other plugins this plugin depends on
    def dependsOn = [
			'jogl-compat':0.1,
			'processing':0.4
		]
    // resources that are included in plugin packaging
    def pluginIncludes = []
    // the plugin license
    def license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    def toolkits = ['swing']
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    def platforms = ['linux','windows','windows64', 'macosx']

    // TODO Fill in these fields
    def author = 'kimukou.buzz'
    def authorEmail = 'kimukou.buzz@gmail.com'
    def title = 'OpenNI Plguin'
    def description = '''
Griffon based simple-openni
'''

    // URL to the plugin's documentation
    def documentation = 'http://griffon.codehaus.org/Openni+Plugin'
}
