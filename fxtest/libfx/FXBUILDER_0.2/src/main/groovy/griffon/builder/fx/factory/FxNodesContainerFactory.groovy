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

import javafx.scene.Node
import com.sun.javafx.runtime.location.*
import com.sun.javafx.runtime.sequence.*
import com.sun.javafx.runtime.TypeInfo

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.com>
 */
class FxNodesContainerFactory extends FxBeanFactory {
    private final String propertyName
    private final String propertyClass

    FxNodesContainerFactory(Class beanClass) {
        this(beanClass, "content", Node)
    }

    FxNodesContainerFactory(Class beanClass, String propertyName) {
        this(beanClass, propertyName, Node)
    }

    FxNodesContainerFactory(Class beanClass, String propertyName, Class propertyClass) {
        super( beanClass, false )
        this.propertyName = propertyName
        this.propertyClass = propertyClass
    }

    public void doSetChild( FactoryBuilderSupport builder, Object parent, Object child ) {
        if(!builder.parentContext.children) builder.parentContext.children = []
        if(propertyClass.isAssignableFrom(child?.class)) builder.parentContext.children << child
    }

    public void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node ) {
        if( builder.context.children ) {
            node.location(propertyName).setAsSequence(Sequences.fromCollection(TypeInfo.Object,builder.context.children))
            builder.context.children = []
        }

        super.onNodeCompleted( builder, parent, node )
    }
}