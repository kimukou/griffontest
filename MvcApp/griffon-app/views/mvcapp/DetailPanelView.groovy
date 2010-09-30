package mvcapp

import java.awt.*
import static java.awt.BorderLayout.*

tabbedPane(id:'tab', rootPane, selectedIndex:rootPane.tabCount) {
  panel(title:item) {
    borderLayout()
    panel(constraints:NORTH) {
      button('CLOSE', actionPerformed:controller.closeTab)
    }
    panel(constraints:CENTER) {
      borderLayout()
      splitPane(constraints:CENTER) {
        panel {
          borderLayout()
          scrollPane(constraints:CENTER) {
            tree(id:'testTree')
          }
        }
        panel {
          borderLayout()
          scrollPane(constraints:CENTER) {
            textArea(id:'consoleText', editable:false,
                     font:new Font('Monospaced', Font.PLAIN, 12))
          }
        }
      }
    }
  }
}
