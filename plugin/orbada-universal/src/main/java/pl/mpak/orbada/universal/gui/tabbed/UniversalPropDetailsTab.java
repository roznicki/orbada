package pl.mpak.orbada.universal.gui.tabbed;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import pl.mpak.orbada.gui.comps.table.VerticalQueryTablePanel;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.gui.ITabObjectInfo;
import pl.mpak.orbada.gui.cm.ComponentActionsAction;
import pl.mpak.orbada.universal.OrbadaUniversalPlugin;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.UseDBException;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.ParametrizedCommand;
import pl.mpak.usedb.core.Query;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.StringUtil;

/**
 *
 * @author  akaluza
 */
public abstract class UniversalPropDetailsTab extends javax.swing.JPanel implements ITabObjectInfo {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("universal");

  protected IViewAccesibilities accesibilities;
  protected String currentSchemaName = "";
  protected String currentObjectName = "";
  protected boolean requestRefresh = false;
  protected boolean closing = false;
  protected VerticalQueryTablePanel tableInfoPanel;
  protected ComponentActionsAction componentActions;
  
  public UniversalPropDetailsTab(IViewAccesibilities accesibilities) {
    this.accesibilities = accesibilities;
    initComponents();
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        init();
      }
    });
  }
  
  private void init() {
    add(tableInfoPanel = new VerticalQueryTablePanel(getDatabase()), BorderLayout.CENTER);
    componentActions = new ComponentActionsAction(getDatabase(), tableInfoPanel, buttonActions, menuActions, getPanelName() +"-actions");
  }
  
  /**
   * panel name used for setting name, filter, actions, etc
   * like "database-panel"
   * @return 
   */
  abstract public String getPanelName();
  
  /**
   * @return 
   */
  abstract public String getSql();
  
  public void extraSqlParameters(ParametrizedCommand qc) throws UseDBException {
    
  }
  
  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  private void refreshTask() {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        refresh();
      }
    });
  }
  
  public void refresh() {
    String sqlText = getSql();
    if (!StringUtil.isEmpty(currentObjectName) && !StringUtil.isEmpty(sqlText)) {
      Query query = getDatabase().createQuery();
      try {
        query.setSqlText(sqlText);
        extraSqlParameters(query);
        query.open();
        tableInfoPanel.refresh(query);
      } catch (Exception ex) {
        MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
      }
      finally {
        query.close();
        requestRefresh = false;
      }
    }
    else {
      tableInfoPanel.getTable().setModel(new DefaultTableModel());
      requestRefresh = false;
    }
  }
  
  public void refresh(String catalogName, String schemaName, String objectName) {
    if (!currentSchemaName.equals(schemaName) || !currentObjectName.equals(objectName) || requestRefresh) {
      currentSchemaName = schemaName;
      currentObjectName = objectName;
      if (SwingUtil.isVisible(this)) {
        refresh();
      }
      else {
        requestRefresh = true;
      }
    }
  }
  
  @Override
  public boolean canClose() {
    return true;
  }

  public void close() throws IOException {
    closing = true;
    accesibilities = null;
  }
  
  public String getCurrentSchemaName() {
    return currentSchemaName;
  }

  public String getCurrentObjectName() {
    return currentObjectName;
  }

  public JToolBar getToolBar() {
    return toolBar;
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
        jPanel1 = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        buttonRefresh = new pl.mpak.sky.gui.swing.comp.ToolButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        buttonActions = new pl.mpak.sky.gui.swing.comp.ToolButton();
        panel = new javax.swing.JPanel();
        statusStatus = new pl.mpak.usedb.gui.swing.QueryTableStatusBar();

        cmRefresh.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/refresh16.gif")); // NOI18N
        cmRefresh.setText(stringManager.getString("cmRefresh-text")); // NOI18N
        cmRefresh.setTooltip(stringManager.getString("cmRefresh-hint")); // NOI18N
        cmRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmRefreshActionPerformed(evt);
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        buttonRefresh.setAction(cmRefresh);
        buttonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(buttonRefresh);
        toolBar.add(jSeparator1);
        toolBar.add(buttonActions);

        jPanel1.add(toolBar);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        panel.setLayout(new java.awt.BorderLayout());
        panel.add(statusStatus, java.awt.BorderLayout.SOUTH);

        add(panel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
  if (requestRefresh && !closing) {
    refreshTask();
  }
}//GEN-LAST:event_formComponentShown

private void cmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmRefreshActionPerformed
  refresh();
}//GEN-LAST:event_cmRefreshActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pl.mpak.sky.gui.swing.comp.ToolButton buttonActions;
    private pl.mpak.sky.gui.swing.comp.ToolButton buttonRefresh;
    private pl.mpak.sky.gui.swing.Action cmRefresh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu menuActions;
    private javax.swing.JPanel panel;
    private pl.mpak.usedb.gui.swing.QueryTableStatusBar statusStatus;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
  
}
