import groovy.sql.Sql

class BootstrapGsql {
    def init = { String dataSourceName = 'default', Sql sql ->
    }

    def destroy = { String dataSourceName = 'default', Sql sql ->
    }
} 
