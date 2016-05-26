package pl.mpak.orbada.sqlmacro.gui;

import java.beans.IntrospectionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JComponent;

import orbada.gui.comps.OrbadaSyntaxTextArea;
import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.sqlmacro.OrbadaSqlMacrosPlugin;
import pl.mpak.orbada.sqlmacro.db.SqlMacroRecord;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.UseDBException;
import pl.mpak.usedb.gui.RecordLink;
import pl.mpak.usedb.gui.linkreq.FieldRequeiredNotNull;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.patt.Resolvers;
import pl.mpak.util.variant.VariantType;

/**
 *
 * @author  akaluza
 */
public class SqlMacroEditDialog extends javax.swing.JDialog {

  private final StringManager stringManager = StringManagerFactory.getStringManager(OrbadaSqlMacrosPlugin.class);

  private IApplication application;
  private int modalResult = ModalResult.NONE;
  private SqlMacroRecord macro;
  private RecordLink dataLink;
  private String osm_id;
  
  /** Creates new form OgQueryInfoDialog */
  public SqlMacroEditDialog(IApplication application, String osm_id, SqlMacroRecord macro) throws IntrospectionException, UseDBException {
    super(SwingUtil.getRootFrame());
    this.application = application;
    this.osm_id = osm_id;
    this.macro = macro;
    initComponents();
    init();
  }
  
  public static String show(IApplication application, String osm_id) throws IntrospectionException, UseDBException {
    SqlMacroEditDialog dialog = new SqlMacroEditDialog(application, osm_id, null);
    dialog.setVisible(true);
    return (dialog.modalResult == ModalResult.OK ? dialog.osm_id : null);
  }
  
  public static String show(IApplication application, String osm_id, SqlMacroRecord macro) throws IntrospectionException, UseDBException {
    SqlMacroEditDialog dialog = new SqlMacroEditDialog(application, osm_id, macro);
    dialog.setVisible(true);
    return (dialog.modalResult == ModalResult.OK ? dialog.osm_id : null);
  }
  
  private void init() throws IntrospectionException, UseDBException {
    try {
      queryDriverTypes.open();
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }

    dataLink = new RecordLink();
    dataLink.add("OSM_NAME", editName, new FieldRequeiredNotNull(stringManager.getString("sql-macro-name")));
    dataLink.add("OSM_REGEXP", editRegexp, new FieldRequeiredNotNull(stringManager.getString("regular-expression")));
    dataLink.add("OSM_DTP_ID", comboDriverType, "selectedItem");
    dataLink.add("OSM_RESOLVE", editResolve, new FieldRequeiredNotNull(stringManager.getString("sql-macro-resolve")));
    dataLink.add("OSM_ORDER", editOrder, "text", VariantType.varInteger);
    
    if (macro == null) {
      if (osm_id != null) {
        macro = new SqlMacroRecord(application.getOrbadaDatabase(), osm_id);
      } else {
        macro = new SqlMacroRecord(application.getOrbadaDatabase());
      }
    }
    dataLink.updateComponents(macro);
    checkAllUsers.setSelected(macro.getUsrId() == null);
    if (!application.isUserAdmin()) {
      checkAllUsers.setEnabled(false);
      checkAllUsers.setSelected(true);
    }
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);
    
    SwingUtil.centerWithinScreen(this);
  }
  
  @Override
  public void dispose() {
    queryDriverTypes.close();
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

        cmOk = new pl.mpak.sky.gui.swing.Action();
        cmCancel = new pl.mpak.sky.gui.swing.Action();
        cmResolve = new pl.mpak.sky.gui.swing.Action();
        queryDriverTypes = new pl.mpak.usedb.core.Query();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        editName = new pl.mpak.sky.gui.swing.comp.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        editResolve = new OrbadaSyntaxTextArea();
        checkAllUsers = new javax.swing.JCheckBox();
        editRegexp = new pl.mpak.sky.gui.swing.comp.TextField();
        jLabel4 = new javax.swing.JLabel();
        editOrder = new pl.mpak.sky.gui.swing.comp.TextField();
        jPanel1 = new javax.swing.JPanel();
        editTestCommand = new pl.mpak.sky.gui.swing.comp.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        editTestResolve = new OrbadaSyntaxTextArea();
        buttonResolve = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        comboDriverType = new pl.mpak.usedb.gui.swing.QueryComboBox();

        cmOk.setActionCommandKey("cmOk");
        cmOk.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0));
        cmOk.setText(stringManager.getString("cmOk-text")); // NOI18N
        cmOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmOkActionPerformed(evt);
            }
        });

        cmCancel.setActionCommandKey("cmCancel");
        cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        cmCancel.setText(stringManager.getString("cmCancel-text")); // NOI18N
        cmCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmCancelActionPerformed(evt);
            }
        });

        cmResolve.setActionCommandKey("cmResolve");
        cmResolve.setText(stringManager.getString("cmResolve-text")); // NOI18N
        cmResolve.setTooltip(stringManager.getString("cmResolve-hint")); // NOI18N
        cmResolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmResolveActionPerformed(evt);
            }
        });

        queryDriverTypes.setDatabase(application.getOrbadaDatabase());
        try {
            queryDriverTypes.setSqlText("select dtp_id, dtp_name from driver_types\nunion all select null, 'Any/All' from driver_types where dtp_id = (select min(dtp_id) from driver_types)\norder by dtp_name");
        } catch (pl.mpak.usedb.UseDBException e1) {
            e1.printStackTrace();
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(stringManager.getString("SqlMacroEditDialog-title")); // NOI18N
        setModal(true);

        buttonOk.setAction(cmOk);
        buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
        buttonOk.setPreferredSize(new java.awt.Dimension(85, 25));

        buttonCancel.setAction(cmCancel);
        buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
        buttonCancel.setPreferredSize(new java.awt.Dimension(85, 25));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(stringManager.getString("sql-macro-name-dd")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(stringManager.getString("regular-expression-dd")); // NOI18N

        jLabel3.setText(stringManager.getString("sql-macro-resolve-dd")); // NOI18N

        checkAllUsers.setText(stringManager.getString("for-all-users")); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText(stringManager.getString("order-dd")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(stringManager.getString("macro-testing"))); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText(stringManager.getString("command-dd")); // NOI18N

        jLabel6.setText(stringManager.getString("sql-macro-resolve-dd")); // NOI18N

        editTestResolve.setEditable(false);

        buttonResolve.setAction(cmResolve);
        buttonResolve.setMargin(new java.awt.Insets(2, 2, 2, 2));
        buttonResolve.setPreferredSize(new java.awt.Dimension(85, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editTestResolve, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editTestCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonResolve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(editTestCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonResolve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editTestResolve, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText(stringManager.getString("for-driver-dd")); // NOI18N

        comboDriverType.setDisplayField("DTP_NAME");
        comboDriverType.setKeyField("DTP_ID");
        comboDriverType.setQuery(queryDriverTypes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editResolve, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkAllUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
                        .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editName, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editRegexp, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboDriverType, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(editName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editRegexp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboDriverType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(editOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editResolve, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAllUsers))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
try {
    dataLink.updateRecord(macro);
    if (checkAllUsers.isSelected()) {
      macro.setUsrId(null);
    }
    else {
      macro.setUsrId(application.getUserId());
    }
    if (macro.isChanged()) {
      if (osm_id == null) {
        macro.applyInsert();
        osm_id = macro.getId();
      } else {
        macro.applyUpdate();
      }
    }
    modalResult = ModalResult.OK;
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

private void cmResolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmResolveActionPerformed
  try {
    Pattern pattern = Pattern.compile(editRegexp.getText(), Pattern.CASE_INSENSITIVE);
    Matcher mat = pattern.matcher(editTestCommand.getText());
    if (mat.matches()) {
      String toResolve = Resolvers.expand(editResolve.getText());
      editTestResolve.setText(mat.replaceAll(toResolve));
    }
    else {
      editTestResolve.setText("");
    }
  }
  catch (PatternSyntaxException ex) {
    MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
  }
}//GEN-LAST:event_cmResolveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;
    private javax.swing.JButton buttonResolve;
    private javax.swing.JCheckBox checkAllUsers;
    private pl.mpak.sky.gui.swing.Action cmCancel;
    private pl.mpak.sky.gui.swing.Action cmOk;
    private pl.mpak.sky.gui.swing.Action cmResolve;
    private pl.mpak.usedb.gui.swing.QueryComboBox comboDriverType;
    private pl.mpak.sky.gui.swing.comp.TextField editName;
    private pl.mpak.sky.gui.swing.comp.TextField editOrder;
    private pl.mpak.sky.gui.swing.comp.TextField editRegexp;
    private OrbadaSyntaxTextArea editResolve;
    private pl.mpak.sky.gui.swing.comp.TextField editTestCommand;
    private OrbadaSyntaxTextArea editTestResolve;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private pl.mpak.usedb.core.Query queryDriverTypes;
    // End of variables declaration//GEN-END:variables

}
