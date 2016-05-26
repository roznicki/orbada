/*
 * ResolversDialog.java
 *
 * Created on 28 grudzie� 2007, 19:08
 */

package pl.mpak.orbada.programmers.gui;

import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

import orbada.gui.comps.table.Table;
import pl.mpak.orbada.programmers.OrbadaProgrammersPlugin;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.patt.ResolvableModel;
import pl.mpak.util.patt.Resolvers;

/**
 *
 * @author  akaluza
 */
public class ResolversDialog extends javax.swing.JDialog {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager(OrbadaProgrammersPlugin.class);

  /** Creates new form ResolversDialog */
  public ResolversDialog() {
    super(SwingUtil.getRootFrame());
    initComponents();
    init();
  }
  
  public static void showDialog() {
    ResolversDialog dialog = new ResolversDialog();
    dialog.setVisible(true);
  }

  private void init() {
    tableResolvers.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {},
      new String [] {stringManager.getString("key"), stringManager.getString("resolve")}
    ) {
      Class[] types = new Class [] {java.lang.String.class, java.lang.String.class};
      @Override
      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    tableResolvers.getColumnModel().getColumn(0).setPreferredWidth(100);
    
    Iterator<String> i = Resolvers.getInstance().keys();
    while (i.hasNext()) {
      String key = i.next();
      ResolvableModel rm = Resolvers.getInstance().get(key);
      ((DefaultTableModel)tableResolvers.getModel()).addRow(new Object[] {"$(" +rm.getModel() +")", rm.getResolve()});
    }
    tableResolvers.setFont(tableResolvers.getFont().deriveFont(12f));
    
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmClose.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmClose);
    SwingUtil.centerWithinScreen(this);
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmClose = new pl.mpak.sky.gui.swing.Action();
    buttonCancel = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableResolvers = new Table();

    cmClose.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmClose.setText(stringManager.getString("cmClose-text")); // NOI18N
    cmClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCloseActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("ResolversDialog-title")); // NOI18N
    setModal(true);

    buttonCancel.setAction(cmClose);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(75, 23));

    jScrollPane1.setViewportView(tableResolvers);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void cmCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCloseActionPerformed
    dispose();
}//GEN-LAST:event_cmCloseActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private pl.mpak.sky.gui.swing.Action cmClose;
  private javax.swing.JScrollPane jScrollPane1;
  private Table tableResolvers;
  // End of variables declaration//GEN-END:variables
  
}