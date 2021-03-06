package pl.mpak.orbada.oracle.gui.types;

import java.awt.Component;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import pl.mpak.orbada.gui.OrbadaTabbedPane;
import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.sky.gui.swing.vtab.VTextIcon;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.Titleable;

/**
 *
 * @author akaluza
 */
public class TypeTabbedPane extends OrbadaTabbedPane {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager("oracle");

  public TypeTabbedPane(IViewAccesibilities accesibilities) {
    super("TYPE",
      new Component[] {
        new TypePartTabbedPane(accesibilities, stringManager.getString("TypeTabbedPane-general")),
        new TypePartTabbedPane(accesibilities, "TYPE", stringManager.getString("TypeTabbedPane-head")),
        new TypePartTabbedPane(accesibilities, "TYPE BODY", stringManager.getString("TypeTabbedPane-body"))
    });
    setTabPlacement(JTabbedPane.LEFT);
  }

  @Override
  public void addInfoPanel(Component panel) {
    if (UIManager.getLookAndFeel().getName() != null && UIManager.getLookAndFeel().getName().startsWith("Substance")) {
      addTab(((Titleable)panel).getTitle(), null, panel);
    }
    else {
      String title = ((Titleable)panel).getTitle();
      VTextIcon titleIcon = new VTextIcon(panel, title, VTextIcon.ROTATE_LEFT);
      addTab(null, titleIcon, panel);
    }
  }

}
