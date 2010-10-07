//SQLJet‘Î‰ž
import griffon.util.SQLJetUtil
def sqLiteU = new SQLJetUtil()
sqLiteU.open()
sqLiteU.closeTable()
sqLiteU.close()

dataSource {
    pooled = false
    driverClassName = "org.sqlite.JDBC"
    username = "sa"
    password = ""
    tokenizeddl = false // set this to true if using MySQL or any other
                        // RDBMS that requires execution of DDL statements
                        // on separate calls
}
pool {
    maxWait = 60000
    maxIdle = 5
    maxActive = 8
}
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
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            //dbCreate = "skip"
            url = "jdbc:sqlite:test.db"
        }
    }
}
