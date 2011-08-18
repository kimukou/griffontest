servers {
    'localhost' {
        port = 11211
        weight = 1
    }
}
pool {
    initConn = 10
    minConn = 5
    maxConn = 50
    maxIdle = 1000 * 60 * 30 // 30 minutes
    maxBusyTime = 1000 * 60 * 5 // 5 minutes
    maintSleep = 1000 * 5 // 5 seconds
    socketTO = 1000 * 3 // 3 seconds to block on reads
    failover = false // turn off auto-failover in event of server down     
    nagle = false // turn off Nagle's algorithm on all sockets in pool  
    aliveCheck = false // disable health check of socket on checkout

}
environments {
    production {
        props {
            failover = true
        }
    }
}
