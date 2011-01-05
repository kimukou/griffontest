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

package griffon.builder.fx.impl

import com.sun.javafx.runtime.FXObject

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class FxClosureChangeAdapter extends FxChangeListenerAdapter {
   private final Closure closure

   FxClosureChangeAdapter(FXObject source, String propertyName, Closure closure) {
      super(source, propertyName)
      this.closure = closure
   }

   void valueChanged(Object oldValue, Object newValue) {
      closure(source, propertyName, oldValue, newValue)
   }

   boolean equals(Object obj) {
      if(!obj || !(obj instanceof FxClosureChangeAdapter)) return false
      return source == obj.source &&
             propertyName == obj.propertyName &&
             closure == obj.closure
   }

   int hashCode() {
      int seed = 23 * 37
      int hashcode = seed * source.hashCode()
      hashcode = seed * hashcode + propertyName.hashCode()
      hashcode = seed * hashcode + closure.hashCode()
      return hashcode
   }
}