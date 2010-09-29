eventCompileEnd = {
  println "==compile end=="
	dataDir  = "${basedir}/testdata"
	destDir = "${basedir}/staging"
  ant.copy(todir: destDir) {
		fileset(dir: dataDir, includes: '*.dcm')
	}
}


eventPackageEnd = {
  println "==package end=="
	dataDir  = "${basedir}/testdata"
	destDir = "${basedir}/dist/jar"
  ant.copy(todir: destDir) {
		fileset(dir: dataDir, includes: '*.dcm,*.bat,*.sh')
	}
}

