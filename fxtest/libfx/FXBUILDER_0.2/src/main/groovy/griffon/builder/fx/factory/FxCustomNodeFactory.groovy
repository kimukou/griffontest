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

import javafx.scene.CustomNode

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.com>
 */
class FxCustomNodeFactory extends AbstractFxFactory {
    public Object newInstance( FactoryBuilderSupport builder, Object name, Object value, Map attributes )
            throws InstantiationException, IllegalAccessException {
        if( value instanceof CustomNode ) {
            return value
        } else if( value instanceof Class && CustomNode.isAssignableFrom(value) ) {
            def node = value.getDeclaredConstructor([Boolean.TYPE] as Class[]).newInstance([true] as Object[])
            try {
                node.addTriggers$()
                node.applyDefaults$()
            } catch( MissingMethodException mme ) {
                // ignore
            }
            return node
        }
        throw new RuntimeException("in $name value must be either an instance of CustomNode or a CustomNode subclass.")
    }

    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
        applyAttributes(builder, node, attributes)
        return false
    }

    public boolean isLeaf() {
        return true
    }
}