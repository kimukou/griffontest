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

import java.lang.ref.WeakReference
import com.sun.javafx.runtime.location.ChangeListener
import com.sun.javafx.runtime.FXObject

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
abstract class FxChangeListenerAdapter extends ChangeListener {
   final String propertyName
   final WeakReference<FXObject> source

   FxChangeListenerAdapter(FXObject source, String propertyName) {
      super()
      this.source = new WeakReference(source)
      this.propertyName = propertyName
   }

   final FXObject getSource() {
      source.get()
   }

   abstract void valueChanged(Object oldValue, Object newValue)

   public void onChange(Object oldValue, Object newValue)   { valueChanged(oldValue, newValue) }
   public void onChange(byte oldValue, byte newValue)       { valueChanged(oldValue, newValue) }
   public void onChange(short oldValue, short newValue)     { valueChanged(oldValue, newValue) }
   public void onChange(int oldValue, int newValue)         { valueChanged(oldValue, newValue) }
   public void onChange(long oldValue, long newValue)       { valueChanged(oldValue, newValue) }
   public void onChange(float oldValue, float newValue)     { valueChanged(oldValue, newValue) }
   public void onChange(double oldValue, double newValue)   { valueChanged(oldValue, newValue) }
   public void onChange(boolean oldValue, boolean newValue) { valueChanged(oldValue, newValue) }
   public void onChange(char oldValue, char newValue)       { valueChanged(oldValue, newValue) }


    //public void onChange(com.sun.javafx.runtime.sequence.ArraySequence, com.sun.javafx.runtime.sequence.Sequence, int, int, com.sun.javafx.runtime.sequence.Sequence);
}
