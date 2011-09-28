package twittersphere2

import groovy.beans.Bindable

import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import gov.nasa.worldwind.render.Annotation
import ca.odell.glazedlists.swing.EventComboBoxModel

@Bindable class Twittersphere2Model {
  boolean animate
  String searchMode
  String searchText
  int searchLimit

  boolean searching
  List tweetList
  int tweetListPos
  

  @Bindable Map<String, Annotation> tweetAnnotaitons
  EventList<String> searchTermsList = new BasicEventList<String>()
  EventComboBoxModel<String> searchModel = new EventComboBoxModel<String>(searchTermsList)

  //kimukou_26 20110725-temporary add start
  groovy.util.slurpersupport.GPathResult result
  //kimukou_26 20110725-temporary add end
}

