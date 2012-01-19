//
// This script is executed by Griffon when the plugin is uninstalled from project.
// Use this script if you intend to do any additional clean-up on uninstall, but
// beware of messing up SVN directories!
//

// check to see if we already have a OpenniGriffonAddon
def configText = '''root.'OpenniGriffonAddon'.addon=true'''
if(builderConfigFile.text.contains(configText)) {
    println 'Removing OpenniGriffonAddon from Builder.groovy'
    builderConfigFile.text -= configText
}