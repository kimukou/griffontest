import net.spy.memcached.MemcachedClient

class BootstrapSpyMemcached {
    def init = { MemcachedClient mcc ->
    }

    def destroy = { MemcachedClient mcc ->
    }
} 
