//------------------------------------------------------------------
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

  libDir  = "${basedir}/lib/native"
  destDir = "${basedir}/staging/windows/native"

  ant.copy(todir: destDir) {
    fileset(dir: libDir, includes: '*.dll')
  }

  //libDir  = "${basedir}/lib/libraries/opengl/library"
  //ant.copy(todir: destDir) {
  //  fileset(dir: libDir, includes: '*.dll,*.so')
  //}

}


eventPackageStart={msg->
  println "==package start(${msg})=="

  libDir  = "${basedir}/lib/native"
  //doWithPlatform(platform,libDir)
}

eventPackageEnd = {msg->
  println "==package end(${msg})=="
  growlNotify("eventPackageEnd")
}



doWithPlatform = { platformOs,libDir ->
    println "copySetting $platformOs"

    if(!platformOs.startsWith('windows'))return

    nativeLibs=["$libDir/dsj.dll",]
    nativeLibs64=["$libDir/64bit/dsj.dll",]

    new File("$libDir/jogl-compat/windows/native").eachFile{
      nativeLibs<< it.absolutePath
      nativeLibs64<< it.absolutePath
    }

    def origPlatformOs = platformOs
    if(origPlatformOs.endsWith('64')){
       platformOs -= '64'
    }

    def rs = buildConfig.griffon.extensions.resources['windows']
    if(!rs) {
       if(origPlatformOs.endsWith('64')){
        co.nativelibs = nativeLibs64
       }
       else{
        co.nativelibs = nativeLibs
       }
       buildConfig.griffon.extensions.resources[origPlatformOs] = co
    }
    else{
        if(!rs.nativeLibs) rs.nativeLibs = [] 
        if(origPlatformOs.endsWith('64'))rs.nativeLibs.addAll(nativeLibs64)
        else rs.nativeLibs.addAll(nativeLibs)
    }

    println "rs=${rs.dump()}"
}


def copySetting(destDir){
  println "==copySetting(${destDir})=="

  if(platform.startsWith('windows')==false)return

//dsj.dll
  srcDir =""
  if(platform.endsWith('64'))srcDir  = "${basedir}/lib/native/64bit"
  else srcDir  = "${basedir}/lib/native"

  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.dll')
  }

//jogl-compat
  srcDir  = "${basedir}/lib/jogl-compat/windows/native"
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.dll')
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
      //2011/04/08 kimukou_26 skip jar signature add start
      buildConfig.griffon.jars.sign = false
      buildConfig.griffon.jars.pack  = false
      //2011/04/08 kimukou_26 skip jar signature add end

      tmplfile="${basedir}/setting/${griffonAppName}.jsmooth"
      dstfile="${basedir}/target/installer/jsmooth/${griffonAppName}.jsmooth"
      ant.copy(tofile:dstfile,file:tmplfile, overwrite: true )
      break

    case "izpack":
      //2011/04/08 kimukou_26 skip jar signature add start
      buildConfig.griffon.jars.sign = false
      buildConfig.griffon.jars.pack  = false
      //2011/04/08 kimukou_26 skip jar signature add end

      installerWorkDir = "${projectWorkDir}/installer/izpack"
      binaryDir = installerWorkDir + '/binary'
      installerResourcesDir = installerWorkDir + '/resources'

      copySetting("$binaryDir/lib/windows/native")
      ant.copy( todir: installerResourcesDir, overwrite: true ) {
        fileset( dir: "${basedir}/src/installer/izpack/resources", includes: "**" )
      }
      ant.replace( dir: installerResourcesDir ) {
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
    copySetting(destDir)
    break;
  }

  growlNotify("eventCreatePackageEnd(${type})")
}

import java.text.SimpleDateFormat

eventCreatePackageIzpackEnd = {
    Date date = new Date()
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy=MM=dd");
    String tstamp = sdf.format(date)
    ant.move(file: "${distDir}/izpack/${griffonAppName}-${griffonAppVersion}-installer.jar",
             tofile: "${distDir}/izpack/${griffonAppName}-${griffonAppVersion}-installer-${tstamp}.jar")
}

