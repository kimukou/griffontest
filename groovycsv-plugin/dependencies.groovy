griffon.project.dependency.resolution = {
    inherits("global")
    log "warn" 
    repositories {
        griffonPlugins()
        griffonHome()
        griffonCentral()
        mavenCentral()
    }
    dependencies {
        //compile('com.xlson.groovycsv:groovycsv:0.2') { transitive = false }
        //compile('net.sf.opencsv:opencsv:2.2') { transitive = false }
        runtime('com.xlson.groovycsv:groovycsv:0.2') { transitive = false }
        runtime('net.sf.opencsv:opencsv:2.2') { transitive = false }
    }
}

griffon {
    doc {
        logo = '<a href="http://griffon.codehaus.org" target="_blank"><img alt="The Griffon Framework" src="../img/griffon.png" border="0"/></a>'
        sponsorLogo = "<br/>"
        footer = "<br/><br/>Made with Griffon (@griffon.version@)"
    }
}

griffon.jars.destDir='target/addon'
