package sqljettest

import groovy.beans.Bindable

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import ca.odell.glazedlists.*


//SQLJet対応
import griffon.util.SQLJetUtil

class SqljettestModel {

	private static final Log LOG = LogFactory.getLog(SqljettestModel)

	//表示リスト
	EventList dispTableList = new SortedList(new BasicEventList(),{a, b -> a.dispId <=> b.dispId} as Comparator)

	//SQLJet対応
	SQLJetUtil sqLiteU = null

	//CSV読込
	@Bindable def csvf =''

	@Bindable csv_flush_line=100

	//検索時間
	@Bindable def time_serch

	//処理件数
	@Bindable def count = 0
	//検索総数
	@Bindable def result =0

	//ページデータ
	@Bindable def control_page=1
	@Bindable def control_page_max=1

	//表示リスト行
	@Bindable def control_page_line=100
	//表示更新タイミング(行)
	@Bindable long control_flash_line=100

	//停止フラグ
	@Bindable boolean stop_f = false

	def getPageInit={
		result = sqLiteU.getTableSize(sqLiteU.tableNameC)
		count=0
		control_page=1
		control_page_max = result==0 ? 1 : result / control_page_line + 1
		return result
	}


	def getPageList(Long page){
		control_page = page 
		int first = 1 //result <= 0 ? 0 : sqLiteU.getTableId("first") 
		int st_pos = (control_page-1) * control_page_line
		int st_cnt = st_pos + first
		def limit_max = st_cnt + control_page_line -1 < result ? st_cnt + control_page_line -1 : result
		//def limit_max = st_cnt + control_page_line < result ? control_page_line : (result - st_pos -1)

		LOG.debug "(control_page, control_page_line,result,first,st_cnt,limit_max)=(${control_page},${control_page_line},${result},${first},${st_cnt},${limit_max})"
		//controller.edt{
			LOG.debug "[P]dispTableList.size=${dispTableList.size()}"
			dispTableList.getReadWriteLock().writeLock().lock() //非同期Lock
			try{
				if(dispTableList.size()>0)dispTableList.clear()
				if(result>0){
					def tmpList = sqLiteU.scopeTable(st_cnt,limit_max)
					//def tmpList = sqLiteU.limitTable(st_cnt,limit_max)
					if(tmpList.size()>0)dispTableList.addAll(tmpList)
				}
			}catch(Exception ex){
				LOG.error ex
			}
			finally{
				dispTableList.getReadWriteLock().writeLock().unlock() //非同期unLock
			}
			view.clearB.selected = false
			if(result <= 0)	view.clearB.enabled  = false
			else						view.clearB.enabled  = true

			LOG.debug "[A]dispTableList.size=${dispTableList.size()}"
		//}	
	}


	def view
	def controller

	SqljettestModel(){
		sqLiteU = new SQLJetUtil()
		//sqLiteU.open()
		//sqLiteU.closeTable()
		//sqLiteU.close()
	}

	@Override
	void destory(){
		sqLiteU.close()
	}

}
