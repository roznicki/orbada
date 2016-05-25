/*
 * ProgressiveLineDialog.java
 *
 * Created on 10 sierpie� 2008, 12:31
 */

package pl.mpak.sky.gui.swing.syntax;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.ListRowChangeKeyListener;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.util.array.StringList;

/**
 *
 * @author  akaluza
 */
public class ProgressiveLineDialog extends javax.swing.JDialog {
  private static final long serialVersionUID = -592505432834852207L;

  private int modalResult = ModalResult.NONE;
  private JTextComponent textComponent;
  private ArrayList<FoundLine> listFound;
  private int lineNo;
  private boolean selectionChange = true;

  /** Creates new form ProgressiveLineDialog */
  public ProgressiveLineDialog(JTextComponent textComponent) {
    super(SwingUtil.getRootFrame());
    initComponents();
    this.listFound = new ArrayList<FoundLine>();
    this.textComponent = textComponent;
    init();
  }
  
  public static int show(JTextComponent textComponent) {
    ProgressiveLineDialog dialog = new ProgressiveLineDialog(textComponent);
    dialog.setVisible(true);
    if (dialog.modalResult == ModalResult.OK) {
      return ((FoundLine)dialog.listResult.getSelectedValue()).lineNo;
    } else {
      return -1;
    }
  }
  
  private void init() {
    lineNo = -1;
    if (textComponent.getDocument() != null) {
      Document d = textComponent.getDocument();
      if (d.getDefaultRootElement() != null) {
        lineNo = d.getDefaultRootElement().getElementIndex(textComponent.getCaretPosition());
      }
    }
    listResult.setModel(new FoundListModel());
    listResult.setCellRenderer(new FoundLineListCellRenderer());
    listResult.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        cmOk.setEnabled(listResult.getSelectedValue() != null);
      }
    });

    textSearch.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        search();
      }
      public void removeUpdate(DocumentEvent e) {
        search();
      }
      public void changedUpdate(DocumentEvent e) {
      }
    });
    textSearch.requestFocusInWindow();
    textSearch.addKeyListener(new ListRowChangeKeyListener(listResult) {
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.isConsumed()) {
          selectionChange = false;
        }
      }
    });
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);
    SwingUtil.setButtonSizesTheSame(new AbstractButton[] {buttonOk, buttonCancel});
    pack();
    SwingUtil.centerWithinScreen(this);
  }
  
  private void search() {
    int foundLine = -1;
    listFound.clear();
    if (!"".equals(textSearch.getText())) {
      String find = textSearch.getText().toUpperCase();
      StringList sl = new StringList(false);
      sl.setText(textComponent.getText());
      for (int i=0; i<sl.size(); i++) {
        String line = sl.get(i).toUpperCase();
        if (line.indexOf(find) >= 0) {
          listFound.add(new FoundLine(i, sl.get(i)));
          if (i == lineNo || (foundLine == -1 && i >= lineNo)) {
            foundLine = listFound.size() -1;
          }
        }
      }
    }
    ((FoundListModel)listResult.getModel()).dataChanged();
    if (foundLine != -1 && selectionChange) {
      listResult.setSelectedIndex(foundLine);
    }
    else if (foundLine == -1) {
      selectionChange = true;
    }
    if (listResult.getSelectedIndex() >= listFound.size()) {
      listResult.setSelectedIndex(listFound.size() -1);
    }
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        listResult.ensureIndexIsVisible(listResult.getSelectedIndex());
      }
    });
    cmOk.setEnabled(listFound.size() > 0 && listResult.getSelectedValue() != null);
  }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmOk = new pl.mpak.sky.gui.swing.Action();
    cmCancel = new pl.mpak.sky.gui.swing.Action();
    jScrollPane1 = new javax.swing.JScrollPane();
    listResult = new javax.swing.JList();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();
    textSearch = new pl.mpak.sky.gui.swing.comp.TextField();

    cmOk.setEnabled(false);
    cmOk.setText("&Ok");
    cmOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmOkActionPerformed(evt);
      }
    });

    cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmCancel.setText("&Anuluj");
    cmCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCancelActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Wyszukiwanie przyrostowe");
    setModal(true);

    listResult.setFont(new java.awt.Font("Courier New", 0, 11));
    listResult.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    listResult.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        listResultMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(listResult);

    buttonOk.setAction(cmOk);
    buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonOk.setPreferredSize(new java.awt.Dimension(85, 24));

    buttonCancel.setAction(cmCancel);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(85, 24));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(5, 5, 5)
            .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
  modalResult = ModalResult.OK;
  dispose();
}//GEN-LAST:event_cmOkActionPerformed

private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
  modalResult = ModalResult.CANCEL;
  dispose();
}//GEN-LAST:event_cmCancelActionPerformed

private void listResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listResultMouseClicked
  if (listResult.getSelectedValue() != null && evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
    modalResult = ModalResult.OK;
    dispose();
  } 
}//GEN-LAST:event_listResultMouseClicked

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JList listResult;
  private pl.mpak.sky.gui.swing.comp.TextField textSearch;
  // End of variables declaration//GEN-END:variables

  private class FoundLine {
    int lineNo;
    String text;
    public FoundLine(int lineNo, String text) {
      this.lineNo = lineNo;
      this.text = text;
    }
  }
  
  private class FoundLineListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    public FoundLineListCellRenderer() {
      super();
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      final FoundLine line = (FoundLine)value;
      setText(String.format("%4d: %s", new Object[] {line.lineNo +1, line.text}));
      return this;
    }
  }
  
  private class FoundListModel extends AbstractListModel {
    private static final long serialVersionUID = 1L;
    public int getSize() {
      return listFound.size();
    }
    public Object getElementAt(int index) {
      return listFound.get(index);
    }
    public void dataChanged() {
      fireContentsChanged(this, 0, listFound.size());
    }
  }
  
}