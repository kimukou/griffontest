package org.codehaus.griffon.mercurial

import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.SortedList
import ca.odell.glazedlists.BasicEventList

class FilePanelModel {

    def columns = ["Name", "Status", "Directory"]

    EventList fileList = new SortedList(
      new BasicEventList(),
      {a, b -> b.name <=> a.name} as Comparator
   )
}
