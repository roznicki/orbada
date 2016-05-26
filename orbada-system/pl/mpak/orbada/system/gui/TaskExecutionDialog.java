/*
 * TaskExecutionDialog.java
 *
 * Created on 12 listopad 2007, 19:09
 */

package pl.mpak.orbada.system.gui;

import java.awt.BorderLayout;
import pl.mpak.sky.gui.swing.SwingUtil;
import orbada.core.Application;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.system.OrbadaSystemPlugin;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class TaskExecutionDialog extends javax.swing.JDialog {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager(OrbadaSystemPlugin.class);
  private TaskExecutionPanel taskPanel;
  public static boolean showed = false;
  private ISettings settings;
  
  /** Creates new form TaskExecutionDialog */
  public TaskExecutionDialog() {
    super(SwingUtil.getRootFrame());
    initComponents();
    init();
  }
  
  public static void showDialog() {
    TaskExecutionDialog dialog = new TaskExecutionDialog();
    dialog.setVisible(true);
  }
  
  private void init() {
    settings = Application.get().getSettings("orbada-system-task-list-dialog");
    showed = true;
    taskPanel = new TaskExecutionPanel();
    getContentPane().add(taskPanel, BorderLayout.CENTER);
    pack();
    SwingUtil.centerWithinScreen(this);
    try {
      setBounds(settings.getValue("left", (long)getLocation().x).intValue(), settings.getValue("top", (long)getLocation().y).intValue(), settings.getValue("width", (long)getWidth()).intValue(), settings.getValue("height", (long)getHeight()).intValue());
    } catch (Exception ex) {
    }
  }
  
  private void storeWindowSize() {
    settings.setValue("width", (long)getWidth());
    settings.setValue("height", (long)getHeight());
    settings.setValue("left", (long)getLocation().x);
    settings.setValue("top", (long)getLocation().y);
    settings.store();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmClose = new pl.mpak.sky.gui.swing.Action();
    jPanel1 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();

    cmClose.setActionCommandKey("cmClose");
    cmClose.setText(stringManager.getString("close-amp")); // NOI18N
    cmClose.setTooltip(stringManager.getString("close")); // NOI18N
    cmClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCloseActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("task-execution-dialog-title")); // NOI18N
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jButton1.setAction(cmClose);
    jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
    jButton1.setPreferredSize(new java.awt.Dimension(75, 23));
    jPanel1.add(jButton1);

    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
  storeWindowSize();
  showed = false;
  taskPanel.close();
}//GEN-LAST:event_formWindowClosing

private void cmCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCloseActionPerformed
  storeWindowSize();
  showed = false;
  taskPanel.close();
  dispose();
}//GEN-LAST:event_cmCloseActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private pl.mpak.sky.gui.swing.Action cmClose;
  private javax.swing.JButton jButton1;
  private javax.swing.JPanel jPanel1;
  // End of variables declaration//GEN-END:variables
  
}
