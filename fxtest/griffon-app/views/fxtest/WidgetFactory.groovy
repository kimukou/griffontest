package fxtest

import griffon.builder.fx.factory.*
import com.sun.javafx.runtime.FXObject

class WidgetFactory extends FxBeanFactory {
    WidgetFactory( Class beanClass ) {
        super( beanClass, false )
    }

    WidgetFactory( Class beanClass, boolean leaf ) {
        super( beanClass, leaf )
    }

    public void doSetChild( FactoryBuilderSupport builder, Object parent, Object child ) {
        if(!builder.parentContext.children) builder.parentContext.children = []
        if(child instanceof FXObject) builder.parentContext.children << child
    }

}
