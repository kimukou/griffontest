package sqljettest

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

//CSVo—Í
import au.com.bytecode.opencsv.*

//sqlJet
import org.tmatesoft.sqljet.core.SqlJetException
import org.tmatesoft.sqljet.core.SqlJetTransactionMode
import org.tmatesoft.sqljet.core.table.SqlJetDb

class SqljettestService {
	private static final Log LOG = LogFactory.getLog(SqljettestService)

	def model
	def view
	def controller

	void mvcGroupDestroy(Map args) {

	}

	void mvcGroupInit(Map args) {
		//app.config.splash_screen.showStatus(app.getMessage('splash.initialize.service'))
		
		//DI‚Ì‘ã‚è‚ÉQÆ‰Šú‰»‚·‚é
		model = args.model
		view = args.view
		controller = args.controller
	}

//========================================================================
//CSVŒn‚Ìˆ—
	def getCSVReader={filename->
		def input=new FileInputStream(filename)
		def is=new InputStreamReader(input, "UTF-8")
		def reader = new CSVReader(is,',' as Character)
		return reader
	}
}


