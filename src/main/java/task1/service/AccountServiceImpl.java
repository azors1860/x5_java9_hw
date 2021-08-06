package task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import task1.dao.Dao;
import task1.dao.exception.DaoException;
import task1.exceptions.NotEnoughMoneyException;
import task1.exceptions.UnknownAccountException;
import task1.model.Account;

@Component
public class AccountServiceImpl implements AccountService {

    private final Dao<Account> accountDao;

    /**
     * @param dao - Объект класса имплементирующий интерфейс DAO.
     *            Возможные объекты: 'jsonAccountDao', 'dbAccountHibernate'.
     */
    @Autowired
    public AccountServiceImpl(@Qualifier("jsonAccountDao") Dao<Account> dao) {
        accountDao = dao;
    }

    @Override
    public void withDraw(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, DaoException {

        Account account = accountDao.read(accountId);
        account.setAmount(account.getAmount() - amount);
        accountDao.update(account);
    }

    @Override
    public void getBalance(int accountId) throws UnknownAccountException, DaoException {

        Account account = accountDao.read(accountId);
        System.out.println(account.getAmount());
    }

    @Override
    public void deposit(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, DaoException {

        Account account = accountDao.read(accountId);
        account.setAmount(account.getAmount() + amount);
        accountDao.update(account);
    }

    @Override
    public void transfer(int from, int to, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, DaoException {

        Account accountFrom = accountDao.read(from);
        Account accountTo = accountDao.read(to);
        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);
        accountDao.update(accountFrom);
        accountDao.update(accountTo);
    }
}
