import org.codehaus.griffon.cli.GriffonScriptRunner as GSR
import org.codehaus.griffon.plugins.GriffonPluginUtils

ant.property(environment: "env")
javafxHome = ant.antProject.properties."env.JAVAFX_HOME"

includeTargets << griffonScript("Package")
javaFxRuntimeName="JavaFX Runtime"
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
    verifyJavaFXHome()
		
		config.griffon.extensions.jnlpUrls.each{
			println it
		}
    if(!config.griffon.extensions.jnlpUrls.containsKey(javaFxRuntimeName)) {
        config.griffon.extensions.jnlpUrls.put(javaFxRuntimeName, javaFxUrl)
    }
}

eventCompileStart = { type ->
	  println "==compile start(${type})=="

    verifyJavaFXHome()

    def javafxlibs = ant.fileset(dir: "${javafxHome}/lib/shared", includes: "*.jar")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)
    javafxlibs = ant.fileset(dir: "${javafxHome}/lib/desktop",includes: "*.jar", excludes: "*rt15.jar, *.so,*.dll")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)

    ant.taskdef(resource: "javafxc-ant-task.properties",
                classpathref: "griffon.compile.classpath")

    ant.property(name: "javafx.compiler.classpath", refid: "griffon.compile.classpath")
    def javafxCompilerClasspath = ant.antProject.properties.'javafx.compiler.classpath'


    //if( type != "source" ) return
    def javafxSrc = "${basedir}/src/javafx"
//println "a-1"
    if(!new File(javafxSrc).exists()) return
    //compileCommons()
//println "a-2"
    //if(sourcesUpToDate(javafxSrc, classesDirPath, ".fx")) return
//println "a-3"
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


eventCompileEnd = {
	  println "==compile end=="

	//jar
		destDir = "$basedir/staging"
		ant.mkdir(dir:destDir)
		libDir  = "$javafxHome/lib/desktop/"
	  ant.copy(todir: destDir) {
			fileset(dir: libDir, includes: '*.jar',excludes:'rt15.jar')
		}
		libDir  = "$javafxHome/lib/shared/"
	  ant.copy(todir: destDir) {
			fileset(dir: libDir, includes: '*.jar')
		}

	//native-dll
		destDir = "$basedir/staging/windows/native"
		ant.mkdir(dir:destDir)
		libDir  = "$javafxHome/lib/desktop/"
	  ant.copy(todir: destDir) {
			fileset(dir: libDir, includes: '*.dll,*.so')
		}

		libDir  = "$javafxHome/lib/shared/"
	  ant.copy(todir: destDir) {
			fileset(dir: libDir, includes: '*.dll.*.so')
		}
}

/*
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
*/

/**
 * Detects whether we're compiling the fx plugin itself
 */

private void verifyJavaFXHome() {
    if( !javafxHome ) {
       ant.fail(message: "  [griffon-fx] environment variable $JAVAFX_HOME is undefined")
    }
}
