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

    String userHomeRegex = isWindows ? userHome.toString().replace('\\', '\\\\') : userHome.toString()
    String griffonHomeRegex = isWindows ? griffonHome.toString().replace('\\', '\\\\') : griffonHome.toString()

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
        classpathentry(kind: 'output', path: classesDirPath.replaceFirst(~/$userHomeRegex/, 'USER_HOME'))
        
        def normalizeFilePath = { file ->
            String path = file.absolutePath
            String originalPath = path
            path = path.replaceFirst(~/$griffonHomeRegex/, 'GRIFFON_HOME')
            path = path.replaceFirst(~/$userHomeRegex/, 'USER_HOME')
            boolean var = path != originalPath
            originalPath = path
            path = path.replaceFirst(~/${griffonSettings.baseDir.path}(\\|\/)/, '')
            var = path == originalPath && !path.startsWith(File.separator)
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
    }
}
