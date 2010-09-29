package mvcapp

import static java.awt.BorderLayout.*

application(title:'mvc-app', size:[500,400], locationByPlatform:true) {
  tabbedPane(id:'tab') {
    splitPane(title:"jobs") {
      panel {
        borderLayout()
        scrollPane(constraints:CENTER) {
          tree(id:'jobTree')
        }
      }
      panel {
        borderLayout()
        scrollPane(constraints:CENTER) {
          list(id:'buildList')
        }
      }
    }
  }
}
