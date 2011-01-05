/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.builder.fx

import groovy.util.GroovySwingTestCase
import javafx.animation.*
import javafx.animation.transition.*
import javafx.stage.*
import javafx.scene.*
import javafx.scene.shape.*
import javafx.scene.text.*
import javafx.scene.layout.*
import javafx.scene.control.*
import javafx.scene.paint.*
import javafx.scene.image.*
import javafx.scene.transform.*
import javafx.scene.effect.*
import javafx.scene.effect.light.*
import javafx.ext.swing.*
import org.jfxtras.scene.shape.*
import org.jfxtras.scene.border.*

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class FxBuilderTests extends GroovySwingTestCase {
   FxBuilder builder

   void setUp() {
      builder = new FxBuilder()
   }

   void testSwingWidgets() {
      testInEDT {
         def widgets = [
            swingButton: SwingButton,
            swingCheckBox: SwingCheckBox,
            swingComboBox: SwingComboBox,
            swingIcon: SwingIcon,
            swingList: SwingList,
            swingListItem: SwingListItem,
            swingLabel: SwingLabel,
            swingRadioButton: SwingRadioButton,
            swingScrollPane: SwingScrollPane,
            swingSlider: SwingSlider,
            swingTextField: SwingTextField,
            swingToggleButton: SwingToggleButton
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testControls() {
      testInEDT {
         def widgets = [
            button: Button,
            checkBox: CheckBox,
            hyperlink: Hyperlink,
            radioButton: RadioButton,
            toggleButton: ToggleButton,
            toggleGroup: ToggleGroup,
            textBox: TextBox,
            scrollBar: ScrollBar,
            progressBar: ProgressBar,
            progressIndicator: ProgressIndicator,
            slider: Slider
            //textBox: LayoutInfo
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testLayouts() {
      testInEDT {
         def widgets = [
            hbox: HBox,
            vbox: VBox,
            flow: Flow,
            stack: javafx.scene.layout.Stack,
            tile: Tile,
            clipView: ClipView,
            layoutInfo: LayoutInfo
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testDrawingPrimitives() {
      testInEDT {
         def widgets = [
            arc: Arc,
            circle: Circle,
            cubicCurve: CubicCurve,
            eclipse: Ellipse,
            line: Line,
            polygon: Polygon,
            polyline: Polyline,
            quadCurve: QuadCurve,
            rectangle: Rectangle,
            //shape: Shape,
            intersect: ShapeIntersect,
            subtract: ShapeSubtract,
            svgPath: SVGPath,
            path: Path,
            arcTo: ArcTo,
            cubicCurveTo: CubicCurveTo,
            hlineTo: HLineTo,
            lineTo: LineTo,
            moveTo: MoveTo,
            quadCurveTo: QuadCurveTo,
            vlineTo: VLineTo,
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testText() {
      testInEDT {
         def widgets = [
            text: Text,
            font: Font
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testTransforms() {
      testInEDT {
         def widgets = [
            affine: Affine,
            rotate: Rotate,
            scale: Scale,
            shear: Shear,
            translate: Translate
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testPaints() {
      testInEDT {
         def widgets = [
            linearGradient: LinearGradient,
            radialGradient: RadialGradient,
            stop: Stop
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testEffects() {
      testInEDT {
         def widgets = [
            blend: Blend,
            bloom: Bloom,
            colorAdjust: ColorAdjust,
            displacementMap: DisplacementMap,
            dropShadow: DropShadow,
            floatMap: FloatMap,
            flood: Flood,
            gaussianBlur: GaussianBlur,
            glow: Glow,
            identity: Identity,
            innerShadow: InnerShadow,
            invertMask: InvertMask,
            lighting: Lighting,
            distantLight: DistantLight,
            //light: Light,
            pointLight: PointLight,
            spotLight: SpotLight,
            motionBlur: MotionBlur,
            perspectiveTransform: PerspectiveTransform,
            reflection: Reflection,
            sepiaTone: SepiaTone,
            shadow: Shadow
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testAnimation() {
      testInEDT {
         def widgets = [
            timeline: Timeline,
            keyFrame: KeyFrame
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testTransitions() {
      testInEDT {
         def widgets = [
            parallelTransition: ParallelTransition,
            sequentialTransition: SequentialTransition,
            fadeTransition:FadeTransition,
            pathTransition: PathTransition,
            pauseTransition: PauseTransition,
            rotateTransition: RotateTransition,
            scaleTransition: ScaleTransition,
            translateTransition: TranslateTransition
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testJfxtrasShapes() {
      testInEDT {
         def widgets = [
            almond: Almond,
            arrow: Arrow,
            asterisk: Asterisk,
            astroid: Astroid,
            ballon: Balloon,
            cross: Cross,
            donut: Donut,
            etriangle: ETriangle,
            itriangle: ITriangle,
            rtriangle: RTriangle,
            lauburu: Lauburu,
            multiRoundRectangle: MultiRoundRectangle,
            rays: Rays,
            regularPolygon: RegularPolygon,
            resizableEllipse: ResizableEllipse,
            resizableRectangle: ResizableRectangle,
            reuleauxTriangle: ReuleauxTriangle,
            roundPin: RoundPin,
            star2: Star2
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testJfxtrasBorders() {
      testInEDT {
         def widgets = [
            bevelBorder: BevelBorder,
            ellipseBorder: EllipseBorder,
            emptyBorder: EmptyBorder,
            etchedBorder: EtchedBorder,
            frameBorder: FrameBorder,
            imageBorder: ImageBorder,
            lineBorder: LineBorder,
            metallicBorder: MetallicBorder,
            pipeBorder: PipeBorder,
            roundedRectBorder: RoundedRectBorder,
            shapeBorder: ShapeBorder,
            softBevelBorder: SoftBevelBorder,
            titledBorder: TitledBorder
         ]
         widgets.each{ name, expectedClass ->
            def node = builder."$name"(id:"${name}Id".toString())
            assert builder."${name}Id".class == expectedClass
         }
      }
   }

   void testSwingButton() {
      testInEDT {
         boolean clicked = false
         def button = builder.swingButton(text: "Button", action: {clicked = true}, visible: true)
         assert button
         assert button.text == "Button"
         assert !clicked
         button.JButton.doClick()
         assert clicked
      }
   }

   void testCustomNode() {
      testInEDT {
         def node = builder.customNode(HelloWorldCustomNode, str: "Groovy")
         assert node
         assert node.str == "Groovy"
      }
   }

   void testClipView() {
      testInEDT {
         def node = builder.clipView {
            customNode(HelloWorldCustomNode, id: "custom", str: "Groovy")
         }
         assert node
         assert node.node
         assert node.node == builder.custom
      }
   }

   void testListView() {
      testInEDT {
         def node = builder.listView {
            customNode(HelloWorldCustomNode, id: "custom1", str: "Groovy")
            customNode(HelloWorldCustomNode, id: "custom2", str: "JavaFX")
         }
         assert node
         def items = node.location("items").get()
         assert items
         assert items[0] == builder.custom1
         assert items[1] == builder.custom2
      }
   }

   void testPathAndPathElements() {
      testInEDT {
         def path = builder.path {
            moveTo(id: "p1", x: 10, y: 50)
            hlineTo(id: "p2", x: 70)
            quadCurveTo(id: "p3", x: 120, y: 60, controlX: 100, controlY: 0)
            lineTo(id: "p4", x: 175, y: 55)
            arcTo(id: "p5", x: 10, y: 50, radiusX: 100, radiusY: 100, sweepFlag: true)
         }
         assert path
         def elements = path.location("elements").get()
         assert elements.size() == 5
         [[id: "p1", klass: MoveTo],[id: "p2", klass: HLineTo],
          [id: "p3", klass: QuadCurveTo],[id: "p4", klass: LineTo],
          [id: "p5", klass: ArcTo]].eachWithIndex { m, i ->
            assert elements[i] == builder[(m.id)]
            assert elements[i].class == m.klass
         }
      }
   }

   void testTransformsAndChildren() {
      testInEDT {
         def rectangle = builder.rectangle {
            transforms(id: "tx") {
               rotate(id: "r")
               scale(id: "s")
               translate(id: "t")
               shear(id: "h")
               affine(id: "a")
            }
         }
         assert rectangle
         def transforms = rectangle.location("transforms").get()
         assert transforms
         assert transforms.size() == 5
         [[id: "r", klass: Rotate],[id: "s", klass: Scale],
          [id: "t", klass: Translate],[id: "h", klass: Shear],
          [id: "a", klass: Affine]].eachWithIndex { m, i ->
            assert transforms[i] == builder[(m.id)]
            assert transforms[i].class == m.klass
         }
      }
   }

   void testTransitionsAndChildren() {
      testInEDT {
         def transitions = builder.content {
            parallelTransition(id: "p") {
               fadeTransition(id: "t1")
               pathTransition(id: "t2")
               pauseTransition(id: "t3")
            }
            sequentialTransition(id: "s") {
               rotateTransition(id: "t4")
               scaleTransition(id: "t5")
               translateTransition(id: "t6")
            }
         }

         assert transitions
         assert transitions.size() == 2
         [[id: "t1", klass: FadeTransition],
          [id: "t2", klass: PathTransition],
          [id: "t3", klass: PauseTransition]].eachWithIndex { m, i ->
            assert transitions[0][i] == builder[(m.id)]
            assert transitions[0][i].class == m.klass
         }
         [[id: "t4", klass: RotateTransition],
          [id: "t5", klass: ScaleTransition],
          [id: "t6", klass: TranslateTransition]].eachWithIndex { m, i ->
            assert transitions[1][i] == builder[(m.id)]
            assert transitions[1][i].class == m.klass
         }
      }
   }

   void testImplicitSwingWidget() {
       testInEDT {
           builder.registerFactory("comboBox", new groovy.swing.factory.ComboBoxFactory())

           def node = builder.listView {
               comboBox(id:'swingCombo', items:['a','b','c'])
           }
           assert node
           def items = node.location("items").get()
           assert items
           assert items[0] instanceof Node
           assert builder.swingCombo instanceof javax.swing.JComboBox
       }
   }
/*
   void testPaintsAndChildren() {
      testInEDT {
         def paint = builder.linearGradient {
            stop(id: "s1", offset: 0, color: Color.$BLACK)
            stop(id: "s2", offset: 0.5, color: Color.$RED)
            stop(id: "s3", offset: 1, color: Color.$WHITE)
         }
         assert paint
         def stops = paint.location("stops").get()
         assert stops
         assert stops.size() == 3
         [[id: "s1", klass: Stop, offset: 0, color: Color.$BLACK],
          [id: "s2", klass: Stop, offset: 0.5, color: Color.$RED],
          [id: "s3", klass: Stop, offset: 1, color: Color.$WHITE],].eachWithIndex { m, i ->
            //assert stops[i] == builder[(m.id)]
            //assert stops[i].class == m.klass
            //assert stops[i].offset == m.offset
            //assert stops[i].color == m.color
         }
      }
   }
*/
/*
   void testStage() {
        def countCache = [:]
        def counter = { target ->
            def key = target.toString() +"."+ target.hashCode()
            def count = countCache.get(key,0)
            countCache[key] = ++count
            target.text = "Click me! ("+count+")"
        }
        testInEDT {
            builder.stage(title: "Griffon + FX", width: 240, height: 84) {
                scene {
                    vbox {
                        swingButton(text: "Click me!", id: "button1",
                            width: 230, action: {counter(button1)})
                        swingButton(text: "Click me!", id: "button2",
                            width: 230, action: {counter(button2)})
                        customNode(HelloWorldCustomNode, str: "Groovy")
                        hbox(spacing: 10) {
                            button(text: "Button1", action: {println "Button1"})
                            button(text: "Button2", action: {println "Button2"})
                        }
                    }
                    //listView(height: 100, items: [ "one", "two", "three", "four" ])
                }
            }
        }
        Thread.sleep(50000)
   }
*/
}