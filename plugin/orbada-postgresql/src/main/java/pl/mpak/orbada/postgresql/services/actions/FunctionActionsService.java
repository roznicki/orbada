/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mpak.orbada.postgresql.services.actions;

import java.util.ArrayList;
import pl.mpak.orbada.plugins.ComponentAction;
import pl.mpak.orbada.plugins.providers.ComponentActionsProvider;
import pl.mpak.orbada.postgresql.OrbadaPostgreSQLPlugin;
import pl.mpak.orbada.postgresql.cm.CommentFunctionAction;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author akaluza
 */
public class FunctionActionsService extends ComponentActionsProvider {

  private final StringManager stringManager = StringManagerFactory.getStringManager("postgresql");

  @Override
  public ComponentAction[] getForComponent(Database database, String actionType) {
    if (database == null || !OrbadaPostgreSQLPlugin.driverType.equals(database.getDriverType())) {
      return null;
    }
    if (!"postgresql-functions-actions".equals(actionType) &&
        !"postgresql-trigger-functions-actions".equals(actionType)) {
      return null;
    }

    ArrayList<ComponentAction> actions = new ArrayList<ComponentAction>();

    //actions.add(new TableFreezeAction());
    actions.add(new CommentFunctionAction());

    return actions.toArray(new ComponentAction[actions.size()]);
  }

  @Override
  public String getDescription() {
    return stringManager.getString("FunctionActionsService-description");
  }

  @Override
  public String getGroupName() {
    return OrbadaPostgreSQLPlugin.driverType;
  }

}
