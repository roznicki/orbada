/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExplainPlanDialog.java
 *
 * Created on 2009-05-02, 15:52:07
 */

package pl.mpak.orbada.oracle.gui.universal;

import java.io.IOException;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import orbada.core.Application;
import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author akaluza
 */
public class ExplainPlanDialog extends javax.swing.JDialog {

  private final StringManager stringManager = StringManagerFactory.getStringManager(OrbadaOraclePlugin.class);

  private Database database;
  private String sqlText;
  private ExplainPlanPanel planPanel;
  private ISettings settings;

  public static void show(Database database, String sqlText) {
    ExplainPlanDialog dialog = new ExplainPlanDialog(database, sqlText);
    dialog.setVisible(true);
  }

  /** Creates new form ExplainPlanDialog */
  public ExplainPlanDialog(Database database, String sqlText) {
    super(SwingUtil.getRootFrame(), true);
    this.database = database;
    this.sqlText = sqlText;
    initComponents();
    init();
  }

  private void init() {
    panelExplaintPlan.add(planPanel = new ExplainPlanPanel(database, sqlText));
    getRootPane().setDefaultButton(buttonClose);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmClose.getShortCut(), "cmClose");
    getRootPane().getActionMap().put("cmClose", cmClose);
    settings = Application.get().getSettings("oracle-explain-plan-dialog");
    try {
      setBounds(0, 0, settings.getValue("width", (long)getWidth()).intValue(), settings.getValue("height", (long)getHeight()).intValue());
    } catch (Exception ex) {
    }
    SwingUtil.setButtonSizesTheSame(new AbstractButton[] {buttonClose});
    SwingUtil.centerWithinScreen(this);
  }

  @Override
  public void dispose() {
    try {
      planPanel.close();
    } catch (IOException ex) {
    }
    settings.setValue("width", (long)getWidth());
    settings.setValue("height", (long)getHeight());
    settings.store();
    super.dispose();
  }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmClose = new pl.mpak.sky.gui.swing.Action();
    buttonClose = new javax.swing.JButton();
    panelExplaintPlan = new javax.swing.JPanel();

    cmClose.setActionCommandKey("cmClose");
    cmClose.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmClose.setText(stringManager.getString("cmClose-text")); // NOI18N
    cmClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCloseActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("ExplainPlanDialog-title")); // NOI18N

    buttonClose.setAction(cmClose);
    buttonClose.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonClose.setPreferredSize(new java.awt.Dimension(85, 25));

    panelExplaintPlan.setLayout(new java.awt.BorderLayout());

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(buttonClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(panelExplaintPlan, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(panelExplaintPlan, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(buttonClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void cmCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCloseActionPerformed
      dispose();
    }//GEN-LAST:event_cmCloseActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonClose;
  private pl.mpak.sky.gui.swing.Action cmClose;
  private javax.swing.JPanel panelExplaintPlan;
  // End of variables declaration//GEN-END:variables

}
