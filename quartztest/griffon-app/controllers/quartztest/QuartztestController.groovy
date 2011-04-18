package quartztest

class QuartztestController {
    // these will be injected by Griffon
    def model
    def view

		def dataSource
     void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
				println dataSource.dump()
    }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }

    /*
    def action = { evt = null ->
    }
    */
}
