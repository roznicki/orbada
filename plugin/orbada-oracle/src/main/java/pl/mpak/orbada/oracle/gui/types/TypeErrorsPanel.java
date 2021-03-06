package pl.mpak.orbada.oracle.gui.types;

import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JTabbedPane;

import pl.mpak.orbada.gui.comps.table.ViewTable;
import pl.mpak.orbada.oracle.Sql;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.gui.ITabObjectInfo;
import pl.mpak.orbada.gui.cm.ComponentActionsAction;
import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.Query;
import pl.mpak.usedb.gui.swing.QueryTableColumn;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class TypeErrorsPanel extends javax.swing.JPanel implements ITabObjectInfo {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("oracle");

  private IViewAccesibilities accesibilities;
  private String currentSchemaName = "";
  private String currentTriggerName = "";
  private String objectType;
  private boolean requestRefresh = false;
  private boolean closing = false;
  
  /** Creates new form TableIndexesPanel 
   * @param accesibilities 
   */
  public TypeErrorsPanel(IViewAccesibilities accesibilities, String objectType) {
    this.accesibilities = accesibilities;
    this.objectType = objectType;
    initComponents();
    init();
  }
  
  private void init() {
    tableErrors.getQuery().setDatabase(getDatabase());
    try {
      tableErrors.addColumn(new QueryTableColumn("line", stringManager.getString("err-line"), 50));
      tableErrors.addColumn(new QueryTableColumn("position", stringManager.getString("err-position"), 50));
      tableErrors.addColumn(new QueryTableColumn("text", stringManager.getString("err-text"), 450));
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
    SwingUtil.addAction(tableErrors, cmGotoSource);
    new ComponentActionsAction(getDatabase(), tableErrors, buttonActions, menuActions, "oracle-type-errors-actions");
  }
  
  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  public String getTitle() {
    return stringManager.getString("TypeErrorsPanel-title");
  }
  
  private void refreshTask() {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        refresh();
      }
    });
  }
  
  public void refresh() {
    try {
      requestRefresh = false;
      tableErrors.getQuery().close();
      tableErrors.getQuery().setSqlText(Sql.getObjectErrorList());
      tableErrors.getQuery().paramByName("schema_name").setString(currentSchemaName);
      tableErrors.getQuery().paramByName("object_name").setString(currentTriggerName);
      tableErrors.getQuery().paramByName("object_type").setString(objectType);
      tableErrors.getQuery().open();
      if (!tableErrors.getQuery().isEmpty()) {
        tableErrors.changeSelection(0, 0);
      }
    } catch (Exception ex) {
      MessageBox.show(stringManager.getString("error"), ex.getMessage(), ModalResult.OK);
    }
  }
  
  public void refresh(String catalogName, String schemaName, String objectName) {
    if (!currentSchemaName.equals(schemaName) || !currentTriggerName.equals(objectName) || requestRefresh) {
      currentSchemaName = schemaName;
      currentTriggerName = objectName;
      if (isVisible()) {
        refresh();
      }
      else {
        requestRefresh = true;
      }
    }
  }
  
  public Query getQuery() {
    return tableErrors.getQuery();
  }
  
  @Override
  public boolean canClose() {
    return true;
  }

  public void close() throws IOException {
    closing = true;
    tableErrors.getQuery().close();
    accesibilities = null;
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmRefresh = new pl.mpak.sky.gui.swing.Action();
    menuActions = new javax.swing.JPopupMenu();
    cmGotoSource = new pl.mpak.sky.gui.swing.Action();
    statusBarIndexes = new pl.mpak.usedb.gui.swing.QueryTableStatusBar();
    jPanel1 = new javax.swing.JPanel();
    toolBarIndexes = new javax.swing.JToolBar();
    buttonRefresh = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    menuGotoSource = new pl.mpak.sky.gui.swing.comp.ToolButton();
    buttonActions = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableErrors = new ViewTable();

    cmRefresh.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/refresh16.gif")); // NOI18N
    cmRefresh.setText(stringManager.getString("cmRefresh-text")); // NOI18N
    cmRefresh.setTooltip(stringManager.getString("cmRefresh-hint")); // NOI18N
    cmRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmRefreshActionPerformed(evt);
      }
    });

    cmGotoSource.setActionCommandKey("cmGotoSource");
    cmGotoSource.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
    cmGotoSource.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/descending.gif")); // NOI18N
    cmGotoSource.setText(stringManager.getString("cmGotoSource-text")); // NOI18N
    cmGotoSource.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmGotoSourceActionPerformed(evt);
      }
    });

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        formComponentShown(evt);
      }
    });
    setLayout(new java.awt.BorderLayout());

    statusBarIndexes.setShowFieldType(false);
    statusBarIndexes.setShowOpenTime(false);
    statusBarIndexes.setTable(tableErrors);
    add(statusBarIndexes, java.awt.BorderLayout.PAGE_END);

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

    toolBarIndexes.setFloatable(false);
    toolBarIndexes.setRollover(true);

    buttonRefresh.setAction(cmRefresh);
    buttonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarIndexes.add(buttonRefresh);
    toolBarIndexes.add(jSeparator1);

    menuGotoSource.setAction(cmGotoSource);
    menuGotoSource.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    menuGotoSource.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarIndexes.add(menuGotoSource);
    toolBarIndexes.add(buttonActions);

    jPanel1.add(toolBarIndexes);

    add(jPanel1, java.awt.BorderLayout.NORTH);

    tableErrors.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tableErrorsMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(tableErrors);

    add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
  if (requestRefresh && !closing) {
    refreshTask();
  }
}//GEN-LAST:event_formComponentShown

private void cmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmRefreshActionPerformed
  refresh();
}//GEN-LAST:event_cmRefreshActionPerformed

private void cmGotoSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmGotoSourceActionPerformed
  if (tableErrors.getSelectedRow() >= 0) {
    try {
      tableErrors.getQuery().getRecord(tableErrors.getSelectedRow());
      TypeSourcePanel source = (TypeSourcePanel)SwingUtil.getTabbedPaneComponent(TypeSourcePanel.class, this);
      if (source != null) {
        JTabbedPane tp = (JTabbedPane)SwingUtil.getOwnerComponent(JTabbedPane.class, this);
        if (tp != null) {
          tp.setSelectedComponent(source);
          source.gotoPoint(
            tableErrors.getQuery().fieldByName("line").getInteger(),
            tableErrors.getQuery().fieldByName("position").getInteger());
        }
      }
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
  }
}//GEN-LAST:event_cmGotoSourceActionPerformed

private void tableErrorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableErrorsMouseClicked
  if (tableErrors.getSelectedRow() >= 0 && evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
    cmGotoSource.performe();
  }
}//GEN-LAST:event_tableErrorsMouseClicked
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonActions;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonRefresh;
  private pl.mpak.sky.gui.swing.Action cmGotoSource;
  private pl.mpak.sky.gui.swing.Action cmRefresh;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JToolBar.Separator jSeparator1;
  private javax.swing.JPopupMenu menuActions;
  private pl.mpak.sky.gui.swing.comp.ToolButton menuGotoSource;
  private pl.mpak.usedb.gui.swing.QueryTableStatusBar statusBarIndexes;
  private ViewTable tableErrors;
  private javax.swing.JToolBar toolBarIndexes;
  // End of variables declaration//GEN-END:variables
  
}
