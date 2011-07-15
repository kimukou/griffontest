import groovy.swing.SwingBuilder
import griffon.util.GriffonPlatformHelper
import org.codehaus.griffon.mercurial.service.MercurialService

/*
 * This script is executed inside the EDT, so be sure to
 * call long running code in another thread.
 *
 * You have the following options
 * - SwingBuilder.doOutside { // your code  }
 * - Thread.start { // your code }
 * - SwingXBuilder.withWorker( start: true ) {
 *      onInit { // initialization (optional, runs in current thread) }
 *      work { // your code }
 *      onDone { // finish (runs inside EDT) }
 *   }
 *
 * You have the following options to run code again inside EDT
 * - SwingBuilder.doLater { // your code }
 * - SwingBuilder.edt { // your code }
 * - SwingUtilities.invokeLater { // your code }
 */


GriffonPlatformHelper.tweakForNativePlatform(app)
SwingBuilder.lookAndFeel(org.jvnet.substance.skin.SubstanceSaharaLookAndFeel.class.name)

println "INITIALIZE ${app.dump()}"
def startDir = System.getProperty("griffon.start.dir")
if( startDir?.startsWith('"') && startDir?.endsWith('"') ) {
    startDir = startDir[1..-2]
} else {
    println"Props=${System.getProperties()}"
    startDir = System.getProperty("base.dir") 
}

println "StartDir = $startDir"

app.config.mercurial = new MercurialService(mercurialExecutable: '/usr/local/bin/hg', rootDirectory: new File(startDir))
