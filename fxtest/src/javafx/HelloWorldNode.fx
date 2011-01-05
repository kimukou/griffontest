package fxtest;

import javafx.scene.*;  
import javafx.scene.text.*;  
  
class HelloWorldNode extends CustomNode {  
  public var str:String;  
  
  override function create():Node {  
    Text {  
      content: bind str  
      x: 10  
      y: 25  
      font: Font {  
        name: "Sans Serif"  
        size: 24  
      }  
    }  
  }  
}  
 
