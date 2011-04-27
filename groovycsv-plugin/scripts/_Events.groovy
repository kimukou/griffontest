eventCopyLibsEnd = { jardir ->
		println "==eventCopyLibsEnd(groovycsv)=="
		def libPath="${getPluginDirForName('groovycsv').file}/lib_/"
		if(new File(libPath).exists()==false)return

    ant.fileset(dir:libPath, includes:"*.jar").each {
        griffonCopyDist(it.toString(), jardir)
    }
}
