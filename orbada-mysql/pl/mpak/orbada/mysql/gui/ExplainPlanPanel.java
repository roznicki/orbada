/*
 * ExplainPlanPanel.java
 *
 * Created on 30 grudzie� 2007, 16:41
 */

package pl.mpak.orbada.mysql.gui;

import java.io.Closeable;
import java.io.IOException;

import orbada.gui.comps.table.DataTable;
import pl.mpak.orbada.mysql.OrbadaMySQLPlugin;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.usedb.core.Command;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.Query;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class ExplainPlanPanel extends javax.swing.JPanel implements Closeable {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager(OrbadaMySQLPlugin.class);

  private Database database;
  
  /** Creates new form ExplainPlanPanel */
  public ExplainPlanPanel(Database database) {
    this.database = database;
    initComponents();
    init();
  }
  
  private void init() {
  }
  
  public void updatePlan(String sqlText) {
    data.getQuery().setDatabase(database);
    try {
      Query query = database.createQuery();
      query.setSqlText(sqlText);
      Command command = database.createCommand();
      command.execute("EXPLAIN \n" + query.getPreparedSqlText());
      if (command.getStatement() != null) {
        data.getQuery().close();
        data.getQuery().setResultSet(command.getStatement().getResultSet());
      }
    } catch (Exception ex) {
      MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
    }
  }

  public void close() throws IOException {
    data.getQuery().close();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    statusBar = new pl.mpak.usedb.gui.swing.QueryTableStatusBar();
    jScrollPane1 = new javax.swing.JScrollPane();
    data = new DataTable();

    setLayout(new java.awt.BorderLayout());

    statusBar.setTable(data);
    add(statusBar, java.awt.BorderLayout.SOUTH);

    jScrollPane1.setViewportView(data);

    add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private DataTable data;
  private javax.swing.JScrollPane jScrollPane1;
  private pl.mpak.usedb.gui.swing.QueryTableStatusBar statusBar;
  // End of variables declaration//GEN-END:variables
  
}
