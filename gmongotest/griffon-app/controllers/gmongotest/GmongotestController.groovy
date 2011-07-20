package gmongotest

import com.mongodb.*
import com.gmongo.*

import com.tinkerpop.gremlin.Gremlin
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory
import com.tinkerpop.blueprints.pgm.Graph


class GmongotestController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
		    Gremlin.load()

		    Graph g = TinkerGraphFactory.createTinkerGraph()
		    def results = []
		    g.v(1).outE.inV >> results
		    println "results=${results}"
    }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }

		def mongo
    def conAction = {
        mongo = new GMongo("192.168.10.106", 27017)
        println mongo.dump()
        mongo.close()
    }

    /*
    def action = { evt = null ->
    }
    */
}
