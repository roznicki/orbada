package pl.mpak.orbada.firebird.gui.wizards;

import javax.swing.DefaultComboBoxModel;
import pl.mpak.orbada.firebird.OrbadaFirebirdPlugin;
import pl.mpak.orbada.firebird.Sql;
import pl.mpak.orbada.universal.gui.wizards.SqlCodeWizardPanel;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.Query;
import pl.mpak.usedb.util.QueryUtil;
import pl.mpak.usedb.util.SQLUtil;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class RevokeTablePrivilegesWizard extends SqlCodeWizardPanel {

  private StringManager stringManager = StringManagerFactory.getStringManager("firebird");

  private Database database;
  private String objectName;

  public RevokeTablePrivilegesWizard(Database database, String objectName) {
    this.database = database;
    this.objectName = objectName;
    initComponents();
    init();
  }

  private void init() {
  }
  
  public void wizardShow() {
    Query query = database.createQuery();
    try {
      query.open(Sql.getTabledNameList());
      comboObjects.setModel(new DefaultComboBoxModel(QueryUtil.queryToArray(query)));
      comboObjects.setSelectedItem(objectName);
      query.open(Sql.getUserNameList());
      comboUsers.setModel(new DefaultComboBoxModel(QueryUtil.queryToArray(query)));
      query.open(Sql.getViewNameList());
      comboViews.setModel(new DefaultComboBoxModel(QueryUtil.queryToArray(query)));
      query.open(Sql.getProcedureNameList());
      comboProcedures.setModel(new DefaultComboBoxModel(QueryUtil.queryToArray(query)));
      query.open(Sql.getTriggerNameList());
      comboTriggers.setModel(new DefaultComboBoxModel(QueryUtil.queryToArray(query)));
    }
    catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
    finally {
      query.close();
    }
  }

  public String getDialogTitle() {
    return stringManager.getString("RevokeTablePrivilegesWizard-dialog-title");
  }

  public String getTabTitle() {
    return stringManager.getString("RevokeTablePrivilegesWizard-tab-title");
  }

  public String getSqlCode() {
    String privs = "";
    if (!checkAll.isSelected()) {
      if (checkSelect.isSelected()) {
        privs = privs +"SELECT";
      }
      if (checkInsert.isSelected()) {
        if (privs.length() > 0) {
          privs = privs +", ";
        }
        privs = privs +"INSERT";
      }
      if (checkUpdate.isSelected()) {
        if (privs.length() > 0) {
          privs = privs +", ";
        }
        privs = privs +"UPDATE";
      }
      if (checkDelete.isSelected()) {
        if (privs.length() > 0) {
          privs = privs +", ";
        }
        privs = privs +"DELETE";
      }
      if (checkReference.isSelected()) {
        if (privs.length() > 0) {
          privs = privs +", ";
        }
        privs = privs +"REFERENCES";
      }
    }
    else {
      privs = "ALL PRIVILEGES";
    }
    String object = "";
    if (radioUser.isSelected()) {
      object = SQLUtil.createSqlName(comboUsers.getText());
    }
    else if (radioProcedure.isSelected()) {
      object = "PROCEDURE " +SQLUtil.createSqlName(comboProcedures.getText());
    }
    else if (radioTrigger.isSelected()) {
      object = "TRIGGER " +SQLUtil.createSqlName(comboTriggers.getText());
    }
    else if (radioView.isSelected()) {
      object = "VIEW " +SQLUtil.createSqlName(comboViews.getText());
    }
    return 
      "REVOKE" +
      (checkGrantOption.isSelected() ? " GRANT OPTION FOR" : "") +
      " " +privs +
      " ON " +SQLUtil.createSqlName(comboObjects.getText()) +
      " FROM " +object;
  }

  public boolean execute() {
    try {
      database.executeCommand(getSqlCode());
      return true;
    } catch (Exception ex) {
      MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
      return false;
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

    groupObject = new javax.swing.ButtonGroup();
    jLabel5 = new javax.swing.JLabel();
    comboObjects = new pl.mpak.sky.gui.swing.comp.ComboBox();
    jPanel1 = new javax.swing.JPanel();
    checkAll = new javax.swing.JCheckBox();
    checkSelect = new javax.swing.JCheckBox();
    checkInsert = new javax.swing.JCheckBox();
    checkUpdate = new javax.swing.JCheckBox();
    checkDelete = new javax.swing.JCheckBox();
    checkReference = new javax.swing.JCheckBox();
    radioUser = new javax.swing.JRadioButton();
    comboUsers = new pl.mpak.sky.gui.swing.comp.ComboBox();
    radioProcedure = new javax.swing.JRadioButton();
    comboProcedures = new pl.mpak.sky.gui.swing.comp.ComboBox();
    comboTriggers = new pl.mpak.sky.gui.swing.comp.ComboBox();
    radioTrigger = new javax.swing.JRadioButton();
    comboViews = new pl.mpak.sky.gui.swing.comp.ComboBox();
    radioView = new javax.swing.JRadioButton();
    checkGrantOption = new javax.swing.JCheckBox();

    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel5.setText(stringManager.getString("RevokeTablePrivilegesWizard-privilege-for-object-dd")); // NOI18N

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Prawa"));

    checkAll.setText(stringManager.getString("RevokeTablePrivilegesWizard-all")); // NOI18N
    checkAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        checkAllActionPerformed(evt);
      }
    });

    checkSelect.setText("SELECT");

    checkInsert.setText("INSERT");

    checkUpdate.setText("UPDATE");

    checkDelete.setText("DELETE");

    checkReference.setText("REFERENCE");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(checkAll)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(checkSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkInsert, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkReference, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addComponent(checkAll)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(checkSelect)
          .addComponent(checkInsert)
          .addComponent(checkUpdate)
          .addComponent(checkDelete)
          .addComponent(checkReference))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    groupObject.add(radioUser);
    radioUser.setSelected(true);
    radioUser.setText(stringManager.getString("RevokeTablePrivilegesWizard-for-user")); // NOI18N

    comboUsers.setEditable(true);

    groupObject.add(radioProcedure);
    radioProcedure.setText(stringManager.getString("RevokeTablePrivilegesWizard-for-procedure")); // NOI18N

    groupObject.add(radioTrigger);
    radioTrigger.setText(stringManager.getString("RevokeTablePrivilegesWizard-for-trigger")); // NOI18N

    groupObject.add(radioView);
    radioView.setText(stringManager.getString("RevokeTablePrivilegesWizard-for-view")); // NOI18N

    checkGrantOption.setText(stringManager.getString("RevokeTablePrivilegesWizard-grant-option")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(checkGrantOption)
              .addComponent(comboObjects, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
          .addGroup(layout.createSequentialGroup()
            .addComponent(radioView, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboViews, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(radioUser, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(radioProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboProcedures, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(radioTrigger, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboTriggers, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(comboObjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkGrantOption)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(radioUser)
          .addComponent(comboUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(radioProcedure)
          .addComponent(comboProcedures, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(radioTrigger)
          .addComponent(comboTriggers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(radioView)
          .addComponent(comboViews, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

private void checkAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAllActionPerformed
  checkSelect.setEnabled(!checkAll.isSelected());
  checkInsert.setEnabled(!checkAll.isSelected());
  checkUpdate.setEnabled(!checkAll.isSelected());
  checkDelete.setEnabled(!checkAll.isSelected());
  checkReference.setEnabled(!checkAll.isSelected());
}//GEN-LAST:event_checkAllActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkAll;
  private javax.swing.JCheckBox checkDelete;
  private javax.swing.JCheckBox checkGrantOption;
  private javax.swing.JCheckBox checkInsert;
  private javax.swing.JCheckBox checkReference;
  private javax.swing.JCheckBox checkSelect;
  private javax.swing.JCheckBox checkUpdate;
  private pl.mpak.sky.gui.swing.comp.ComboBox comboObjects;
  private pl.mpak.sky.gui.swing.comp.ComboBox comboProcedures;
  private pl.mpak.sky.gui.swing.comp.ComboBox comboTriggers;
  private pl.mpak.sky.gui.swing.comp.ComboBox comboUsers;
  private pl.mpak.sky.gui.swing.comp.ComboBox comboViews;
  private javax.swing.ButtonGroup groupObject;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JRadioButton radioProcedure;
  private javax.swing.JRadioButton radioTrigger;
  private javax.swing.JRadioButton radioUser;
  private javax.swing.JRadioButton radioView;
  // End of variables declaration//GEN-END:variables

}
