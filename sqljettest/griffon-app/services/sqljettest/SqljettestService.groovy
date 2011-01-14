package sqljettest

//CSV output
import au.com.bytecode.opencsv.*

//sqlJet
import org.tmatesoft.sqljet.core.SqlJetException
import org.tmatesoft.sqljet.core.SqlJetTransactionMode
import org.tmatesoft.sqljet.core.table.SqlJetDb


//CSV OutPut
import com.xlson.groovycsv.*
import java.io.*

//GPars Async
import groovyx.gpars.*


class SqljettestService {

  def model
  def view
  def controller

  void mvcGroupDestroy(Map args) {

  }

  void mvcGroupInit(Map args) {
    //app.config.splash_screen.showStatus(app.getMessage('splash.initialize.service'))
    
    //The reference initialization is done instead of DI.
    //model = args.model
    //view = args.view
    //controller = args.controller
    //println app.griffon.dump()
  }

//========================================================================
//CSV action
  def getCSVReader={filename->
    def input=new FileInputStream(filename)
    def is=new InputStreamReader(input, "UTF-8")
    def reader = new CSVReader(is,',' as Character)
    return reader
  }


  def st_time
  def clear ={
    st_time = new Date()
    //[NOTE] service injection execSync/execAsync/execOutside automatically (from 0.9.1)
    execOutside{
    //controller.doOutside {
      try{
        model.sqLiteU.clearOpenTable(model.sqLiteU.tableNameC)
      }
      catch(Exception e){
        log.error e
      }
      finally{
        model.sqLiteU.closeTable()
      }
      execAsync{
        model.getPageInit()
        model.getPageList(model.control_page)

        //An elapsed time
        if(st_time!=null){
          def c2 = new Date()
          def ps_time = (c2.getTime() - st_time.getTime())/1000.0
          model.time_serch = ps_time
        }
      }
    }
  }

  def csvLoadA ={nextLine,table,cnt->
    def dispId  = Long.valueOf(nextLine.dispId)
    def outstr= nextLine.path
    println "outstr=${outstr}"

    outstr = outstr.replaceAll("\\\\","\\\\\\\\")//When SQLite case insert,nessesary [\\] twice
    table.insert(cnt,dispId,outstr)// data add (id,dispId,path）
    if(cnt % model.control_flash_line ==0  && view.frame.visible && view.frame.active){
      model.result=cnt//tmpList.size
      //passing time
      if(st_time!=null){
        def c2 = new Date()
        def ps_time = (c2.getTime() - st_time.getTime())/1000.0
        model.time_serch = ps_time
      }
    }
  }


  def csvLoad ={filename->
    model.csvf = filename
    st_time = new Date()
    execOutside{
    //controller.doOutside {
      def reader = null
      
      def cnt = model.sqLiteU.getTableId("last") + 1 //next line start
      def table = model.sqLiteU.openTable(org.tmatesoft.sqljet.core.SqlJetTransactionMode.WRITE)// writing mode


      try{
        log.debug "model.csvf=${model.csvf}"
        //reader = service.getCSVReader(model.csvf)
        def input=new FileInputStream(model.csvf)
        def is=new InputStreamReader(input, "UTF-8")
        reader = new CsvParser().parse([separator:',',quoteChar:'"'],is)



//def thSet = new HashSet()       
//def exs = java.util.concurrent.Executors.newFixedThreadPool(2)
//Asynchronizer.withExistingAsynchronizer(exs){
Asynchronizer.withAsynchronizer(2){
        reader.each{nextLine->
          csvLoadA.callAsync(nextLine,table,cnt)
          cnt++
        }
}
//thSet.each{
//  if(!it.isDone())it.get()
////  if(model.stop_f || griffon.util.ApplicationHolder.application.config.shutdown_hook_f){
////    exs.shutdownNow()
////    return
////  }
//}

      }
      catch(Exception e){
        log.error e
      }
      finally{
        //if(reader!=null)reader.close()
        model.sqLiteU.closeTable()
      }
      
      execAsync{
        model.getPageInit()
        model.getPageList(model.control_page)

        //passed time
        if(st_time!=null){
          def c2 = new Date()
          def ps_time = (c2.getTime() - st_time.getTime())/1000.0
          model.time_serch = ps_time
        }
      }
    }
  }

  def onStartupEnd = {app -> 
     st_time = new Date()
     URL ddl = getClass().classLoader.getResource('select.ddl')
     execSync{
     //controller.doOutside {
       withSql { sql ->
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

}


