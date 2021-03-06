//SQLJet settingdataSources
import griffon.util.SQLJetUtil
def sqLiteU = new SQLJetUtil()
sqLiteU.open()
sqLiteU.closeTable()
sqLiteU.close()


def xml_driver
def xml_jdbc
def xml_user
def xml_pass

InputStream st = null
if(griffon.util.RunMode.current==griffon.util.RunMode.STANDALONE && new File('setting/resources.xml').exists()){
	st=new FileInputStream('setting/resources.xml')
}
else{
	st=getClass().classLoader.getResourceAsStream('resources.xml')
}
def xml = new XmlParser().parse(st)

println xml.bean.property.size()
xml.bean.property.each{
	println "name=${it.@name}"
	println "value=${it.value.text()}"

	switch(it.@name){
		case "driverClass":xml_driver=it.value.text();break;
		case "jdbcUrl":xml_jdbc=it.value.text();break;
		case "user":xml_user=it.value.text();break;
		case "password":xml_pass=it.value.text();break;
	}
}


dataSource {

		//driverClassName = "org.sqlite.JDBC"
		//username = "sa"
		//password = ""
		driverClassName = xml_driver//"org.sqlite.JDBC"
		username = xml_user//"sa"
		password = xml_pass//""

	  tokenizeddl = false // set this to true if using MySQL or any other
	                      // RDBMS that requires execution of DDL statements
	                      // on separate calls

	  //pooled = false
		pool {
	    maxWait = 60000
	    maxIdle = 5
	    maxActive = 8
		}
}

println dataSource.dump()

//===================================================================

environments {
    development {
        dataSource {
            //dbCreate = "create" // one of ['create', 'skip']
            url = "jdbc:sqlite:test.db"
        }
    }
    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:sqlite:test.db"
        }
    }
    production {
        dataSource {
            //dbCreate = "skip"
            url = "jdbc:sqlite:test.db"
        }
    }
}
