package pl.mpak.orbada.firebird.gui.views;

import java.io.IOException;

import orbada.gui.comps.OrbadaSyntaxTextArea;
import pl.mpak.orbada.firebird.OrbadaFirebirdPlugin;
import pl.mpak.orbada.firebird.util.SourceCreator;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import orbada.gui.ITabObjectInfo;
import orbada.gui.cm.ComponentActionsAction;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class ViewSourcePanel extends javax.swing.JPanel implements ITabObjectInfo {
  
  private StringManager stringManager = StringManagerFactory.getStringManager(OrbadaFirebirdPlugin.class);

  private IViewAccesibilities accesibilities;
  private String currentObjectName = "";
  private boolean requestRefresh = false;
  private boolean closing = false;
  
  /** Creates new form ViewSourcePanel
   * @param accesibilities
   */
  public ViewSourcePanel(IViewAccesibilities accesibilities) {
    this.accesibilities = accesibilities;
    initComponents();
    init();
  }
  
  private void setEditorText(final String objectName, final String text) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        syntaxSource.setDatabaseObject(null, "VIEW", objectName, text);
        syntaxSource.getEditorArea().setCaretPosition(0);
      }
    });
  }
  
  private void init() {
    syntaxSource.getStatusBar().addPanel("ddl-status").setText(" ");
    syntaxSource.setDatabase(getDatabase());
    SwingUtil.addAction(syntaxSource.getEditorArea(), cmStore);
    new ComponentActionsAction(getDatabase(), syntaxSource.getEditorArea(), buttonActions, menuActions, "firebird-view-source-actions");
  }
  
  public String getObjectName() {
    return syntaxSource.getObjectName();
  }

  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  public String getTitle() {
    return stringManager.getString("ViewSourcePanel-title");
  }
  
  public void gotoPoint(int line, int column) {
    int offset = syntaxSource.getEditorArea().getLineStartOffset(line -1) +column -1;
    syntaxSource.getEditorArea().setCaretPosition(offset);
  }
  
  private void refreshTask() {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        refresh();
      }
    });
  }
  
  public void refresh() {
    requestRefresh = false;
    new SourceCreator(getDatabase(), syntaxSource).getSource(null, "VIEW", currentObjectName);
    syntaxSource.getStatusBar().getPanel("ddl-status").setText(" " +currentObjectName);
  }
  
  public void refresh(String catalogName, String schemaName, String objectName) {
    if (!currentObjectName.equals(objectName) || requestRefresh) {
      currentObjectName = objectName;
      if (isVisible()) {
        refresh();
      } else {
        requestRefresh = true;
        setEditorText(null, "");
      }
    }
  }
  
  @Override
  public boolean canClose() {
    return syntaxSource.canClose();
  }

  public void close() throws IOException {
    closing = true;
    syntaxSource.setDatabase(null);
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
    cmStore = new pl.mpak.sky.gui.swing.Action();
    jPanel1 = new javax.swing.JPanel();
    toolBarContent = new javax.swing.JToolBar();
    buttonRefresh = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    buttonStore = new pl.mpak.sky.gui.swing.comp.ToolButton();
    buttonActions = new pl.mpak.sky.gui.swing.comp.ToolButton();
    syntaxSource = new OrbadaSyntaxTextArea();

    cmRefresh.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/pl/mpak/res/icons/refresh16.gif")); // NOI18N
    cmRefresh.setText(stringManager.getString("cmRefresh-text")); // NOI18N
    cmRefresh.setTooltip(stringManager.getString("ViewSourcePanel-cmRefresh-hint")); // NOI18N
    cmRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmRefreshActionPerformed(evt);
      }
    });

    cmStore.setActionCommandKey("cmStore");
    cmStore.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
    cmStore.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/pl/mpak/res/icons/store_db.gif")); // NOI18N
    cmStore.setText(stringManager.getString("ViewSourcePanel-cmStore-text")); // NOI18N
    cmStore.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmStoreActionPerformed(evt);
      }
    });

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        formComponentShown(evt);
      }
    });
    setLayout(new java.awt.BorderLayout());

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

    toolBarContent.setFloatable(false);
    toolBarContent.setRollover(true);

    buttonRefresh.setAction(cmRefresh);
    buttonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarContent.add(buttonRefresh);
    toolBarContent.add(jSeparator1);

    buttonStore.setAction(cmStore);
    buttonStore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonStore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarContent.add(buttonStore);
    toolBarContent.add(buttonActions);

    jPanel1.add(toolBarContent);

    add(jPanel1, java.awt.BorderLayout.NORTH);
    add(syntaxSource, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
  
private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
  if (requestRefresh && !closing) {
    refreshTask();
  }
}//GEN-LAST:event_formComponentShown

private void cmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmRefreshActionPerformed
  refresh();
}//GEN-LAST:event_cmRefreshActionPerformed

private void cmStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmStoreActionPerformed
  syntaxSource.getStatusBar().getPanel("ddl-status").setText(" " +String.format(stringManager.getString("ViewSourcePanel-storing-info"), new Object[] {currentObjectName}));
  try {
    syntaxSource.storeScript();
    syntaxSource.getStatusBar().getPanel("ddl-status").setText(" " +String.format(stringManager.getString("ViewSourcePanel-stored-info"), new Object[] {currentObjectName}));
  }
  catch (Exception ex) {
    syntaxSource.getStatusBar().getPanel("ddl-status").setText(" " +String.format(stringManager.getString("ViewSourcePanel-error-info"), new Object[] {currentObjectName}));
    MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
  }
}//GEN-LAST:event_cmStoreActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonActions;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonRefresh;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonStore;
  private pl.mpak.sky.gui.swing.Action cmRefresh;
  private pl.mpak.sky.gui.swing.Action cmStore;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JToolBar.Separator jSeparator1;
  private javax.swing.JPopupMenu menuActions;
  private OrbadaSyntaxTextArea syntaxSource;
  private javax.swing.JToolBar toolBarContent;
  // End of variables declaration//GEN-END:variables
  
}
