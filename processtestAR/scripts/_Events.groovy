eventCompileEnd = {
	libDir  = "${basedir}/lib/native"
	destDir = "${basedir}/staging/windows64/native"

  ant.copy(todir: destDir) {
		fileset(dir: libDir, includes: '*.dll')
	}

	libDir  = "${basedir}/lib/libraries/opengl/library"
  ant.copy(todir: destDir) {
		fileset(dir: libDir, includes: '*.dll,*.so')
	}
}

eventPackageEnd = {
  println "==package end=="
}

