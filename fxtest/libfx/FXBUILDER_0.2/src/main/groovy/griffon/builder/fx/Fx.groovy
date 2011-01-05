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

package griffon.builder.fx

import com.sun.javafx.runtime.FXObject
import com.sun.javafx.runtime.location.*
import com.sun.javafx.runtime.sequence.*
import java.beans.PropertyChangeListener
import griffon.builder.fx.impl.*
import javafx.animation.transition.*

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class Fx {
    private static final String ENHANCED = "_ENHANCED_METACLASS_"
    private static final Class[] EMPTY_PARAMS = new Class[0]
    private static final Object[] EMPTY_ARGS = new Object[0]

    static boolean hasBeenEnhanced(Class klass) {
        MetaClassRegistry mcr = GroovySystem.metaClassRegistry
        MetaClass mc = mcr.getMetaClass(klass)
        if( !(mc instanceof ExpandoMetaClass) ) return false
        return mc.hasMetaProperty(ENHANCED)
    }

    static void enhance(Class klass, Map enhancedMethods) {
        MetaClassRegistry mcr = GroovySystem.metaClassRegistry
        MetaClass mc = mcr.getMetaClass(klass)
        boolean init = false
        if( !(mc instanceof ExpandoMetaClass) ||
             (mc instanceof ExpandoMetaClass && !mc.isModified()) ) {
            mcr.removeMetaClass klass
            mc = new ExpandoMetaClass(klass)
            init = true
        }
        // if mc is an EMC that was initialized previously
        // with additional methods/properties and it does
        // not allow modifications after init, then the next
        // block will throw an exception
        enhancedMethods.each {k, v ->
            v.each { c ->
                if (mc.pickMethod(k,c.parameterTypes) == null) {
                    mc.registerInstanceMethod(k, c)
                }
            }
        }
        mc.registerBeanProperty(ENHANCED,true)
        if (init) {
            mc.initialize()
            mcr.setMetaClass(klass, mc)
        }
    }

    static void enhanceFxClasses() {
       Class klass = FXObject
       if( !Fx.hasBeenEnhanced(klass) ) {
            Fx.enhance(klass,[
               getProperty: {String name -> Fx.fxGetProperty(delegate,name)},
               setProperty: {String name, value -> Fx.fxSetProperty(delegate,name,value)},
               location: {String name -> Fx.fxLocation(delegate,name)},
               hasLocation: {String name -> Fx.fxHasLocation(delegate,name)},
               locationType: {String name -> Fx.fxGetLocationType(delegate,name)},
               addChangeListener: [{String name, Closure closure -> Fx.addClosureChangeAdapter(delegate, name, closure)},
                                   {String name, PropertyChangeListener listener -> Fx.addPropertyChangeAdapter(delegate, name, listener)}],
               removeChangeListener: [{String name, Closure closure -> Fx.removeClosureChangeAdapter(delegate, name, closure)},
                                      {String name, PropertyChangeListener listener -> Fx.removePropertyChangeAdapter(delegate, name, listener)}],
               addPropertyChangeListener: {String name, PropertyChangeListener listener -> Fx.addPropertyChangeAdapter(delegate, name, listener)},
               removePropertyChangeListener: {String name, PropertyChangeListener listener -> Fx.removePropertyChangeAdapter(delegate, name, listener)}
            ])
        }

        klass = ObjectLocation
        if( !Fx.hasBeenEnhanced(klass) ) {
            Fx.enhance(klass,[
               onChange: { Closure closure -> Fx.addLocationChangeAdapter(delegate,closure)}
            ])
        }

        klass = Sequence
        if( !Fx.hasBeenEnhanced(klass) ) {
            Fx.enhance(klass,[
               getAt: { int index -> delegate.get(index)}
            ])
        }

        klass = ParallelTransition
        if( !Fx.hasBeenEnhanced(klass) ) {
            Fx.enhance(klass,[
               getAt: { int index -> delegate.location("content").get()[index]}
            ])
        }

        klass = SequentialTransition
        if( !Fx.hasBeenEnhanced(klass) ) {
            Fx.enhance(klass,[
               getAt: { int index -> delegate.location("content").get()[index]}
            ])
        }
    }

    static fxGetMetaProperty(FXObject target, String name) {
       def mc = target.metaClass
       def clazz = target.class
       def mp = mc.getMetaProperty(name)
       if( !mp ) mp = mc.getMetaProperty("\$"+name)
       if( !mp ) mp = mc.getMetaProperty("\$"+clazz.name.replace('.','\$')+"\$"+name)
       if( mp ) return mp
       throw new MissingPropertyException(name,clazz)
    }

    static fxGetLocationField(FXObject target, String name) {
       def mc = target.metaClass
       def clazz = target.class
       def mp = mc.getMetaProperty(name)
       if( !mp ) mp = mc.getMetaProperty("loc\$"+name)
       if( !mp ) mp = mc.getMetaProperty("loc\$"+clazz.name.replace('.','\$')+"\$"+name)
       return mp
    }

    static fxGetProperty(FXObject target, String name) {
       def mp = fxGetMetaProperty(target, name)
       return mp.getProperty(target)
    }

    static fxSetProperty(FXObject target, String name, value) {
       def mp = fxGetMetaProperty(target, name)
       mp.setProperty(target, value)
    }

    static fxLocation(FXObject target, String name) {
       def mc = target.metaClass
       def clazz = target.class
       def mm = mc.pickMethod("loc\$"+name, EMPTY_PARAMS)
       if( !mm ) mm = mc.pickMethod("loc\$"+clazz.name.replace('.','\$')+"\$"+name, EMPTY_PARAMS)
       if( mm ) return mm.invoke(target, EMPTY_ARGS)
       throw new MissingPropertyException(name,clazz)
    }

    static fxGetLocationType(FXObject target, String name) {
       def mp = fxGetLocationField(target, name)
       if( !mp ) throw new MissingPropertyException(name,target.class)
       return mp.type
    }

    static fxHasLocation(FXObject target, String name) {
       return fxGetLocationField(target, name) != null
    }

    static addClosureChangeAdapter(FXObject target, String propertyName, Closure closure) {
       if( !propertyName || !closure) return
       def variable = fxGetLocation(target, propertyName)
       variable.addChangeListener(new FxClosureChangeAdapter(target, propertyName, closure))
    }

    static removeClosureChangeAdapter(FXObject target, String propertyName, Closure closure) {
       if( !propertyName || !closure) return
       def variable = fxLocation(target, propertyName)
       variable.removeChangeListener(new FxClosureChangeAdapter(target, propertyName, closure))
    }

    static addPropertyChangeAdapter(FXObject target, String propertyName, PropertyChangeListener listener) {
       if( !propertyName || !listener) return
       def variable = fxLocation(target, propertyName)
       variable.addChangeListener(new FxPropertyChangeAdapter(target, propertyName, listener))
    }

    static removePropertyChangeAdapter(FXObject target, String propertyName, PropertyChangeListener listener) {
       if( !propertyName || !listener) return
       def variable = fxLocation(target, propertyName)
       variable.removeChangeListener(new FxPropertyChangeAdapter(target, propertyName, listener))
    }

    static addLocationChangeAdapter(ObjectLocation target, Closure closure) {
       if( !closure) return
       target.addChangeListener(new FxOnChangeListener(closure))
    }
}