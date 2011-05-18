package gmongotest

import com.mongodb.*
import com.gmongo.*

class GmongotestController {
    // these will be injected by Griffon
    def model
    def view

    // void mvcGroupInit(Map args) {
    //    // this method is called after model and view are injected
    // }

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
