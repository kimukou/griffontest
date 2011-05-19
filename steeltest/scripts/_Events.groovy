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
    break;
  }

  growlNotify("eventCreatePackageEnd(${type})")
}

