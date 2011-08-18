servers {
    'localhost' {
        port = 11211
        weight = 1
    }
}
client {
    props {
        conectionPoolSize = 10
    }
    configuration {
        soTimeout = 1000 * 3 // 3 seconds to block on reads
    }
}

environments {
    production {
        client {
            props {
                sessionIdleTimeout = 1000 * 60 * 30 // 30 minutes
            }
        }
    }
}
