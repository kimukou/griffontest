package sqljettest

import static griffon.util.GriffonApplicationUtils.*
import java.awt.datatransfer.*
import java.awt.dnd.*

abstract class ConvertDropTargetCsv extends DropTarget {

	/** その名の通りドロップされたときに呼ばれるメソッド。 */
	public void drop(DropTargetDropEvent event) {
		// fravorて言うのはドラッグ＆ドロップするデータ形式みたいな物。
		// mime-typeとかが有って、たいてい送り側はいくつかの形式をサポートしていて、
		// その中から受け手が選びます。今回はプレーンテキストがあればそれ、という感じで。
//		println "a"
		event.currentDataFlavors.mimeType.each{x ->
			println x
		}
//		println "b"

		def flavor = event.currentDataFlavors.find {
			it.isMimeTypeEqual("text/plain") ||
			it.isMimeTypeEqual("application/x-java-file-list")
		}

		// うまく処理できない（今回だと送り側がプレーンテキストを送ってくれない）時は
		//  event.rejectDrop()してから帰ります。
		if (flavor == null) {
			event.rejectDrop()
			return;
		}
		println flavor
		// うまく処理できる時はevent.acceptDrop()したあと、
		event.acceptDrop(DnDConstants.ACTION_COPY)

		// 必要な処理をして、
		def text
		if(flavor.isMimeTypeEqual("text/plain")){
			text = convertText(event.transferable.getTransferData(flavor))
		}
		else{
			def fileList = event.transferable.getTransferData(DataFlavor.javaFileListFlavor);
			fileList.each{file->
				text = file.getAbsoluteFile()
				return
			}
		}
		println text
		griffon.util.ApplicationHolder.application.config.model.csvf = text
		griffon.util.ApplicationHolder.application.config.controller.csvLoad()

		// event.dropComplete()してから帰ります。
		event.dropComplete(true)
	}

	/** 実際の変換処理はサブクラスで実装。 */
	abstract String convertText(droped);
}

class CsvDropTarget extends ConvertDropTargetCsv {
	String convertText(droped) {
		  BufferedReader br = new BufferedReader(droped);
		  String msg = br.readLine();
		  println msg
		  return msg
	}
	public String toString() {"URL又はファイルパス取得"}
}
