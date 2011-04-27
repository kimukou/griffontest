eventCompileEnd = {msg->
  println "==compile end(${msg})=="
	srcDir ="${basedir}/griffon-app/resources"
	destDir = "${classesDir}"

	ant.copy(file:"${srcDir}/config.properties_t",tofile:"${destDir}/config.properties",overwrite:true)
	//ant.copy(file:"${srcDir}/config.properties_n",tofile:"${destDir}/config.properties",overwrite:true)
}
