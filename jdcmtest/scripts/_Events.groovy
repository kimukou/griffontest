eventCompileEnd = {
	destDir = "${basedir}/staging"
	if(!new File("${destDir}").exists() || !new File("${destDir}/brimmywall.dcm").exists() ){
		new File("${destDir}").mkdirs()
		new File("${destDir}/brimmywall.dcm") << new File("${basedir}/testdata/brimmywall.dcm").readBytes()
	}
}


eventPackageEnd = {
  println "==package end=="
  destDir = "${basedir}/dist/jar"
	if(!new File("${destDir}").exists() || !new File("${destDir}/brimmywall.dcm").exists() ){
		new File("${destDir}").mkdirs()
		new File("${destDir}/brimmywall.dcm") << new File("${basedir}/testdata/brimmywall.dcm").readBytes()
		new File("${destDir}/exec.bat") << new File("${basedir}/testdata/exec.bat").readBytes()
	}
}

