import org.codehaus.griffon.cli.GriffonScriptRunner as GSR
import org.codehaus.griffon.plugins.GriffonPluginUtils

ant.property(environment: "env")
javafxHome = ant.antProject.properties."env.JAVAFX_HOME"

includeTargets << griffonScript("Package")
includePluginScript("lang-bridge", "CompileCommons")
javaFxUrl = "http://dl.javafx.com/1.2/javafx-rt.jnlp"

/*
eventPackagePluginStart = { pluginName, plugin ->
    def destFileName = "lib/griffon-${pluginName}-addon-${plugin.version}.jar"
    ant.delete(dir: destFileName, quiet: false, failOnError: false)
    ant.jar(destfile: destFileName) {
        fileset(dir: classesDirPath) {
            exclude(name: '_*.class')
            exclude(name: '*GriffonPlugin.class')
        }
    }
}
*/

eventCopyLibsEnd = { jardir ->
    if( compilingJavaFXPlugin() ) return
    verifyJavaFXHome()
    ant.echo(message: "[fx] Copying FX jar files from ${getPluginDirForName('fx').file}/lib")

    ant.fileset(dir:"${getPluginDirForName('fx').file}/lib/", includes: "*.jar").each {
        griffonCopyDist(it.toString(), jardir)
    }

    if(!config.griffon.extensions.jnlpUrls.contains(javaFxUrl)) {
        config.griffon.extensions.jnlpUrls << javaFxUrl
    }
}

eventCompileStart = { type ->
    verifyJavaFXHome()

    def javafxlibs = ant.fileset(dir: "${javafxHome}/lib/shared", includes: "*.jar")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)
    javafxlibs = ant.fileset(dir: "${javafxHome}/lib/desktop", excludes: "*rt15.jar, *.so")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)

    if( compilingJavaFXPlugin() ) return

    ant.taskdef(resource: "javafxc-ant-task.properties",
                classpathref: "griffon.compile.classpath")

    ant.property(name: "javafx.compiler.classpath", refid: "griffon.compile.classpath")
    def javafxCompilerClasspath = ant.antProject.properties.'javafx.compiler.classpath'


    if( type != "source" ) return
    def javafxSrc = "${basedir}/src/javafx"
    if(!new File(javafxSrc).exists()) return
    compileCommons()

    if(sourcesUpToDate(javafxSrc, classesDirPath, ".fx")) return
    def javafxSrcEncoding = buildConfig.javafx?.src?.encoding ?: 'UTF-8'

    ant.echo(message: "[fx] Compiling JavaFX sources with JAVAFX_HOME=${javafxHome} to $classesDirPath")
    try {
        ant.javafxc(destdir: classesDirPath,
                    classpathref: "griffon.compile.classpath",
                    compilerclasspath: javafxCompilerClasspath,
                    encoding: javafxSrcEncoding,
                    srcdir: javafxSrc)
    }
    catch (Exception e) {
        ant.fail(message: "Could not compile JavaFX sources: " + e.class.simpleName + ": " + e.message)
    }
}

eventRunAppStart = {
    ant.echo(message: "[fx] Copying additional JavaFX jars to $jardir")
    ant.fileset(dir: "${javafxHome}/lib/shared", includes: "*.jar").each {
        griffonCopyDist(it.toString(), jardir)
    }
    ant.fileset(dir: "${javafxHome}/lib/desktop", excludes: "*rt15.jar, *.so").each {
        griffonCopyDist(it.toString(), jardir)
    }
}

eventRunAppEnd = {
    ant.echo(message: "[fx] Deleting $jardir")
    ant.delete(dir: jardir)
}

/**
 * Detects whether we're compiling the fx plugin itself
 */
private boolean compilingJavaFXPlugin() { getPluginDirForName("fx") == null }

private void verifyJavaFXHome() {
    if( compilingJavaFXPlugin() ) return
    if( !javafxHome ) {
       ant.fail(message: "  [griffon-fx] environment variable $JAVAFX_HOME is undefined")
    }
}
