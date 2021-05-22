package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Typ Access level manager implementation - implementacja AccessLevelManager.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccessLevelManagerImplementation extends AbstractManager implements AccessLevelManager {
    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Override
    public void revokeAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (accessLevel.getActive()) {
            accessLevel.setActive(false);
            accessLevelFacade.edit(accessLevel);
        }
    }

    @Override
    public void addAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (!accessLevel.getActive()) {
            accessLevel.setActive(true);
            accessLevelFacade.edit(accessLevel);
        }
    }

}
