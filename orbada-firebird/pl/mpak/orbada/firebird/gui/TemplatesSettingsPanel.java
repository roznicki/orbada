package pl.mpak.orbada.firebird.gui;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import orbada.db.InternalDatabase;
import pl.mpak.orbada.firebird.OrbadaFirebirdPlugin;
import pl.mpak.orbada.firebird.services.FirebirdTemplatesSettingsProvider;
import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.plugins.ISettingsComponent;
import pl.mpak.usedb.core.Query;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class TemplatesSettingsPanel extends javax.swing.JPanel implements ISettingsComponent {
  
  private StringManager stringManager = StringManagerFactory.getStringManager(OrbadaFirebirdPlugin.class);

  private IApplication application;
  private ISettings settings;

  /** Creates new form DatabaseSettingsPanel */
  public TemplatesSettingsPanel(IApplication application) {
    this.application = application;
    initComponents();
    init();
  }
  
  private void init() {
    Query query = InternalDatabase.get().createQuery();
    try {
      ArrayList<String> teplateList = new ArrayList<String>();
      query.setSqlText(
        "select distinct tpl_name\n" +
        "  from templates\n" +
        " where (tpl_usr_id = :usr_id or tpl_usr_id is null)\n" +
        " order by tpl_name");
      query.paramByName("usr_id").setString(application.getUserId());
      query.open();
      while (!query.eof()) {
        teplateList.add(query.fieldByName("tpl_name").getString());
        query.next();
      }
      comboProcedure.setModel(new DefaultComboBoxModel(teplateList.toArray()));
      comboTrigger.setModel(new DefaultComboBoxModel(teplateList.toArray()));
    }
    catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
    finally {
      query.close();
    }

    settings = application.getSettings(FirebirdTemplatesSettingsProvider.settingsName);
    comboProcedure.setSelectedItem(settings.getValue(FirebirdTemplatesSettingsProvider.setProcedure, "firebird-procedure"));
    comboTrigger.setSelectedItem(settings.getValue(FirebirdTemplatesSettingsProvider.setTrigger, "firebird-trigger"));
    restoreSettings();
  }
  
  public void restoreSettings() {
  }

  public void applySettings() {
    settings.setValue(FirebirdTemplatesSettingsProvider.setProcedure, comboProcedure.getSelectedItem().toString());
    settings.setValue(FirebirdTemplatesSettingsProvider.setTrigger, comboTrigger.getSelectedItem().toString());
    settings.store();
  }

  public void cancelSettings() {
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    comboTrigger = new javax.swing.JComboBox();
    comboProcedure = new javax.swing.JComboBox();
    jLabel3 = new javax.swing.JLabel();

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText(stringManager.getString("trigger-dd")); // NOI18N

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel3.setText(stringManager.getString("procedure-dd")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboTrigger, 0, 285, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboProcedure, 0, 285, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(comboTrigger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(comboProcedure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(83, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox comboProcedure;
  private javax.swing.JComboBox comboTrigger;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  // End of variables declaration//GEN-END:variables
  
}
