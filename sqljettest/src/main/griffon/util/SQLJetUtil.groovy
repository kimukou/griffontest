package griffon.util


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory

import java.io.File
import org.tmatesoft.sqljet.core.*
import org.tmatesoft.sqljet.core.table.*

class SQLJetUtil {

	private static final Log LOG = LogFactory.getLog(SQLJetUtil)
	
	def sqlF=null
	def DB_name ="test.db"
	
	def getFileName(){
		return 
	}
	
	def open = {
		LOG.debug "=== open start ==="
		def create_f =false
		File dbFile = new File(DB_name)
		if(!dbFile.exists()){
			create_f =true
		}
		println "[${create_f}]:dbFile=${dbFile}"
		//dbFile.delete()
		try{
			println "===AA==="
			// DBオブジェクト生成
			sqlF = SqlJetDb.open(dbFile, true)
			println "===BB==="
			// Autovacuum有効
			//sqlF.getOptions().setAutovacuum(true)
			println "===CC==="
			//sqlF.getOptions().setIncrementalVacuum(true)
			
			if(create_f){//ファイルが存在しないときのみDB,テーブルを作成する
				createDB()
				createTable()
			}
			println "===DD==="
		}catch(SqlJetException e){
			println "===EE==="
			LOG.error e
			sqlF = null
			return null
		}
		return sqlF
	}

	def close = {
	  if(sqlF==null)return
	  try{
		  sqlF.close()
	  }catch(SqlJetException e){
	  	LOG.error e
	  }
		sqlF = null
	}

	def createDB ={
		LOG.debug "=== createDB start ==="
		try{
			// 書き込みモード
			sqlF.beginTransaction(SqlJetTransactionMode.WRITE)
			sqlF.getOptions().setUserVersion(1)
			//sqlF.getOptions().setEncoding(SqlJetEncoding.UTF8)  //NG。DBが作れません！！
			sqlF.getOptions().setLegacyFileFormat(true)//レガシーモード
	  }catch(SqlJetException e){
			LOG.error e
			throw e
	  }
		finally{
			closeTable()
		}
	}

	//テーブル名	
	def tableNameC = "testdb"
	// テーブルクエリ(作成) 
	def createTableQuery = "CREATE TABLE ${tableNameC}(id INTEGER NOT NULL PRIMARY KEY,dispId INTEGER NOT NULL,path TEXT NOT NULL)"
	// インデックスクエリ
	//def createNameIndexQuery = "CREATE INDEX dispId_index ON ${tableName}(dispId)";

	
			
	def createTable = {
		LOG.debug "=== createTable start ==="
		try{ 
			// 書き込みモード
			sqlF.beginTransaction(SqlJetTransactionMode.WRITE)
			//テーブル作成
			sqlF.createTable(createTableQuery)
			// インデックス作成
			//sqlF.createIndex(createNameIndexQuery);
			
		}catch(SqlJetException e){
			LOG.error e
			throw e
		}
		finally{
			closeTable()
		}
	}
	
//===============================================================================================
	def openTable = {mode->
		LOG.debug "=== openTable(${mode}) start ==="
		def table = null
		try{
			closeTable()
			sqlF.beginTransaction(mode)// 書込みモード
			table = sqlF.getTable(tableNameC)// テーブルオープン
		}catch(SqlJetException e){
			LOG.error e
			throw e
		}
		return table
	}

	def closeTable = {
		LOG.debug "=== closeTable start ==="
		try{
			if(sqlF == null || sqlF.isInTransaction()==false || sqlF.isOpen()==false){
				println "===A=== $sqlF "
				if(sqlF==null)open()
				return
			}
			sqlF.commit()
		}catch(SqlJetException e){
			println "===B==="
			LOG.error e
			switch(e.getErrorCode()){
				case SqlJetErrorCode.NOTADB:
					createDB()
					createTable()
					break
				case SqlJetErrorCode.CORRUPT://データベースが奇形
					close()
					File dbFile = new File(DB_name)
					dbFile.delete()
					//open()
					break
				case SqlJetErrorCode.MISUSE://トランザクションの不一致
				case SqlJetErrorCode.ERROR://DB接続が切れた
				case SqlJetErrorCode.BUSY:
				case SqlJetErrorCode.IOERR:
					close()
					//open()
					break
			}
		}
		catch(Exception ex){
			println "===C==="
			LOG.error ex
		}
		println "===D==="
		if(sqlF==null)open()
		println "===E==="
	}
	
	def clearOpenTable = {tableC->
		LOG.debug "=== clearOpenTable(${tableC}) start ==="
		def table = null
		try{
			closeTable()
			sqlF.beginTransaction(org.tmatesoft.sqljet.core.SqlJetTransactionMode.WRITE)// 書込みモード
			table = sqlF.getTable(tableC)// テーブルオープン
			table.clear()
		}catch(SqlJetException e){
			LOG.error e
			throw e
		}
		return table
	}

	
//===============================================================================================
	def getTableSize = {tableC->
		LOG.debug "=== getTableSize(${tableC}) start ==="
		def len = 0
		try{
			closeTable()
			
			// 読み取りモード
			sqlF.beginTransaction(SqlJetTransactionMode.READ_ONLY)
			//テーブル選択
			def table = sqlF.getTable("${tableC}")
			ISqlJetCursor cur = table.open()
			len = cur.getRowCount()
			LOG.info "getTableSize=${len}"
		}catch(SqlJetException e){
			LOG.error e
			//throw e
		}
		finally{
			closeTable()
		}
		return len
	}

	def getTableId = {mode ->
		LOG.debug "=== getTableId(${mode}) start ==="
		def id = 0
		try{
			closeTable()
			
			// 読み取りモード
			sqlF.beginTransaction(SqlJetTransactionMode.READ_ONLY)
			//テーブル選択
			def table = sqlF.getTable("${tableNameC}")
			ISqlJetCursor cur = table.open()

			//テーブルが0行の時エラーが出るので
			def len = cur.getRowCount()
			if(len<=0)return 0
			
			if("first".equals(mode))cur.first()
			else					cur.last()
			id = cur.getInteger("id")
			LOG.info "id=${id}"
		}catch(SqlJetException e){
			LOG.error e
			//throw e
		}
		finally{
			closeTable()
		}
		return id
	}
	

	def scopeTable = {st_cnt,loop_max->
		LOG.debug "=== scopeTable start(${st_cnt},${loop_max}) ==="
		def tmpList = []
		ISqlJetCursor cur = null
		try{ 
			closeTable()
			
			// 読み取りモード
			sqlF.beginTransaction(SqlJetTransactionMode.READ_ONLY)
			//テーブル選択
			def table = sqlF.getTable("${tableNameC}")
			//println "table=${table}"
				
			// 範囲指定SELECT
			cur = table.scope(null, [st_cnt] as Object[], [loop_max] as Object[])
			// 結果を取得
			while (!cur.eof()) {
				def dispId = cur.getInteger("dispId")
				def path = cur.getString("path")
				path = path.replaceAll("\\\\\\\\","\\\\")//SQLiteは逆戻ししないと駄目らしい？？
				tmpList << [
					dispId:dispId,
					path:path,
					flag:false
				]
				if(!cur.next())break
			}
		}catch(SqlJetException e){
			LOG.error e
			throw e
		}
		finally{
			if(cur!=null)cur.close()
			//SelectもTransaction,Commitがいるらしい・・
			closeTable()
		}
		return tmpList
	} 

	
	def limitTable = {st_cnt,limit_max->
		LOG.debug "=== limitTable start(${st_cnt},${limit_max}) ==="
		def tmpList = []
		ISqlJetCursor cur = null
		try{
			closeTable()
			// 読み取りモード
			sqlF.beginTransaction(SqlJetTransactionMode.READ_ONLY)
			//テーブル選択
			def table = sqlF.getTable("${tableNameC}")
			//println "table=${table}"
				
			// 範囲指定SELECT
			cur = table.open()
			
			def len = cur.getRowCount()
			if(len<=0 || !cur.goToRow(st_cnt)){//index columとは関係ない。通し番号
				return tmpList
			}
			//cur.setLimit(limit_max) //how to use ?
			//println "${cnt}/${limit_max}/${cur}"
			// 結果を取得
			int pos=0
			while (!cur.eof()) {
				def dispId  = cur.getInteger("dispId")
				def path = cur.getString("path")
				path = path.replaceAll("\\\\\\\\","\\\\")//SQLiteは逆戻ししないと駄目らしい？？
				tmpList << [
					dispId :dispId ,
					path:path,
					flag:false
				]
				if(!cur.next() || pos >=limit_max )break
				pos++
			}
		}catch(SqlJetException e){
			LOG.error e
			throw e
		}
		finally{
			if(cur!=null)cur.close()
			//SelectもTransaction,Commitがいるらしい・・
			closeTable()
		}
		return tmpList
	}

}
