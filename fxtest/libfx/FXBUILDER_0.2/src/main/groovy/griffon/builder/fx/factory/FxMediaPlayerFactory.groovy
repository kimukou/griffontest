/*
 * Copyright 2008-2009 the original author or authors.
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

package griffon.builder.fx.factory

import javafx.scene.media.*
import com.sun.javafx.runtime.FXObject
import com.sun.javafx.runtime.location.*
import com.sun.javafx.runtime.sequence.*
import com.sun.javafx.runtime.TypeInfo

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.com>
 */
class FxMediaPlayerFactory extends FxBeanFactory {
    FxMediaPlayerFactory() {
        super(MediaPlayer, false)
    }

    public void doSetChild( FactoryBuilderSupport builder, Object parent, Object child ) {
        switch(child) {
           case Media:
              parent.location("media").set(child)
              break
           case Track:
              if(!builder.parentContext.tracks) builder.parentContext.tracks = []
              builder.parentContext.tracks << child
              break
           case MediaTimer:
              if(!builder.parentContext.timers) builder.parentContext.timers = []
              builder.parentContext.timers << child
              break
         }
    }

    public void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node ) {
        if( builder.context.tracks ) {
            node.location("enabledTracks").setAsSequence(Sequences.fromCollection(TypeInfo.Object,builder.context.tracks))
            builder.context.tracks = []
        }
        if( builder.context.timers ) {
            node.location("timers").setAsSequence(Sequences.fromCollection(TypeInfo.Object,builder.context.timers))
            builder.context.timers = []
        }

        super.onNodeCompleted( builder, parent, node )
    }
}