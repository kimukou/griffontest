ant.property(environment: "env")
javafxHome = ant.antProject.properties."env.JAVAFX_HOME"


eventCompileStart = { type ->

    def javafxlibs = ant.fileset(dir: "${javafxHome}/lib/shared", includes: "*.jar")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)
    javafxlibs = ant.fileset(dir: "${javafxHome}/lib/desktop", excludes: "*rt15.jar, *.so ,*.dll")
    ant.project.references["griffon.compile.classpath"].addFileset(javafxlibs)

    ant.taskdef(resource: "javafxc-ant-task.properties",
                classpathref: "griffon.compile.classpath")

    ant.property(name: "javafx.compiler.classpath", refid: "griffon.compile.classpath")
    def javafxCompilerClasspath = ant.antProject.properties.'javafx.compiler.classpath'
    if( type != "source" ) return
    def javafxSrc = "${basedir}/src/javafx"
    if(!new File(javafxSrc).exists()) return

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
	ant.mkdir(dir:destDir)
	destDir = "$basedir/staging/windows/native"
	libDir  = "$javafxHome/lib/desktop/"
  ant.copy(todir: destDir) {
		fileset(dir: libDir, includes: '*.dll,*.so')
	}

	libDir  = "$javafxHome/lib/shared/"
  ant.copy(todir: destDir) {
		fileset(dir: libDir, includes: '*.dll.*.so')
	}
}

eventPackageEnd = {
  println "==package end=="
}

