package task1.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import task1.dao.Dao;
import task1.dao.exception.DaoExceptionJson;
import task1.exceptions.UnknownAccountException;
import task1.model.Account;
import org.codehaus.jackson.map.ObjectMapper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
@Lazy
@PropertySource("classpath:fileRepository.properties")
public class JsonAccountDao implements Dao<Account> {

    private static File file;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public JsonAccountDao(@Value("${file.path}") File file) {
        JsonAccountDao.file = file;
    }

    @Override
    public void create(Account item) throws DaoExceptionJson {

        if (item == null) {
            throw new NullPointerException("Input parameter == null");
        }
        List<Account> accounts = getListAccounts();
        accounts.add(item);
        writerFileJson(accounts);
    }

    @Override
    public void update(Account item) throws UnknownAccountException, DaoExceptionJson {

        if (item == null) {
            throw new NullPointerException("Input parameter == null");
        }
        List<Account> accounts = getListAccounts();
        int searchId = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == item.getId()) {
                searchId = i;
                break;
            }
        }
        if (searchId == -1) {
            throw new UnknownAccountException("Аккаунт с указанным id не найден");
        }
        accounts.set(searchId, item);
        writerFileJson(accounts);
    }

    @Override
    public void delete(Account item) throws DaoExceptionJson, UnknownAccountException {

        if (item == null) {
            throw new NullPointerException("Input parameter == null");
        }
        List<Account> accounts = getListAccounts();
        boolean isReplacedAccount = accounts.removeIf(temp -> temp.getId() == item.getId());
        if (!isReplacedAccount) {
            throw new UnknownAccountException("Аккаунт с указанным id не найден");
        } else {
            writerFileJson(accounts);
        }
    }

    @Override
    public Account read(int id) throws UnknownAccountException, DaoExceptionJson {
        Account account = null;
        List<Account> accounts = getListAccounts();
        for (Account acc : accounts) {
            if (acc.getId() == id) {
                account = acc;
            }
        }

        if (account == null) {
            throw new UnknownAccountException("Аккаунт не найден");
        }
        return account;
    }

    @Override
    public List<Account> getListAccounts() throws DaoExceptionJson {
        List<Account> accounts;
        try {
            accounts = Arrays.asList(mapper.readValue(file, Account[].class));
        } catch (IOException e) {
            throw new DaoExceptionJson("Error getting the accounts", e);
        }

        if (accounts == null) {
            throw new DaoExceptionJson("Failed to initialize the object");
        } else {
            return accounts;
        }
    }

    private void writerFileJson(Object array) throws DaoExceptionJson {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileOutputStream, array);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new DaoExceptionJson("Failed to writing the file");
        }
    }

    @PostConstruct
    private void initialization() throws DaoExceptionJson {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            if (!file.exists()) {
                file.createNewFile();
                Account[] accounts = {
                        (new Account(1, "Иванов Иван", 500)),
                        (new Account(2, "Валентинов Валентин", 600)),
                        (new Account(3, "Константинов Константин", 700)),
                        (new Account(4, "Михайлов Михаил", 800)),
                        (new Account(5, "Гай Игоорь", 900)),
                        (new Account(6, "Носов Николай", 1000)),
                        (new Account(7, "Николаев Константин", 10100)),
                        (new Account(8, "Михайлов Алексей", 2300)),
                        (new Account(9, "Константинов Шон", 15005)),
                        (new Account(10, "Бабушкин Михаил", 0))
                };
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                mapper.writerWithDefaultPrettyPrinter().writeValue(fileOutputStream, accounts);
                fileOutputStream.close();
            }
        } catch (IOException e) {
            throw new DaoExceptionJson("Error initialization file", e);
        }
    }
}
