import net.spy.memcached.ConnectionFactoryBuilder as CFB

servers {
    'localhost' {
        port = 11211
    }
}
pool {
    protocol = CFB.Protocol.TEXT
}
environments {
    production {
        props {
            protocol = CFB.Protocol.BINARY
        }
    }
}
