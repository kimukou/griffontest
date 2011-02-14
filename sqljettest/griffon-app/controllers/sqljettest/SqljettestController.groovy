package sqljettest


class SqljettestController {
   // these will be injected by Griffon
    def model
    def view
    def service

    void mvcGroupInit(Map args) {

      // this method is called after model and view are injected
      //service = new SqljettestService()
      log.debug "service=${service}"

      //model into access pointer
      model.view = view//args.view
      model.controller = this//args.controller

      //delay access pointer
      app.config.controller = this//args.controller
      app.config.service = service
      app.config.view = view//args.view
      app.config.model = model//args.model
    }

  void mvcGroupDestroy(Map args) {
    
  }

  def clear ={
    service.clear()
  }

  def brows ={dispId,path->
    doOutside {
      //["Explorer.exe","${path}"].execute()
      println "${dispId}/${path}"
    }
  }
  def onStartupEnd = {app -> 
		service.onStartupEnd(app)
	}
/*
  def st_time
  def onStartupEnd = {
     st_time = new Date()
     URL ddl = getClass().classLoader.getResource('select.ddl')
     doOutside {
       //[TODO]gsql plugin injecttion controller only
       app -> withSql { sql ->
         def tmpList = []
         def i=0
         log.debug "sql=${ddl.text}"
         sql.eachRow(ddl.text) {
         if(i>=model.control_page_line)return
           tmpList << [dispId: it.dispId, path: it.path]
           i++
         }
         edt { 
           model.dispTableList.addAll(tmpList) 
         }
         model.sqLiteU.open()
         model.count=0
         model.result=model.sqLiteU.getTableSize(model.sqLiteU.tableNameC)
         model.getPageInit()
         //An elapsed time
         if(st_time!=null){
           def c2 = new Date()
           def ps_time = (c2.getTime() - st_time.getTime())/1000.0
           model.time_serch = ps_time
         }
      }
    }
  }
*/
	//@Threading(Threading.Policy.SKIP)
  def csvLoadA ={nextLine,table,cnt->
    service.csvLoadA(nextLine,table,cnt)
  }

  
  def csvLoad ={filename->
    service.csvLoad(filename)
  }
}