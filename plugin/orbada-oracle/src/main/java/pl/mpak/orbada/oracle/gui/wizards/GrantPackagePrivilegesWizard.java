package pl.mpak.orbada.oracle.gui.wizards;

import javax.swing.DefaultListModel;
import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.orbada.oracle.dbinfo.OraclePackageInfo;
import pl.mpak.orbada.oracle.gui.util.PackageComboBoxModel;
import pl.mpak.orbada.oracle.gui.util.PackageItemListener;
import pl.mpak.orbada.oracle.gui.util.SchemaComboBoxModel;
import pl.mpak.orbada.universal.gui.wizards.SqlCodeWizardPanel;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.util.SQLUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class GrantPackagePrivilegesWizard extends SqlCodeWizardPanel {

  private final StringManager stringManager = StringManagerFactory.getStringManager("oracle");

  private Database database;
  private String schemaName;
  private String objectName;
  
  private static String[] privileges = {
    "EXECUTE", "DEBUG"
  };

  public GrantPackagePrivilegesWizard(Database database, String schemaName, String objectName) {
    this.database = database;
    this.schemaName = schemaName;
    this.objectName = objectName;
    initComponents();
    init();
  }

  private void init() {
    listAvailPrivs.setModel(new DefaultListModel());
    listSelectedPrivs.setModel(new DefaultListModel());
    comboObject.setModel(new PackageComboBoxModel(database));
    comboObject.addItemListener(new PackageItemListener() {
      public void itemChanged(OraclePackageInfo info) {
        updateEventList(info);
      }
    });
    comboSchemas.setModel(new SchemaComboBoxModel(database));
  }

  public void wizardShow() {
    ((PackageComboBoxModel)comboObject.getModel()).change(schemaName);
    ((PackageComboBoxModel)comboObject.getModel()).select(objectName, comboObject);
    ((SchemaComboBoxModel)comboSchemas.getModel()).change();
    ((SchemaComboBoxModel)comboSchemas.getModel()).select(schemaName, comboSchemas);
  }
  
  private void updateMoveActions() {
    cmMoveRight.setEnabled(listAvailPrivs.getSelectedValue() != null && !checkAll.isSelected());
    cmMoveAllRight.setEnabled(listAvailPrivs.getModel().getSize() > 0 && !checkAll.isSelected());
    cmMoveLeft.setEnabled(listSelectedPrivs.getSelectedValue() != null && !checkAll.isSelected());
    cmMoveAllLeft.setEnabled(listSelectedPrivs.getModel().getSize() > 0 && !checkAll.isSelected());
  }

  private void updateEventList(OraclePackageInfo info) {
    DefaultListModel model = (DefaultListModel)listSelectedPrivs.getModel();
    model.removeAllElements();
    model = (DefaultListModel)listAvailPrivs.getModel();
    model.removeAllElements();
    for (String s : privileges) {
      model.addElement(s);
    }
    updateMoveActions();
  }
      
  public String getDialogTitle() {
    return stringManager.getString("GrantPackagePrivilegesWizard-dialog-title");
  }

  public String getTabTitle() {
    return stringManager.getString("GrantPackagePrivilegesWizard-tab-title");
  }

  public String getSqlCode() {
    String privs = "";
    if (!checkAll.isSelected()) {
      DefaultListModel sel = (DefaultListModel)listSelectedPrivs.getModel();
      if (sel.getSize() > 0) {
        for (int i=0; i<sel.getSize(); i++) {
          if (i > 0) {
            privs = privs +", ";
          }
          privs = privs +sel.getElementAt(i);
        }
      }
    }
    else {
      privs = "ALL";
    }
    return 
      "GRANT " +privs +
      " ON " +SQLUtil.createSqlName(comboObject.getSelectedItem().toString()) +
      " TO " +SQLUtil.createSqlName(comboSchemas.getSelectedItem().toString());
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
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmMoveRight = new pl.mpak.sky.gui.swing.Action();
    cmMoveAllRight = new pl.mpak.sky.gui.swing.Action();
    cmMoveLeft = new pl.mpak.sky.gui.swing.Action();
    cmMoveAllLeft = new pl.mpak.sky.gui.swing.Action();
    jLabel5 = new javax.swing.JLabel();
    comboObject = new javax.swing.JComboBox();
    labelAvailPrivs = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    comboSchemas = new javax.swing.JComboBox();
    jScrollPane1 = new javax.swing.JScrollPane();
    listAvailPrivs = new javax.swing.JList();
    buttonMoveRight = new javax.swing.JButton();
    buttonMoveAllRight = new javax.swing.JButton();
    buttonMoveRight1 = new javax.swing.JButton();
    buttonMoveAllRight1 = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    listSelectedPrivs = new javax.swing.JList();
    labelSelectedPrivs = new javax.swing.JLabel();
    checkAll = new javax.swing.JCheckBox();

    cmMoveRight.setActionCommandKey("cmMoveRight");
    cmMoveRight.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/move_right.gif")); // NOI18N
    cmMoveRight.setText(stringManager.getString("cmMoveRight-text")); // NOI18N
    cmMoveRight.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmMoveRightActionPerformed(evt);
      }
    });

    cmMoveAllRight.setActionCommandKey("cmMoveAllRight");
    cmMoveAllRight.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/move_all_right.gif")); // NOI18N
    cmMoveAllRight.setText(stringManager.getString("cmMoveAllRight-text")); // NOI18N
    cmMoveAllRight.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmMoveAllRightActionPerformed(evt);
      }
    });

    cmMoveLeft.setActionCommandKey("cmMoveLeft");
    cmMoveLeft.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/move_left.gif")); // NOI18N
    cmMoveLeft.setText(stringManager.getString("cmMoveLeft-text")); // NOI18N
    cmMoveLeft.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmMoveLeftActionPerformed(evt);
      }
    });

    cmMoveAllLeft.setActionCommandKey("cmMoveAllLeft");
    cmMoveAllLeft.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/move_all_left.gif")); // NOI18N
    cmMoveAllLeft.setText(stringManager.getString("cmMoveAllLeft-text")); // NOI18N
    cmMoveAllLeft.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmMoveAllLeftActionPerformed(evt);
      }
    });

    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel5.setText(stringManager.getString("privilege-to-object-dd")); // NOI18N

    labelAvailPrivs.setText(stringManager.getString("available-privileges-dd")); // NOI18N

    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel6.setText(stringManager.getString("for-schema-dd")); // NOI18N

    listAvailPrivs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    listAvailPrivs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        listAvailPrivsValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(listAvailPrivs);

    buttonMoveRight.setAction(cmMoveRight);
    buttonMoveRight.setHideActionText(true);
    buttonMoveRight.setMargin(new java.awt.Insets(1, 1, 1, 1));
    buttonMoveRight.setPreferredSize(new java.awt.Dimension(50, 23));

    buttonMoveAllRight.setAction(cmMoveAllRight);
    buttonMoveAllRight.setHideActionText(true);
    buttonMoveAllRight.setMargin(new java.awt.Insets(1, 1, 1, 1));
    buttonMoveAllRight.setPreferredSize(new java.awt.Dimension(50, 23));

    buttonMoveRight1.setAction(cmMoveLeft);
    buttonMoveRight1.setHideActionText(true);
    buttonMoveRight1.setMargin(new java.awt.Insets(1, 1, 1, 1));
    buttonMoveRight1.setPreferredSize(new java.awt.Dimension(50, 23));

    buttonMoveAllRight1.setAction(cmMoveAllLeft);
    buttonMoveAllRight1.setHideActionText(true);
    buttonMoveAllRight1.setMargin(new java.awt.Insets(1, 1, 1, 1));
    buttonMoveAllRight1.setPreferredSize(new java.awt.Dimension(50, 23));

    listSelectedPrivs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    listSelectedPrivs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        listSelectedPrivsValueChanged(evt);
      }
    });
    jScrollPane2.setViewportView(listSelectedPrivs);

    labelSelectedPrivs.setText(stringManager.getString("granted-privileges-dd")); // NOI18N

    checkAll.setText(stringManager.getString("all")); // NOI18N
    checkAll.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        checkAllItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(checkAll)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboObject, 0, 235, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboSchemas, 0, 235, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(buttonMoveAllRight1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(buttonMoveRight1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(buttonMoveAllRight, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(buttonMoveRight, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addComponent(labelAvailPrivs))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(labelSelectedPrivs)
              .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(comboObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel6)
          .addComponent(comboSchemas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkAll)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(labelSelectedPrivs)
              .addComponent(labelAvailPrivs))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(buttonMoveRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonMoveAllRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonMoveRight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonMoveAllRight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void listAvailPrivsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listAvailPrivsValueChanged
    updateMoveActions();
}//GEN-LAST:event_listAvailPrivsValueChanged

  private void listSelectedPrivsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSelectedPrivsValueChanged
    updateMoveActions();
}//GEN-LAST:event_listSelectedPrivsValueChanged

  private void cmMoveRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmMoveRightActionPerformed
    DefaultListModel sel = (DefaultListModel)listSelectedPrivs.getModel();
    DefaultListModel tab = (DefaultListModel)listAvailPrivs.getModel();
    sel.addElement(listAvailPrivs.getSelectedValue());
    tab.removeElement(listAvailPrivs.getSelectedValue());
    updateMoveActions();
  }//GEN-LAST:event_cmMoveRightActionPerformed

  private void cmMoveLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmMoveLeftActionPerformed
    DefaultListModel sel = (DefaultListModel)listSelectedPrivs.getModel();
    DefaultListModel tab = (DefaultListModel)listAvailPrivs.getModel();
    tab.addElement(listSelectedPrivs.getSelectedValue());
    sel.removeElement(listSelectedPrivs.getSelectedValue());
    updateMoveActions();
  }//GEN-LAST:event_cmMoveLeftActionPerformed

  private void cmMoveAllRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmMoveAllRightActionPerformed
    DefaultListModel sel = (DefaultListModel)listSelectedPrivs.getModel();
    DefaultListModel tab = (DefaultListModel)listAvailPrivs.getModel();
    for (int i=0; i<tab.getSize(); i++) {
      sel.addElement(tab.get(i));
    }
    tab.removeAllElements();
    updateMoveActions();
  }//GEN-LAST:event_cmMoveAllRightActionPerformed

  private void cmMoveAllLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmMoveAllLeftActionPerformed
    DefaultListModel sel = (DefaultListModel)listSelectedPrivs.getModel();
    DefaultListModel tab = (DefaultListModel)listAvailPrivs.getModel();
    for (int i=0; i<sel.getSize(); i++) {
      tab.addElement(sel.get(i));
    }
    sel.removeAllElements();
    updateMoveActions();
  }//GEN-LAST:event_cmMoveAllLeftActionPerformed

  private void checkAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkAllItemStateChanged
    labelAvailPrivs.setEnabled(!checkAll.isSelected());
    listAvailPrivs.setEnabled(!checkAll.isSelected());
    labelSelectedPrivs.setEnabled(!checkAll.isSelected());
    listSelectedPrivs.setEnabled(!checkAll.isSelected());
    updateMoveActions();
  }//GEN-LAST:event_checkAllItemStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonMoveAllRight;
  private javax.swing.JButton buttonMoveAllRight1;
  private javax.swing.JButton buttonMoveRight;
  private javax.swing.JButton buttonMoveRight1;
  private javax.swing.JCheckBox checkAll;
  private pl.mpak.sky.gui.swing.Action cmMoveAllLeft;
  private pl.mpak.sky.gui.swing.Action cmMoveAllRight;
  private pl.mpak.sky.gui.swing.Action cmMoveLeft;
  private pl.mpak.sky.gui.swing.Action cmMoveRight;
  private javax.swing.JComboBox comboObject;
  private javax.swing.JComboBox comboSchemas;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JLabel labelAvailPrivs;
  private javax.swing.JLabel labelSelectedPrivs;
  private javax.swing.JList listAvailPrivs;
  private javax.swing.JList listSelectedPrivs;
  // End of variables declaration//GEN-END:variables
}
