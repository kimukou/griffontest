eventStatusFinal = { msg ->
  println "==eventStatusFinal(${msg})=="
  growlNotify(msg)
}
 
eventStatusUpdate = { msg ->
  println "==eventStatusUpdate(${msg})=="
  growlNotify(msg)
}
 
growlNotify = { message ->
    println "==growlNotify(${message})=="
    return

    //path="/usr/local/bin/growlnotify"
    path="c:/opt/Growl/growlnotify.exe"
    if(!new File(path).exists())return
    imgpath="${basedir}/griffon-app/resources/griffon-icon-32x32.png"

    ant.exec(executable:path) {
          arg(value:"/t:Griffon")
          arg(value:"/i:\"${imgpath}\"")
          arg(value:"\"${message}\"")
    }
}


eventCompileStart = {msg->
  println "==compile start(${msg})=="
}

eventCompileEnd = {msg->
  println "==compile end(${msg})=="
  growlNotify("eventCompileEnd")

	destDir = "${basedir}/staging"
	copySetting(destDir)

	//need TTF & ddl Copy (defaul griffon-app/resources only image file)
	srcDir ="${basedir}/griffon-app/resources"
	destDir = "${classesDir}"
	ant.copy(todir: destDir, overwrite: true ) {
		fileset(dir: srcDir, includes: '*.ddl,*.TTF')
	}
}


eventPackageStart={msg->
  println "==package start(${msg})=="
}

eventPackageEnd = {msg->
  println "==package end(${msg})=="
  growlNotify("eventPackageEnd")

	destDir = "${basedir}/dist/jar"
	copySetting(destDir)
}


//--------------------------------------------------------------------------------------------
eventGenerateJNLPStart = {
  println "==onGenerateJNLPStart(${packageType})=="
  if(packageType == 'applet') {
    destDir="${basedir}/dist/applet"
    buildConfig.griffon.webstart.codebase = "${new File(destDir).toURI().toASCIIString()}"
  } else if(packageType == 'webstart') {
    destDir="${basedir}/dist/webstart"
    buildConfig.griffon.webstart.codebase = "${new File(destDir).toURI().toASCIIString()}"
  }
}



//--------------------------------------------------------------------------------------------
copySetting ={destDir->
  println "destDir=${destDir}"

 	//ROOT
  srcDir  = "${basedir}/setting"
  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.sh,*.txt,*.bat,*.pdf,*.inf' ,excludes:'autorun.bat,Autorun.inf')
  }

	//classes copy
	setting_dir = "${classesDir}"
	ant.mkdir(dir:setting_dir)
	ant.copy(todir: setting_dir, overwrite: true ) {
		fileset(dir: srcDir, includes: '*.sql,*.properties,*.xml')
	}


	//other
	setting_dir="${destDir}/setting"
	ant.mkdir(dir:setting_dir)
	ant.copy(todir: setting_dir, overwrite: true ) {
		fileset(dir: srcDir, includes: '*.properties,*.xml')
	}

}

copySettingExe={destDir->
	println "==copySettingExe(${distDir})=="
	println "destDir=${destDir}"
  srcDir  = "${basedir}/setting"
  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.sh,*.txt,*.bat,*.pdf,*.inf' ,excludes:'start.bat,start.sh')
  }

	//classes copy
	setting_dir = "${classesDir}"
	ant.mkdir(dir:setting_dir)
	ant.copy(todir: setting_dir, overwrite: true ) {
		fileset(dir: srcDir, includes: '*.sql,*.properties,*.xml')
	}


	//other
	setting_dir="${destDir}/setting"
	ant.mkdir(dir:setting_dir)
	ant.copy(todir: setting_dir, overwrite: true ) {
		fileset(dir: srcDir, includes: '*.properties,*.xml')
	}
}

//--------------------------------------------------------------------------------------------
//installer plugin

eventPreparePackageStart={ type->
  println "==eventPreparePackageStart [${type}]=="
}


eventPreparePackageEnd={ type->
  println "==eventPreparePackageEnd [${type}]=="
	println "sign=${buildConfig.griffon.jars.sign}"
	println "pack=${buildConfig.griffon.jars.pack}"

  switch(type){
    case "windows":
      tmplfile="${basedir}/setting/${griffonAppName}.jsmooth"
      dstfile="${basedir}/target/installer/jsmooth/${griffonAppName}.jsmooth"
      ant.copy(tofile:dstfile,file:tmplfile, overwrite: true )

			//2011/04/08 kimukou_26 skip jar signature add start
			buildConfig.griffon.jars.sign = false
			buildConfig.griffon.jars.pack  = false
			//2011/04/08 kimukou_26 skip jar signature add end
      break

    case "izpack":
      destDir = "${basedir}/installer/izpack/resources/"
      copySetting(destDir)
      ant.copy( todir: "${basedir}/installer/izpack/resources", overwrite: true ) {
        fileset( dir: "${basedir}/src/installer/izpack/resources", includes: "**" )
      }
      ant.replace( dir: "${basedir}/installer/izpack/resources" ) {
        replacefilter(token: "@app.name@", value: griffonAppName)
        replacefilter(token: "@app.version@", value: griffonAppVersion)
      }
      break
  }

  //growlNotify("eventPreparePackageEnd(${type})")

}


eventCreatePackageStart = { type->
  println "==eventCreatePackageStart ${type}=="
}

eventCreatePackageEnd = { type->
  println "==eventCreatePackageEnd  ${type}=="
  switch(type){
    case "windows":
    destDir = "${basedir}/dist/windows"
    copySettingExe(destDir)
    break;
  }

  growlNotify("eventCreatePackageEnd(${type})")
}

