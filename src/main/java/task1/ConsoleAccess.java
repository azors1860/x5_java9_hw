package task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task1.dao.exception.DaoException;
import task1.exceptions.NotEnoughMoneyException;
import task1.exceptions.UnknownAccountException;
import task1.service.AccountService;

import java.util.Scanner;

@Component
public class ConsoleAccess {

    private final AccountService accountService;

    @Autowired
    public ConsoleAccess(AccountService accountService) {
        this.accountService = accountService;
    }

    public void consoleAppUse() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String str = scanner.nextLine();
                if (str.equals("exit")) {
                    break;
                } else if (str.matches("balance [0-9]+")) {
                    String stringId = str.split(" ")[1];
                    int id = Integer.parseInt(stringId);
                    accountService.getBalance(id);
                } else if (str.matches("withdraw [0-9]+ [0-9]+")) {
                    String[] array = str.split(" ");
                    int id = Integer.parseInt(array[1]);
                    int sum = Integer.parseInt(array[2]);
                    accountService.withDraw(id, sum);
                } else if (str.matches("deposite [0-9]+ [0-9]+")) {
                    String[] array = str.split(" ");
                    int id = Integer.parseInt(array[1]);
                    int sum = Integer.parseInt(array[2]);
                    accountService.deposit(id, sum);
                } else if (str.matches("transfer [0-9]+ [0-9]+ [0-9]+")) {
                    String[] array = str.split(" ");
                    int id1 = Integer.parseInt(array[1]);
                    int id2 = Integer.parseInt(array[2]);
                    int sum = Integer.parseInt(array[3]);
                    accountService.transfer(id1, id2, sum);
                } else {
                    throw new UnsupportedOperationException("Команда не найдена");
                }
            }
        } catch (UnknownAccountException | NotEnoughMoneyException | DaoException e) {
            e.printStackTrace();
        }
    }
}
