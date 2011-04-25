
eventCopyLibsEnd = { jardir ->
		println "==eventCopyLibsEnd(groovycsv)=="
    ant.fileset(dir:"${getPluginDirForName('groovycsv').file}/lib/", includes:"*.jar").each {
        griffonCopyDist(it.toString(), jardir)
    }
}
