package main.Repositories;

import main.domain.Account;

public interface AccountRepository {
    Account load(String accountId);

    void update(Account account);

}
