package fxtest;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

class SampleNode extends CustomNode{
    public override function create():Node{
        return Group{
            onMouseClicked: function(e:MouseEvent){
                    println( "node -> {e.node}" );
                    println( "source -> {e.source}" );
            }
            content: Rectangle {
                x: 10, y: 10;
                width: 140, height: 90;
                fill: Color.BLACK;
           };
        }
    }
}

Stage {
    title : "MouseEventSample"
    scene: Scene {
        width: 200
        height: 200
        content: [ SampleNode{} ]
    }
}

