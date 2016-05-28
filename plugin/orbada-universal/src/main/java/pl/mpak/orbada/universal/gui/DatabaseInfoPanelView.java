/*
 * DatabaseInfoViewPanel.java
 *
 * Created on 28 pa�dziernik 2007, 11:53
 */

package pl.mpak.orbada.universal.gui;

import java.awt.Component;
import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import pl.mpak.orbada.gui.comps.table.view.BeanPropertyPanel;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.plugins.providers.DatabaseInfoProvider;
import pl.mpak.orbada.universal.OrbadaUniversalPlugin;
import pl.mpak.sky.gui.swing.TabCloseComponent;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.Titleable;

/**
 *
 * @author  akaluza
 */
public class DatabaseInfoPanelView extends javax.swing.JPanel implements Closeable {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("universal");

  private IViewAccesibilities accesibilities;
  
  /**
   * Creates new form DatabaseInfoViewPanel
   * @param accesibilities
   */
  public DatabaseInfoPanelView(IViewAccesibilities accesibilities) {
    this.accesibilities = accesibilities;
    initComponents();
    init();
  }
  
  private void init() {
    addBeanPanel(stringManager.getString("database"), getDatabase().getMetaData(), new String[] {"catalogs", "clientInfoProperties", "schemas", "tableTypes", "typeInfo"});
    addBeanPanel(stringManager.getString("driver"), getDatabase().getDriver(), null);
    try {
      addBeanPanel(stringManager.getString("connection"), getDatabase().getConnection(), null);
    } catch (SQLException ex) {
    }
    try {
      addResultSetPanel(stringManager.getString("catalogs"), getDatabase().getMetaData().getCatalogs());
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    try {
      addResultSetPanel(stringManager.getString("schemas"), getDatabase().getMetaData().getSchemas());
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    try {
      addResultSetPanel(stringManager.getString("parameters"), getDatabase().getMetaData().getClientInfoProperties());
    } catch (AbstractMethodError ex) {
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    try {
      addResultSetPanel(stringManager.getString("table-types"), getDatabase().getMetaData().getTableTypes());
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    try {
      addResultSetPanel(stringManager.getString("data-types"), getDatabase().getMetaData().getTypeInfo());
    } catch (Throwable ex) {
      ExceptionUtil.processException(ex);
    }
    
    DatabaseInfoProvider[] dip = accesibilities.getApplication().getServiceArray(DatabaseInfoProvider.class);
    if (dip != null && dip.length > 0) {
      for (int i=0; i<dip.length; i++) {
        if (dip[i].isForDatabase(getDatabase())) {
          Component[] panels = dip[i].getExtendedPanelInfo(getDatabase());
          if (panels != null && panels.length > 0) {
            for (Component panel : panels) {
              String title = stringManager.getString("no-name");
              if (panel instanceof Titleable) {
                title = ((Titleable)panel).getTitle();
              }
              addExtendedPanel(title, panel);
            }
          }
          break;
        }
      }
    }
  }
  

  public void close() throws IOException {
    int i = 0;
    while (i<tabbedInfo.getTabCount()) {
      Component c = tabbedInfo.getComponentAt(i);
      if (c instanceof Closeable) {
        try {
          ((Closeable) c).close();
        } catch (IOException ex) {
          ExceptionUtil.processException(ex);
        }
        tabbedInfo.remove(c);
      } else {
        i++;
      }
    }
  }

  private void addBeanPanel(String title, Object bean, String[] exclude) {
    BeanPropertyPanel panel = new BeanPropertyPanel(bean, exclude);
    tabbedInfo.addTab(title, panel);
    tabbedInfo.setTabComponentAt(tabbedInfo.indexOfComponent(panel), new TabCloseComponent(title));
    tabbedInfo.setSelectedComponent(panel);
  }
  
  private void addResultSetPanel(String title, ResultSet resultSet) throws Exception {
    ResultSetPanel panel = new ResultSetPanel(getDatabase(), resultSet);
    tabbedInfo.addTab(title, panel);
    tabbedInfo.setTabComponentAt(tabbedInfo.indexOfComponent(panel), new TabCloseComponent(title));
    tabbedInfo.setSelectedComponent(panel);
  }
  
  private void addExtendedPanel(String title, Component c) {
    tabbedInfo.addTab(title, c);
    tabbedInfo.setTabComponentAt(tabbedInfo.indexOfComponent(c), new TabCloseComponent(title));
    tabbedInfo.setSelectedComponent(c);
  }
  
  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabbedInfo = new javax.swing.JTabbedPane();

    setLayout(new java.awt.BorderLayout());
    add(tabbedInfo, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTabbedPane tabbedInfo;
  // End of variables declaration//GEN-END:variables

  
}