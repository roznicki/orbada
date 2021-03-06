package pl.mpak.orbada.oracle.gui.settings;

import javax.swing.SpinnerNumberModel;
import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.orbada.oracle.services.OracleDbmsOutputSettingsProvider;
import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.plugins.ISettingsComponent;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class DbmsOutputSettingsPanel extends javax.swing.JPanel implements ISettingsComponent {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("oracle");

  private IApplication application;
  private ISettings settings;

  /** Creates new form DatabaseSettingsPanel */
  public DbmsOutputSettingsPanel(IApplication application) {
    this.application = application;
    initComponents();
    init();
  }
  
  private void init() {
    settings = application.getSettings(OracleDbmsOutputSettingsProvider.settingsName);
    spinBufferSize.setModel(new SpinnerNumberModel(1000, 1, 1000000, 100));
    spinRefreshInterval.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
    restoreSettings();
  }
  
  public void restoreSettings() {
    spinBufferSize.setValue(settings.getValue(OracleDbmsOutputSettingsProvider.setBufferSize, 1000L).intValue());
    spinRefreshInterval.setValue(settings.getValue(OracleDbmsOutputSettingsProvider.setRefreshInterval, 5L).intValue());
    checkOnStartupViewEnable.setSelected(settings.getValue(OracleDbmsOutputSettingsProvider.setOnStartupViewEnable, true));
  }

  public void applySettings() {
    settings.setValue(OracleDbmsOutputSettingsProvider.setBufferSize, (long)(Integer)spinBufferSize.getValue());
    settings.setValue(OracleDbmsOutputSettingsProvider.setRefreshInterval, (long)(Integer)spinRefreshInterval.getValue());
    settings.setValue(OracleDbmsOutputSettingsProvider.setOnStartupViewEnable, checkOnStartupViewEnable.isSelected());
    settings.store();
  }

  public void cancelSettings() {
    restoreSettings();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    spinBufferSize = new javax.swing.JSpinner();
    jLabel2 = new javax.swing.JLabel();
    checkOnStartupViewEnable = new javax.swing.JCheckBox();
    jLabel3 = new javax.swing.JLabel();
    spinRefreshInterval = new javax.swing.JSpinner();
    jLabel4 = new javax.swing.JLabel();

    jLabel1.setText(stringManager.getString("DbmsOutputSettingsPanel-buffer-size-dd")); // NOI18N

    jLabel2.setText(stringManager.getString("in-kibi")); // NOI18N

    checkOnStartupViewEnable.setText(stringManager.getString("DbmsOutputSettingsPanel-checkOnStartupViewEnable-text")); // NOI18N

    jLabel3.setText(stringManager.getString("DbmsOutputSettingsPanel-refresh-at-dd")); // NOI18N

    jLabel4.setText(stringManager.getString("seconds")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(checkOnStartupViewEnable)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(spinBufferSize, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel2))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(spinRefreshInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel4)))
        .addContainerGap(221, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(checkOnStartupViewEnable)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jLabel2)
          .addComponent(spinBufferSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(jLabel4)
          .addComponent(spinRefreshInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(288, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkOnStartupViewEnable;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JSpinner spinBufferSize;
  private javax.swing.JSpinner spinRefreshInterval;
  // End of variables declaration//GEN-END:variables
  
}
