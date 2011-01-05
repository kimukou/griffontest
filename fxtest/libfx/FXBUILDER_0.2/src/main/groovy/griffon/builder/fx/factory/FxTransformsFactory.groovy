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

import javafx.scene.transform.Transform
import com.sun.javafx.runtime.FXObject
import com.sun.javafx.runtime.TypeInfo
import com.sun.javafx.runtime.sequence.Sequences

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.com>
 */
class FxTransformsFactory extends AbstractFxFactory {
    public Object newInstance( FactoryBuilderSupport builder, Object name, Object value, Map attributes )
            throws InstantiationException, IllegalAccessException {
        []
    }

    public void doSetChild( FactoryBuilderSupport builder, Object parent, Object child ) {
        if( child instanceof Transform) parent << child
    }

    public void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node ) {
        if(parent && (parent instanceof FXObject) && parent.hasLocation("transforms")) {
             parent.location("transforms").setAsSequence(Sequences.fromCollection(TypeInfo.Object,node))
        }
        super.onNodeCompleted( builder, parent, node )
    }
}