/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Andres Almiray
 */

import griffon.util.PluginBuildSettings
import static griffon.util.GriffonApplicationUtils.is64Bit
import static griffon.util.GriffonApplicationUtils.isWindows

import groovy.xml.MarkupBuilder

includeTargets << griffonScript("Init")
includeTargets << griffonScript("_PluginDependencies")

target(updateEclipseClasspath: "Update the application's Eclipse classpath file") {
    updateEclipseClasspathFile()
}
setDefaultTarget('updateEclipseClasspath')

updateEclipseClasspathFile = { newPlugin = null ->
    println "Updating Eclipse classpath file..."

    if(newPlugin) event('SetClasspath', [classLoader])
    griffonSettings.resetDependencies()
    def visitedDependencies = []

    //String userHomeRegex = isWindows ? userHome.toString().replace('\\', '\\\\') : userHome.toString()
    //String griffonHomeRegex = isWindows ? griffonHome.toString().replace('\\', '\\\\') : griffonHome.toString()
    String userHomeRegex = isWindows ? userHome.toString().replace('\\', '/') : userHome.toString()
    String griffonHomeRegex = isWindows ? griffonHome.toString().replace('\\', '/') : griffonHome.toString()

//println "userHomeRegex=$userHomeRegex"
//println "griffonHomeRegex=$griffonHomeRegex"

    String indent = '    '
    def writer = new PrintWriter(new FileWriter('.classpath'))
    def xml = new MarkupBuilder(new IndentPrinter(writer, indent))
    xml.setDoubleQuotes(true)
    xml.mkp.xmlDeclaration(version: '1.0', encoding: 'UTF-8')
    xml.mkp.comment("Auto generated on ${new Date()}")
    xml.mkp.yieldUnescaped '\n'
    xml.classpath {
        mkp.yieldUnescaped("\n${indent}<!-- source paths -->")
        ['griffon-app', 'src', 'test'].each { base ->
            new File(base).eachDir { dir ->
                if (! (dir.name =~ /^\..+/) && dir.name != 'templates') {
                    classpathentry(kind: 'src', path: "${base}/${dir.name}")
                }
            }
        }
        buildConfig.eclipse?.classpath?.include?.each { dir ->
            File target = new File(basedir, dir)
            if(target.exists()) classpathentry(kind: 'src', path: dir)
        }        

        mkp.yieldUnescaped("\n${indent}<!-- output paths -->")
        classpathentry(kind: 'con', path: 'org.eclipse.jdt.launching.JRE_CONTAINER')
        if(isWindows){
          classesDirPath = classesDirPath.replace('\\', '/')
        }
        classpathentry(kind: 'output', path: classesDirPath.replaceFirst(~/$userHomeRegex/, 'USER_HOME'))
        
        def normalizeFilePath = { file ->
            String path = file.absolutePath
            if(isWindows){
              path = path.replace('\\', '/')
            }
            String originalPath = path
            path = path.replaceFirst(~/$griffonHomeRegex/, 'GRIFFON_HOME')
            path = path.replaceFirst(~/$userHomeRegex/, 'USER_HOME')
            boolean var = path != originalPath
            originalPath = path
            if(isWindows){
              basedirPath = griffonSettings.baseDir.path.replace('\\', '/')
//println "basedirPath=$basedirPath"
              path = path.replaceFirst(~/${basedirPath}(\/)/, '')
//println "originalPath=$originalPath"
//println "path=$path"
            }
            else{
              path = path.replaceFirst(~/${griffonSettings.baseDir.path}(\\|\/)/, '')
            }
            var = path == originalPath && !path.startsWith(File.separator)
//println "[○]var=$var"
//println "[○]path=$path"
//println ""
            [kind: var? 'var' : 'lib', path: path]
        }
        def visitDependencies = {List dependencies ->
            dependencies.each { File f ->
                if(visitedDependencies.contains(f)) return
                visitedDependencies << f
                Map pathEntry = normalizeFilePath(f)
                classpathentry(kind: pathEntry.kind, path: pathEntry.path)   
            }    
        }
               
        mkp.yieldUnescaped("\n${indent}<!-- runtime -->")
        visitDependencies(griffonSettings.runtimeDependencies)
        mkp.yieldUnescaped("\n${indent}<!-- test -->")
        visitDependencies(griffonSettings.testDependencies)
        mkp.yieldUnescaped("\n${indent}<!-- compile -->")
        visitDependencies(griffonSettings.compileDependencies)
        mkp.yieldUnescaped("\n${indent}<!-- build -->")
        visitDependencies(griffonSettings.buildDependencies)
        
        def visitPlatformDir = {libdir ->
            def nativeLibDir = new File("${libdir}/${platform}")
            if(nativeLibDir.exists()) {
                nativeLibDir.eachFileMatch(~/.*\.jar/) { file ->
                    Map pathEntry = normalizeFilePath(file)
                    classpathentry(kind: pathEntry.kind, path: pathEntry.path)   
                }
            }
            nativeLibDir = new File("${libdir}/${platform[0..-3]}")
            if(is64Bit && nativeLibDir.exists()) {
                nativeLibDir.eachFileMatch(~/.*\.jar/) { file ->
                    Map pathEntry = normalizeFilePath(file)
                    classpathentry(kind: pathEntry.kind, path: pathEntry.path)   
                }
            }
        }

        mkp.yieldUnescaped("\n${indent}<!-- platform specific -->")
        visitPlatformDir(new File("${basedir}/lib"))

        doWithPlugins{ pluginName, pluginVersion, pluginDir ->
            if("${pluginName}-${pluginVersion}" == newPlugin) return
            def libDir = new File(pluginDir, 'lib')
            visitPlatformDir(libDir)
            
        }
        if(newPlugin) {
            def libDir = new File([pluginsHome, newPlugin, 'lib'].join(File.separator))
            visitPlatformDir(libDir)
        }

        //linked plugin 2011/07/27 kimukou.buzz add start
        //def config  = new ConfigSlurper().parse(new File('griffon-app/conf/BuildConfig.groovy').toURL())
        config.grails.plugin.location.each{base ->
            //println "base=[$base],${base.class.name}"
            def libDir = new File([base.value,'lib'].join(File.separator))
            println libDir.dump()
            visitPlatformDir(libDir)
        }
        //linked plugin 2011/07/27 kimukou.buzz add end


        //link src
        mkp.yieldUnescaped("\n${indent}<!-- link Entry -->")
                //.project update
                List linkEntry = updateEclipseProjectFile(newPlugin)
                linkEntry.each(){
                    classpathentry(kind: 'src', path: it)
                }

    }
}


updateEclipseProjectFile = { newPlugin = null ->
    println "Updating Eclipse project file..."

    List linkEntry =[]

    if(newPlugin) event('SetProjectpath', [classLoader])

    boolean isWindows = System.getProperty("os.name").matches("Windows.*")

    String userHomeRegex = isWindows ? userHome.toString().replace('\\', '/') : userHome.toString()
    String griffonHomeRegex = isWindows ? griffonHome.toString().replace('\\', '/') : griffonHome.toString()

    String indent = '    '
    def writer = new PrintWriter(new FileWriter('.project'))
    def xml = new MarkupBuilder(new IndentPrinter(writer, indent))
    xml.setDoubleQuotes(true)
    xml.mkp.xmlDeclaration(version: '1.0', encoding: 'UTF-8')
    //not gooding conflict problem
    //xml.mkp.comment("Auto generated on ${new Date()}")
    xml.mkp.yieldUnescaped '\n'
    


    def normalizeFilePathP = { file ->
        String path = file.absolutePath
        if(isWindows){
          path = path.replace('\\', '/')
        }
				//println "[normalizeFilePathP]path=$path"
        String originalPath = path
        path = path.replaceFirst(~/$griffonHomeRegex/, 'GRIFFON_HOME')
        path = path.replaceFirst(~/$userHomeRegex/, 'USER_HOME')
        boolean var = path != originalPath
        originalPath = path
        path == originalPath && !path.startsWith(File.separator)
        return path.toString()
    }

    def visitPlatformDirP = {mkp,pluginName, pluginVersion,baseDir ->
      if(!baseDir.exists())return
			def exarr=['templates','docs','conf','views','i18n']
      baseDir.eachDir { dir ->
        if (! (dir.name =~ /^\..+/) && !(dir.name in exarr) ) {
            mkp.yieldUnescaped("\n${indent}${indent}<link>")
            mkp.yieldUnescaped("\n${indent}${indent}${indent}<name>${pluginName}-${pluginVersion}-${baseDir.name}-${dir.name}</name>")
            mkp.yieldUnescaped("\n${indent}${indent}${indent}<type>2</type>")
            mkp.yieldUnescaped("\n${indent}${indent}${indent}<location>${normalizeFilePathP(dir)}</location>")
            mkp.yieldUnescaped("\n${indent}${indent}</link>")
                        linkEntry.add("${pluginName}-${pluginVersion}-${baseDir.name}-${dir.name}")
        }
      }
			baseDir.eachFile{ file ->
					if (file.name =~ /^(.*)-sources.jar/) {
						println file
					}
			}
    }

    xml.projectDescription {
      mkp.yieldUnescaped("\n${indent}<name>$griffonAppName</name>")
      comment()
      projects()
      mkp.yieldUnescaped("\n\n${indent}<!-- buildSpec -->")

      mkp.yieldUnescaped("\n${indent}<buildSpec>")
         //buildCommand(){
         //   mkp.yieldUnescaped("\n${indent}<name>org.eclipse.wst.common.project.facet.core.builder</name>")
         //   arguments()
         //}
         buildCommand(){
            mkp.yieldUnescaped("\n${indent}<name>org.eclipse.jdt.core.javabuilder</name>")
            arguments()
         }
      mkp.yieldUnescaped("\n${indent}</buildSpec>")
      mkp.yieldUnescaped("\n\n${indent}<!-- natures -->")
      natures(){
            //nature('com.springsource.sts.grails.core.nature')
            nature('org.eclipse.jdt.groovy.core.groovyNature')
            nature('org.eclipse.jdt.core.javanature')
            //nature('org.eclipse.wst.common.project.facet.core.nature')
      }

      mkp.yieldUnescaped("\n\n${indent}<!-- linkedResources -->")
      mkp.yieldUnescaped("\n${indent}<linkedResources>")

      //linked plugin 2011/07/27 kimukou.buzz add start
      def config  = new ConfigSlurper().parse(new File('griffon-app/conf/BuildConfig.groovy').toURL())
      config.griffon.plugin.location.each{
          println "it=[$it],${it.class.name}"
          def pluginDirTmp = new File(it.value)
          //def pluginName = it.key
          File pluginXmlFile=new File([it.value,"plugin.xml"].join(File.separator))
          def pluginXml = new XmlSlurper().parse(pluginXmlFile)
          def pluginName = pluginXml.@name.toString()
          def pluginVersion = pluginXml.@version.toString()
          //def pluginGrailsVersion = pluginXml.@griffonVersion.toString()
          println "pluginVersion=$pluginVersion"

          ["$pluginDirTmp/dist","$pluginDirTmp/griffon-app", "$pluginDirTmp/src", "$pluginDirTmp/test"].each { base ->
              def baseDir = new File(base)
		          visitPlatformDirP(mkp,pluginName, pluginVersion,baseDir)
					}
      }
      //linked plugin 2011/07/27 kimukou.buzz add end

      //USER_HOME setting
      mkp.yieldUnescaped("\n${indent}</linkedResources>")
         variableList(){
             variable(){
                 mkp.yieldUnescaped("\n${indent}${indent}${indent}<name>USER_HOME</name>")
                       if(isWindows){
                          mkp.yieldUnescaped("\n${indent}${indent}${indent}<value>file:/$userHomeRegex</value>\n")
                       }
                       else{
                          mkp.yieldUnescaped("\n${indent}${indent}${indent}<value>file:$userHomeRegex</value>\n")
                       }
                       mkp.yieldUnescaped("${indent}${indent}")
             }
         }
      }
    return linkEntry
}

