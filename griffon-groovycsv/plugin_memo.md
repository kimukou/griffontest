<<Plugin memo>>

■Create Plugin
1)griffon create-plugin griffon-groovycsv
	
2)Edit/Add
	GroovycsvGriffonPlugin.groovy
		Edit Infomation
	lib
		Add using jar
	scripts/_Events.groovy
		Add Script
		(event jar copy)
	griffon-app/resources/META-INF
		Add using library's LICENCE,TXT

3)griffon package-plugin


■Using griffon
	griffon-app/conf/BuildConfig.groovy
		griffon.plugin.location.griffon-groovycsv="../groovycsv-plugin"

