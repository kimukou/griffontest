package steeltest

import groovy.beans.Bindable

class SteeltestModel {
   // @Bindable String propName

	@Bindable def count = 30
	@Bindable def cpu_usage = 0
}