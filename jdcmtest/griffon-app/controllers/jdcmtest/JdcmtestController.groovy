package jdcmtest


//dicom画像
import java.awt.image.*
import javax.swing.*

import java.util.Iterator
import javax.imageio.*
import javax.imageio.ImageReader

import jdcm.DicomFile
import jdcm.DicomSequence
import jdcm.DicomSet
import jdcm.StaticProperties


class JdcmtestController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
    }

    /*
    def action = { evt = null ->
    }
    */

	def browse ={

		  def path = "brimmywall.dcm"
		  println path
		  def icon = null
		  DicomFile dcmFile = new DicomFile(path);
		  
		  switch(dcmFile.getTransferSyntaxUID()){
			  case StaticProperties.TS_JPEG_PROCESS_1:
			   println "=====StaticProperties.TS_JPEG_PROCESS_1====="
				 return
				 break;
			  case StaticProperties.TS_JPEG_LOSSLESS:
			   println "=====StaticProperties.TS_JPEG_LOSSLESS====="
				 return
			   break;
	  	  default:
					Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("dcm")
					ImageReader reader = (ImageReader)readers.next()
					reader.setInput(new File(path), true)
					def img = reader.read(0)
					reader.dispose()
					icon = new ImageIcon(img)
					break;
		  }
			  
		  javax.swing.JOptionPane.showMessageDialog(
			  	null,
			  	" ",
					"${path}",
				javax.swing.JOptionPane.PLAIN_MESSAGE,
				icon			//icon
		  )
		}

}