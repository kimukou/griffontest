package sqljettest

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory

//CSV出力
//import au.com.bytecode.opencsv.*
import com.xlson.groovycsv.*
import java.io.*

//GPars非同期
import groovyx.gpars.*

class SqljettestController {
   // these will be injected by Griffon
    def model
    def view
		def service

		private static final Log LOG = LogFactory.getLog(SqljettestController)


    void mvcGroupInit(Map args) {
	    // this method is called after model and view are injected
	    service = new SqljettestService()
	    LOG.debug "service=${service}"

			//model に参照を入れる
			model.view = args.view
			model.controller = args.controller

			//遅延参照用
			app.config.controller = args.controller
			app.config.service = service
			app.config.view = args.view
			app.config.model = args.model

    }

	def clear ={
		st_time = new Date()
	  doOutside {
			try{
		  	model.sqLiteU.clearOpenTable(model.sqLiteU.tableNameC)
			}
		  catch(Exception e){
			  LOG.error e
		  }
		  finally{
				model.sqLiteU.closeTable()
			}
		  execAsync{
			  model.getPageInit()
			  model.getPageList(model.control_page)

			  model.result=tblsize
			  //経過時間
			  if(st_time!=null){
				  def c2 = new Date()
				  def ps_time = (c2.getTime() - st_time.getTime())/1000.0
				  model.time_serch = ps_time
			  }
		  }
	  }
	}

	def brows ={dispId,path->
	  doOutside {
		  //["Explorer.exe","${path}"].execute()
			println "${dispId}/${path}"
	  }
	}


  def onStartupEnd = {
		 st_time = new Date()
     URL ddl = getClass().classLoader.getResource('select.ddl')
     doOutside {
       app -> withSql { sql ->
         def tmpList = []
				 def i=0
				 LOG.debug "sql=${ddl.text}"
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
			  //経過時間
			  if(st_time!=null){
				  def c2 = new Date()
				  def ps_time = (c2.getTime() - st_time.getTime())/1000.0
				  model.time_serch = ps_time
			  }
      }
    }
	}
  
  def csvLoadA ={nextLine,table,cnt->
	  def dispId 	= Long.valueOf(nextLine.dispId)
	  def outstr= nextLine.path
	  println "outstr=${outstr}"

	  outstr = outstr.replaceAll("\\\\","\\\\\\\\")//SQLiteはINS時に\\を2倍増量しないとエラー
	  table.insert(cnt,dispId,outstr)// データ追加　（id,dispId,path）
	  if(cnt % model.control_flash_line ==0  && view.frame.visible && view.frame.active){
		  model.result=cnt//tmpList.size
		  //経過時間
		  if(st_time!=null){
			  def c2 = new Date()
			  def ps_time = (c2.getTime() - st_time.getTime())/1000.0
			  model.time_serch = ps_time
		  }
	  }
  }
	
	def st_time
  def csvLoad ={
		st_time = new Date()
	  doOutside {
		  def reader = null
		  
		  def cnt = model.sqLiteU.getTableId("last") + 1 //次の行から開始する
		  def table = model.sqLiteU.openTable(org.tmatesoft.sqljet.core.SqlJetTransactionMode.WRITE)// 書込みモード

		  try{
			  LOG.debug "model.csvf=${model.csvf}"
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
//	if(!it.isDone())it.get()
////	if(model.stop_f || griffon.util.ApplicationHolder.application.config.shutdown_hook_f){
////		exs.shutdownNow()
////		return
////	}
//}

		  }
		  catch(Exception e){
			  LOG.error e
		  }
		  finally{
			  //if(reader!=null)reader.close()
				model.sqLiteU.closeTable()
		  }
		  
		  execAsync{
			  model.getPageInit()
			  model.getPageList(model.control_page)

			  model.result=tblsize
			  //経過時間
			  if(st_time!=null){
				  def c2 = new Date()
				  def ps_time = (c2.getTime() - st_time.getTime())/1000.0
				  model.time_serch = ps_time
			  }
		  }
	  }
  }

}