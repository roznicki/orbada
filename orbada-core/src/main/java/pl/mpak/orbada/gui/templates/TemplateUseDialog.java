/*
 * TemplateUseDialog.java
 *
 * Created on 25 listopad 2008, 11:12
 */

package pl.mpak.orbada.gui.templates;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import pl.mpak.orbada.Consts;
import pl.mpak.orbada.core.Application;
import pl.mpak.orbada.core.Application;
import pl.mpak.orbada.db.Template;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.sky.gui.swing.syntax.SyntaxEditor;
import pl.mpak.usedb.UseDBException;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.patt.Resolvers;
import pl.mpak.sky.gui.swing.comp.TextField;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class TemplateUseDialog extends javax.swing.JDialog {

  private final static StringManager stringManager = StringManagerFactory.getStringManager("orbada");

  private int modalResult = ModalResult.NONE;
  private String tpl_id;
  private SyntaxEditor editor;
  private Template template;

  /** Creates new form TemplateUseDialog */
  public TemplateUseDialog(String tpl_id, SyntaxEditor editor) throws UseDBException {
    super(SwingUtil.getRootFrame());
    this.tpl_id = tpl_id;
    this.editor = editor;
    initComponents();
    init();
  }

  public static void showDialog(final String tpl_id, final SyntaxEditor editor) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          TemplateUseDialog dialog = new TemplateUseDialog(tpl_id, editor);
          dialog.setVisible(true);
        } catch (Exception ex) {
          ExceptionUtil.processException(ex);
          MessageBox.show(null, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
        }
      }
    });
  }
  
  private void init() throws UseDBException {
    template = new Template(Application.get().getOrbadaDatabase(), tpl_id);
    setTitle(getTitle() +" - " +template.getName());
    
    prepareParameters();
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);
    
    SwingUtil.centerWithinScreen(this);
  }
  
  private void prepareParameters() {
    List<String> valueNames = Resolvers.createValueNames(template.getBody(), true);
    for (int i=0; i<valueNames.size(); i++) {
      JLabel label = new JLabel();
      String name = SwingUtil.setButtonText(label, valueNames.get(i));
      label.setText(name);
      java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridy = i *2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
      panelParameters.add(label, gridBagConstraints);

      TextField field = new TextField();
      field.setName(valueNames.get(i));
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridy = 1 +i *2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
      panelParameters.add(field, gridBagConstraints);
      
      label.setLabelFor(field);
    }
  }

  private void appendToEditor() {
    HashMap<String, String> valueMap = new HashMap<String, String>();
    for (int i=0; i<panelParameters.getComponentCount(); i++) {
      Component c = panelParameters.getComponent(i);
      if (c instanceof TextField) {
        valueMap.put(((TextField)c).getName(), ((TextField)c).getText());
      }
    }
    if (editor.getSelectionStart() != editor.getSelectionEnd()) {
      editor.replaceSelection(template.expand(valueMap));
    }
    else {
      editor.insert(template.expand(valueMap), editor.getCaretPosition());
    }
  }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmOk = new pl.mpak.sky.gui.swing.Action();
    cmCancel = new pl.mpak.sky.gui.swing.Action();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();
    scrollParameters = new javax.swing.JScrollPane();
    panelParameters = new javax.swing.JPanel();

    cmOk.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0));
    cmOk.setText(stringManager.getString("cmOk-text")); // NOI18N
    cmOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmOkActionPerformed(evt);
      }
    });

    cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmCancel.setText(stringManager.getString("cmCancel-text")); // NOI18N
    cmCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCancelActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("TemplateUseDialog-title")); // NOI18N
    setModal(true);

    buttonOk.setAction(cmOk);
    buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonOk.setPreferredSize(new java.awt.Dimension(85, 25));

    buttonCancel.setAction(cmCancel);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(85, 25));

    scrollParameters.setBorder(null);

    panelParameters.setLayout(new java.awt.GridBagLayout());
    scrollParameters.setViewportView(panelParameters);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(scrollParameters, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(scrollParameters, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
try {
    modalResult = ModalResult.OK;
    appendToEditor();
    dispose();
  } catch (Exception ex) {
    ExceptionUtil.processException(ex);
    MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), new int[] {ModalResult.OK});
  }
}//GEN-LAST:event_cmOkActionPerformed

private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
  modalResult = ModalResult.CANCEL;
  dispose();
}//GEN-LAST:event_cmCancelActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private javax.swing.JPanel panelParameters;
  private javax.swing.JScrollPane scrollParameters;
  // End of variables declaration//GEN-END:variables

}