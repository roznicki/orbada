/*
 * CommandParametersDialog.java
 *
 * Created on 20 październik 2007, 18:05
 */

package pl.mpak.orbada.universal.gui;

import java.util.concurrent.Callable;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import pl.mpak.orbada.core.Application;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.universal.OrbadaUniversalPlugin;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.core.ParametrizedCommand;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.variant.Variant;

/**
 *
 * @author  akaluza
 */
public class CommandParametersDialog extends javax.swing.JDialog {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("universal");

  private int modalResult = ModalResult.NONE;
  private ParametrizedCommand command;
  private CommandParametersPanel panel;
  private ISettings settings;
  
  /** Creates new form CommandParametersDialog
   * @param command
   */
  public CommandParametersDialog(ParametrizedCommand command) {
    super(SwingUtil.getRootFrame());
    this.command = command;
    initComponents();
    init();
  }
  
  public static boolean showDialog(final ParametrizedCommand command) {
    Boolean result = SwingUtil.invokeAndWait(new Callable<Boolean>() {
      public Boolean call() throws Exception {
        CommandParametersDialog dialog = new CommandParametersDialog(command);
        dialog.setVisible(true);
        return dialog.modalResult == ModalResult.OK;
      }
    });
    return result;
  }
  
  private void init() {
    getContentPane().add(getPanel(), java.awt.BorderLayout.CENTER);
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);

    pack();
    settings = Application.get().getSettings("orbada-command-parameter-dialog");
    try {
      setBounds(0, 0, settings.getValue("width", new Variant(800)).getInteger(), settings.getValue("height", new Variant(500)).getInteger());
    } catch (Exception ex) {
    }
    
    SwingUtil.setButtonSizesTheSame(new AbstractButton[] {buttonOk, buttonCancel});
    SwingUtil.centerWithinScreen(this);
    getPanel().setFocus();
  }
  
  private CommandParametersPanel getPanel() {
    if (panel == null) {
      panel = new CommandParametersPanel(command);
    }
    return panel;
  }
  
  @Override
  public void dispose() {
    settings.setValue("width", new Variant(getWidth()));
    settings.setValue("height", new Variant(getHeight()));
    settings.store();
    try {
      getPanel().close();
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    super.dispose();
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
    jPanel1 = new javax.swing.JPanel();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();

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
    setTitle("Parametry polecenia SQL");
    setModal(true);

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    buttonOk.setAction(cmOk);
    buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonOk.setPreferredSize(new java.awt.Dimension(75, 23));
    jPanel1.add(buttonOk);

    buttonCancel.setAction(cmCancel);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(75, 23));
    jPanel1.add(buttonCancel);

    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
  modalResult = ModalResult.CANCEL;
  dispose();
}//GEN-LAST:event_cmCancelActionPerformed

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
  modalResult = ModalResult.OK;
  dispose();
}//GEN-LAST:event_cmOkActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private javax.swing.JPanel jPanel1;
  // End of variables declaration//GEN-END:variables
  
}
