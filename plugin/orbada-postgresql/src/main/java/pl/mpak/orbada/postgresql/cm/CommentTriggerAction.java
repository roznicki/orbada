/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mpak.orbada.postgresql.cm;

import pl.mpak.orbada.gui.IRootTabObjectInfo;
import pl.mpak.orbada.gui.comps.table.ViewTable;
import pl.mpak.orbada.postgresql.OrbadaPostgreSQLPlugin;
import pl.mpak.orbada.postgresql.gui.wizards.CommentUniversalWizard;
import pl.mpak.orbada.universal.cm.UniversalViewTableAction;
import pl.mpak.orbada.universal.gui.wizards.SqlCodeWizardDialog;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author akaluza
 */
public class CommentTriggerAction extends UniversalViewTableAction {

  private final StringManager stringManager = StringManagerFactory.getStringManager("postgresql");

  public CommentTriggerAction() {
    super();
    setText(stringManager.getString("comment-edit"));
    setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/comment_edit.gif"));
    setActionCommandKey("CommentTriggerAction");
  }

  @Override
  public boolean isToolButton() {
    return false;
  }

  @Override
  protected boolean doAction(ViewTable vt, IRootTabObjectInfo ip) throws Exception {
    return SqlCodeWizardDialog.show(
      new CommentUniversalWizard(
        vt.getQuery().getDatabase(),
        "trigger-dd", "TRIGGER", 
        vt.getQuery().fieldByName("full_object_name").getString(),
        vt.getQuery().fieldByName("description").getString()
      ), true) != null;
  }

}
