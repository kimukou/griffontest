/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.builder.fx.impl

import com.sun.javafx.functions.*

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class ClosureFunction implements Function0, Function1, Function2, Function3,
                                  Function4, Function5, Function6, Function7, Function8 {
    private final Closure closure

    ClosureFunction( Closure closure ) {
        this.closure = closure
    }

    Object invoke() {
       closure()
    }
    Object invoke( Object a0 ) {
       closure(a0)
    }
    Object invoke( Object a0, Object a1 ) {
       closure(a0,a1)
    }
    Object invoke( Object a0, Object a1, Object a2 ) {
       closure(a0,a1,a2)
    }
    Object invoke( Object a0, Object a1, Object a2, Object a3 ) {
       closure(a0,a1,a2,a3)
    }
    Object invoke( Object a0, Object a1, Object a2, Object a3, Object a4 ) {
       closure(a0,a1,a2,a3,a4)
    }
    Object invoke( Object a0, Object a1, Object a2, Object a3, Object a4, Object a5 ) {
       closure(a0,a1,a2,a3,a4,a5)
    }
    Object invoke( Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6 ) {
       closure(a0,a1,a2,a3,a4,a5,a6)
    }
    Object invoke( Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7 ) {
       closure(a0,a1,a2,a3,a4,a5,a6,a7)
    }
}
