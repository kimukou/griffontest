/*
 * WidgetFX - JavaFX Desktop Widget Platform
 * Copyright (c) 2008-2009, WidgetFX Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of WidgetFX nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.widgetfx.widget.calendar;

/**
 * @author Stephen Chin
 * @author Keith Combs
 */
import javafx.ext.swing.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import java.text.*;
import java.util.*;
import javafx.util.Sequences;
import org.jfxtras.scene.layout.*;
import org.jfxtras.scene.layout.LayoutConstants.*;
import org.widgetfx.Widget;
import org.widgetfx.config.Configuration;
import org.widgetfx.config.StringProperty;

var language:String = Locale.getDefault().getLanguage();
var country:String = Locale.getDefault().getCountry();
var variant:String = Locale.getDefault().getVariant();
var locale = bind new Locale(language, country, variant);

def defaultWidth = 180.0;
def defaultHeight = 200.0;

def group:Group = Group {
    def arcHeight = defaultHeight / 20;
    def offset = defaultWidth / 25;
    transforms: bind Transform.scale(
        widget.width / defaultWidth,
        widget.height / defaultHeight);
    // scale down by 1% to leave room for stroke width
    scaleX: .99
    scaleY: .99
    content: [
        for (i in reverse [0..3]) {
            createPage(offset*i/3, offset*i/3 + arcHeight,
                       defaultWidth - offset,
                       defaultHeight - offset - arcHeight)
        },
        createSpiral(defaultWidth - offset, arcHeight),
        createPageContents(0, arcHeight * 2,
                           defaultWidth - offset,
                           defaultHeight-offset-arcHeight*2)
    ]
}

def config = Configuration {
    properties: [
        StringProperty {
            name: "language"
            value: bind language with inverse
        },
        StringProperty {
            name: "country"
            value: bind country with inverse
        },
        StringProperty {
            name: "variant"
            value: bind variant with inverse
        }
    ]
    var locales = Locale.getAvailableLocales();
    var localePicker = SwingComboBox {
        items: for (l in locales) {
            SwingComboBoxItem {
                selected: l == locale
                text: l.getDisplayName()
                value: l
            }
        }
    }
    scene: Scene {
        content: Grid {
            rows: row([
                Text {content: "Locale:"},
                localePicker
            ])
        }
    }
    onSave: function() {
        var l = localePicker.selectedItem.value as Locale;
        language = l.getLanguage();
        country = l.getCountry();
        variant = l.getVariant();
    }
    onLoad: function() {
        localePicker.selectedIndex =
            Sequences.indexOf(locales, locale);
    }
}

def widget:Widget = Widget {
    width: defaultWidth
    height: defaultHeight
    aspectRatio: defaultWidth / defaultHeight
    configuration: config
    content: group
}

function createPage(x:Number, y:Number,
                    width:Number, height:Number) {
    [
        Rectangle { // Fill
            translateX: x
            translateY: y
            width: width
            height: height
            fill: Color.WHITE
        },
        Rectangle { // Footer
            translateX: x
            translateY: y + height * 6/7
            width: width
            height: height / 7
            fill: Color.MIDNIGHTBLUE
        },
        Rectangle { // Border
            translateX: x
            translateY: y
            width: width
            height: height
            fill: null
            stroke: Color.BLACK
        }
    ]
}

function createSpiral(width:Number, arcHeight:Number) {
    def numArcs = 20;
    for (i in [1..numArcs]) {
        var arcSpacing = width / (numArcs + 2);
        Arc {
            centerX: arcSpacing * (i + 1)
            centerY: arcHeight
            radiusX: arcHeight * 2 / 3
            radiusY: arcHeight
            startAngle: 0
            length: 230
            stroke: Color.BLACK
            fill: null
        }
    }
}

function createPageContents(x:Number, y: Number,
                            width:Number, height:Number) {
    def calendar = Calendar.getInstance();
    def fontHeight = 20;
    def offset = 5;
    def dateSymbols = bind new DateFormatSymbols(locale);
    def date:Text = Text {
        translateX: bind (width-date.layoutBounds.width)/2
        translateY: bind (height-date.layoutBounds.height)/2
        content: "{calendar.get(Calendar.DAY_OF_MONTH)}"
        font: Font.font("Impact", height * 2 / 3)
        textOrigin: TextOrigin.TOP
    }
    def year:Text = Text {
        translateX: offset
        translateY: offset + fontHeight
        font: Font.font(null, fontHeight)
        content: "{calendar.get(Calendar.YEAR)}"
    }
    def month:Text = Text {
        translateX: bind width - month.layoutBounds.width -
                         offset
        translateY: offset + fontHeight
        font: Font.font(null, fontHeight)
        content: bind Arrays.asList(dateSymbols.getMonths())
            .get(calendar.get(Calendar.MONTH))
    }
    def dayOfWeek:Text = Text {
        translateX:
            bind (width - dayOfWeek.layoutBounds.width) / 2
        translateY: height - offset * 1.5
        font: Font.font(null, fontHeight)
        content: bind Arrays.asList(dateSymbols.getWeekdays())
            .get(calendar.get(Calendar.DAY_OF_WEEK))
        fill: Color.WHITESMOKE
    }
    Group {
        translateX: x
        translateY: y
        content: [date, year, month, dayOfWeek]
    }
}

return widget;
