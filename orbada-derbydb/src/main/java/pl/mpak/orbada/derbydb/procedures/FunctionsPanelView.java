package pl.mpak.orbada.derbydb.procedures;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.io.Closeable;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import orbada.gui.comps.table.ViewTable;
import pl.mpak.orbada.derbydb.DerbyDbSql;
import pl.mpak.orbada.derbydb.OrbadaDerbyDbPlugin;
import orbada.gui.IRootTabObjectInfo;
import orbada.gui.ITabObjectInfo;
import orbada.gui.cm.ComponentActionsAction;
import orbada.gui.util.SimpleSelectDialog;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.universal.gui.filter.SqlFilter;
import pl.mpak.orbada.universal.gui.filter.SqlFilterDef;
import pl.mpak.orbada.universal.gui.filter.SqlFilterDefComponent;
import pl.mpak.orbada.universal.gui.filter.SqlFilterDialog;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.sky.gui.swing.TabCloseComponent;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.Query;
import pl.mpak.usedb.gui.swing.QueryTableCellRenderer;
import pl.mpak.usedb.gui.swing.QueryTableColumn;
import pl.mpak.usedb.util.QueryUtil;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.variant.Variant;

/**
 *
 * @author  akaluza
 */
public class FunctionsPanelView extends javax.swing.JPanel implements IRootTabObjectInfo {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager(OrbadaDerbyDbPlugin.class);

  private IViewAccesibilities accesibilities;
  private String currentSchemaName;
  private String tabTitle;
  private SqlFilter filter;
  private boolean viewClosing = false;
  
  /** Creates new form FunctionsPanelView 
   * @param accesibilities 
   */
  public FunctionsPanelView(IViewAccesibilities accesibilities) {
    this.accesibilities = accesibilities;
    tabTitle = this.accesibilities.getTabTitle();
    initComponents();
    init();
  }
  
  private void init() {
    currentSchemaName = getDatabase().getUserName().toUpperCase();
    
    tableFuncs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        String procName = "";
        int rowIndex = tableFuncs.getSelectedRow();
        if (rowIndex >= 0 && tableFuncs.getQuery().isActive()) {
          try {
            tableFuncs.getQuery().getRecord(rowIndex);
            procName = tableFuncs.getQuery().fieldByName("alias").getString();
          } catch (Exception ex) {
            ExceptionUtil.processException(ex);
          }
        }
        for (int i=0; i<tabbedViewInfo.getTabCount(); i++) {
          Component c = tabbedViewInfo.getComponentAt(i);
          if (c instanceof ITabObjectInfo) {
            ((ITabObjectInfo)c).refresh(null, currentSchemaName, procName);
          }
        }
      }
    });
    addInfoPanel(stringManager.getString("FunctionsPanelView-parameters"), new ParametersPanel(accesibilities));
    addInfoPanel(stringManager.getString("FunctionsPanelView-source"), new ProcedureSourcePanel(accesibilities));
    
    tableFuncs.getQuery().setDatabase(getDatabase());
    tableFuncs.addColumn(new QueryTableColumn("alias", stringManager.getString("FunctionsPanelView-function-name"), 150, new QueryTableCellRenderer(java.awt.Font.BOLD)));
    tableFuncs.addColumn(new QueryTableColumn("javaclassname", stringManager.getString("FunctionsPanelView-class-name"), 250));
    SqlFilterDef def = new SqlFilterDef();
    def.add(new SqlFilterDefComponent("a.alias", stringManager.getString("FunctionsPanelView-function-name"), (String[])null));
    filter = new SqlFilter(
      accesibilities.getApplication().getSettings(getDatabase().getUserProperties().getProperty("schemaId"), "derbydb-functions-filter"),
      cmFilter, buttonFilter, 
      def);
    new ComponentActionsAction(getDatabase(), tableFuncs, buttonActions, menuActions, "derbydb-functions-actions");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        tableFuncs.requestFocusInWindow();
      }
    });
  }
  
  public void setCurrentSchemaName(String schemaName) {
    if (!currentSchemaName.equals(schemaName)) {
      currentSchemaName = schemaName;
      if (!currentSchemaName.equalsIgnoreCase(getDatabase().getUserName())) {
        accesibilities.setTabTitle(tabTitle +" (" +currentSchemaName +")");
      }
      else {
        accesibilities.setTabTitle(tabTitle);
      }
    }
  }
  
  public void refresh() {
    if (!isVisible() || viewClosing) {
      return;
    }
    try {
      String funcName = null;
      if (tableFuncs.getQuery().isActive() && tableFuncs.getSelectedRow() >= 0) {
        tableFuncs.getQuery().getRecord(tableFuncs.getSelectedRow());
        funcName = tableFuncs.getQuery().fieldByName("alias").getString();
      }
      refresh(funcName);
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
  }
  
  public void refresh(String objectName) {
    try {
      tableFuncs.getQuery().close();
      tableFuncs.getQuery().setSqlText(DerbyDbSql.getProcedureList(filter.getSqlText()));
      tableFuncs.getQuery().paramByName("schemaname").setString(currentSchemaName);
      tableFuncs.getQuery().paramByName("aliastype").setString("F");
      tableFuncs.getQuery().open();
      if (objectName != null && tableFuncs.getQuery().locate("alias", new Variant(objectName))) {
        tableFuncs.changeSelection(tableFuncs.getQuery().getCurrentRecord().getIndex(), tableFuncs.getSelectedColumn());
      } else if (!tableFuncs.getQuery().isEmpty()) {
        tableFuncs.changeSelection(0, tableFuncs.getSelectedColumn());
      }
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
  }
  
  private void addInfoPanel(String title, JPanel panel) {
    tabbedViewInfo.addTab(title, panel);
    tabbedViewInfo.setTabComponentAt(tabbedViewInfo.indexOfComponent(panel), new TabCloseComponent(title));
  }
  
  public boolean canClose() {
    return true;
  }

  public void close() throws IOException {
    viewClosing = true;
    int i = 0;
    while (i<tabbedViewInfo.getTabCount()) {
      Component c = tabbedViewInfo.getComponentAt(i);
      if (c instanceof Closeable) {
        try {
          ((Closeable)c).close();
        } catch (IOException ex) {
        }
      } else {
        i++;
      }
      tabbedViewInfo.remove(c);
    }
    tableFuncs.getQuery().close();
    accesibilities = null;
  }
  
  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmRefresh = new pl.mpak.sky.gui.swing.Action();
    cmSelectSchema = new pl.mpak.sky.gui.swing.Action();
    cmFilter = new pl.mpak.sky.gui.swing.Action();
    menuActions = new javax.swing.JPopupMenu();
    splinPane = new javax.swing.JSplitPane();
    panelViews = new javax.swing.JPanel();
    toolBarViews = new javax.swing.JToolBar();
    buttonRefresh = new pl.mpak.sky.gui.swing.comp.ToolButton();
    buttonSelectSchema = new pl.mpak.sky.gui.swing.comp.ToolButton();
    buttonFilter = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    buttonActions = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableFuncs = new ViewTable();
    statusBarViews = new pl.mpak.usedb.gui.swing.QueryTableStatusBar();
    tabbedViewInfo = new javax.swing.JTabbedPane();

    cmRefresh.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/pl/mpak/res/icons/refresh16.gif")); // NOI18N
    cmRefresh.setText(stringManager.getString("cmRefresh-text")); // NOI18N
    cmRefresh.setTooltip(stringManager.getString("cmRefresh-hint")); // NOI18N
    cmRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmRefreshActionPerformed(evt);
      }
    });

    cmSelectSchema.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/pl/mpak/res/icons/users16.gif")); // NOI18N
    cmSelectSchema.setText(stringManager.getString("cmSelectSchema-text")); // NOI18N
    cmSelectSchema.setTooltip(stringManager.getString("cmSelectSchema-hint")); // NOI18N
    cmSelectSchema.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmSelectSchemaActionPerformed(evt);
      }
    });

    cmFilter.setActionCommandKey("cmFilter");
    cmFilter.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/pl/mpak/orbada/derbydb/res/icons/filter16.gif")); // NOI18N
    cmFilter.setText(stringManager.getString("cmFilter-text")); // NOI18N
    cmFilter.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmFilterActionPerformed(evt);
      }
    });

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        formComponentShown(evt);
      }
    });
    setLayout(new java.awt.BorderLayout());

    splinPane.setBorder(null);
    splinPane.setDividerLocation(250);
    splinPane.setContinuousLayout(true);
    splinPane.setOneTouchExpandable(true);

    panelViews.setPreferredSize(new java.awt.Dimension(250, 100));
    panelViews.setLayout(new java.awt.BorderLayout());

    toolBarViews.setFloatable(false);
    toolBarViews.setRollover(true);

    buttonRefresh.setAction(cmRefresh);
    buttonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarViews.add(buttonRefresh);

    buttonSelectSchema.setAction(cmSelectSchema);
    buttonSelectSchema.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonSelectSchema.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarViews.add(buttonSelectSchema);

    buttonFilter.setAction(cmFilter);
    buttonFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarViews.add(buttonFilter);
    toolBarViews.add(jSeparator1);

    buttonActions.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonActions.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarViews.add(buttonActions);

    panelViews.add(toolBarViews, java.awt.BorderLayout.PAGE_START);

    jScrollPane1.setViewportView(tableFuncs);

    panelViews.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    statusBarViews.setShowFieldType(false);
    statusBarViews.setShowFieldValue(false);
    statusBarViews.setShowOpenTime(false);
    statusBarViews.setTable(tableFuncs);
    panelViews.add(statusBarViews, java.awt.BorderLayout.PAGE_END);

    splinPane.setLeftComponent(panelViews);

    tabbedViewInfo.setFocusable(false);
    splinPane.setRightComponent(tabbedViewInfo);

    add(splinPane, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
  if (!tableFuncs.getQuery().isActive()) {
    refresh();
  }
}//GEN-LAST:event_formComponentShown

private void cmFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmFilterActionPerformed
  if (SqlFilterDialog.show(filter)) {
    refresh();
  }
}//GEN-LAST:event_cmFilterActionPerformed

private void cmSelectSchemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmSelectSchemaActionPerformed
  Query query = getDatabase().createQuery();
  try {
    query.open("select schemaname from sys.sysschemas order by schemaname");
    Vector<String> vl = QueryUtil.staticData("{schemaname}", query);
    Point point = buttonSelectSchema.getLocationOnScreen();
    point.y+= buttonSelectSchema.getHeight();
    SimpleSelectDialog.select((Window)SwingUtil.getWindowComponent(this), point.x, point.y, vl, vl.indexOf(currentSchemaName), new SimpleSelectDialog.CallBack() {
      public void selected(Object o) {
        setCurrentSchemaName(o.toString());
        refresh();
      }
    });
  } catch (Exception ex) {
    ExceptionUtil.processException(ex);
  } finally {
    query.close();
  }
}//GEN-LAST:event_cmSelectSchemaActionPerformed

private void cmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmRefreshActionPerformed
  refresh();
}//GEN-LAST:event_cmRefreshActionPerformed
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonActions;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonFilter;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonRefresh;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonSelectSchema;
  private pl.mpak.sky.gui.swing.Action cmFilter;
  private pl.mpak.sky.gui.swing.Action cmRefresh;
  private pl.mpak.sky.gui.swing.Action cmSelectSchema;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JToolBar.Separator jSeparator1;
  private javax.swing.JPopupMenu menuActions;
  private javax.swing.JPanel panelViews;
  private javax.swing.JSplitPane splinPane;
  private pl.mpak.usedb.gui.swing.QueryTableStatusBar statusBarViews;
  private javax.swing.JTabbedPane tabbedViewInfo;
  private ViewTable tableFuncs;
  private javax.swing.JToolBar toolBarViews;
  // End of variables declaration//GEN-END:variables

}