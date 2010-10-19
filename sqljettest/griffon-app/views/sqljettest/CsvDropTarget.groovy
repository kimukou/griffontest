package sqljettest

import static griffon.util.GriffonApplicationUtils.*
import java.awt.datatransfer.*
import java.awt.dnd.*

abstract class ConvertDropTargetCsv extends DropTarget {

  def _func
  public ConvertDropTargetCsv(func){
    super()
    _func=func
  }

  /** Method of calling when dropped as its name suggests */
  public void drop(DropTargetDropEvent event) {
    // It is a thing like the data form that does D&D that says fravor . 
    // The sending side mostly supports some forms to having as for mime-type. 
    // The receiver chooses from among that. In feeling of it if there is a plain text this time
//    println "a"
    event.currentDataFlavors.mimeType.each{x ->
      println x
    }
//    println "b"

    def flavor = event.currentDataFlavors.find {
      it.isMimeTypeEqual("text/plain") ||
      it.isMimeTypeEqual("application/x-java-file-list")
    }

    // It returns after it event.rejectDrop()s it when not treatable well 
		//(The sending side doesn't send the plain text in case of this time). 
    if (flavor == null) {
      event.rejectDrop()
      return;
    }
    println flavor
    // After it event.acceptDrop()s it when treatable well
    event.acceptDrop(DnDConstants.ACTION_COPY)

    // Necessary processing
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
    _func.call(text)

    // event.dropComplete()してから帰ります。
    event.dropComplete(true)
  }

  /** 実際の変換処理はサブクラスで実装。 */
  abstract String convertText(droped);
}

class CsvDropTarget extends ConvertDropTargetCsv {

  public CsvDropTarget(_func){
    super(_func)
  }


  String convertText(droped) {
      BufferedReader br = new BufferedReader(droped);
      String msg = br.readLine();
      println msg
      return msg
  }
  public String toString() {"URL又はファイルパス取得"}
}
