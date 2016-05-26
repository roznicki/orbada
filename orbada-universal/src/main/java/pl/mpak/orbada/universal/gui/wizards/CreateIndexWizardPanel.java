package pl.mpak.orbada.universal.gui.wizards;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JTextField;
import pl.mpak.orbada.plugins.dbinfo.jdbc.JdbcDbSchemaInfo;
import pl.mpak.orbada.plugins.dbinfo.jdbc.JdbcDbTableInfo;
import pl.mpak.orbada.universal.OrbadaUniversalPlugin;
import pl.mpak.orbada.universal.gui.util.TableColumnComboBoxModel;
import pl.mpak.orbada.universal.gui.util.TableComboBoxModel;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.util.SQLUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.StringUtil;

/**
 *
 * @author  akaluza
 */
public class CreateIndexWizardPanel extends SqlCodeWizardPanel {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("universal");

  private Database database;
  private String schemaName;
  private String tableName;
  
  /** Creates new form CreateIndexWizardPanel
   * @param database
   * @param schemaName
   * @param tableName
   */
  public CreateIndexWizardPanel(Database database, String schemaName, String tableName) {
    this.database = database;
    this.schemaName = schemaName;
    this.tableName = tableName;
    initComponents();
    init();
  }
  
  private void init() {
    comboColumns.setModel(new TableColumnComboBoxModel(database));
    comboTables.setModel(new TableComboBoxModel(database));

    comboTables.addItemListener(new ItemListener() {
      JdbcDbTableInfo lastTable;
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() != null && e.getItem() instanceof JdbcDbTableInfo) {
          JdbcDbTableInfo ti = (JdbcDbTableInfo)e.getItem();
          if (!ti.equals(lastTable) || lastTable == null) {
            JdbcDbSchemaInfo schema = ti.getSchema();
            ((TableColumnComboBoxModel)comboColumns.getModel()).change(schema == null ? null : schema.getName(), ti.getName());
            lastTable = ti;
          }
        }
      }
    });
  }
  
  public void wizardShow() {
    ((TableComboBoxModel)comboTables.getModel()).change(schemaName);
    ((TableComboBoxModel)comboTables.getModel()).select(tableName, comboTables);
  }
  
  public String getDialogTitle() {
    return stringManager.getString("CreateIndexWizardPanel-dialog-title");
  }
  
  public String getTabTitle() {
    return stringManager.getString("CreateIndexWizardPanel-tab-title");
  }
  
  public String getSqlCode() {
    return
      "CREATE" +(checkUnique.isSelected() ? " UNIQUE" : "") +
      " INDEX " +textIndexName.getText() +
      " ON " +SQLUtil.createSqlName(schemaName, comboTables.getSelectedItem().toString()) +
      "(" +StringUtil.nvl(((JTextField)comboColumns.getEditor().getEditorComponent()).getText(), "").toString() +")";
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

    jLabel1 = new javax.swing.JLabel();
    textIndexName = new pl.mpak.sky.gui.swing.comp.TextField();
    checkUnique = new javax.swing.JCheckBox();
    jLabel2 = new javax.swing.JLabel();
    comboTables = new javax.swing.JComboBox();
    jLabel3 = new javax.swing.JLabel();
    comboColumns = new javax.swing.JComboBox();

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText(stringManager.getString("name-dd")); // NOI18N

    checkUnique.setText(stringManager.getString("unique")); // NOI18N
    checkUnique.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    checkUnique.setMargin(new java.awt.Insets(0, 0, 0, 0));

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setText(stringManager.getString("table-dd")); // NOI18N

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel3.setText(stringManager.getString("expression-dd")); // NOI18N

    comboColumns.setEditable(true);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboTables, 0, 246, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(checkUnique)
                .addGap(74, 74, 74))
              .addComponent(textIndexName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboColumns, 0, 246, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(textIndexName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkUnique)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(comboTables, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(comboColumns, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkUnique;
  private javax.swing.JComboBox comboColumns;
  private javax.swing.JComboBox comboTables;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private pl.mpak.sky.gui.swing.comp.TextField textIndexName;
  // End of variables declaration//GEN-END:variables
  
}
