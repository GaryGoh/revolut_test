package com.resource;

import com.MoneyTransferConstants;
import com.codahale.metrics.annotation.Timed;
import com.domain.Account;
import com.domain.Result;
import com.domain.TransferRequest;
import com.persistence.AccountDAO;
import com.workflow.TransferMoneyBetweenAccounts;
import org.skife.jdbi.v2.DBI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/account")
@Timed
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {
    private final DBI dbi;

    public TransferResource(DBI dbi) {
        this.dbi = dbi;
    }

    /**
     * The list accounts API is provided for test account details at REST client.
     */
    @GET
    @Path("/all")
    public List<Account> getAccounts() {
        return dbi.inTransaction((handle, transactionStatus) -> {
            return handle.attach(AccountDAO.class).findAll();
        });
    }

    /**
     * The new account API is provided for test account details at REST client.
     */
    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public String transferMoney(@Valid @NotNull Account account) {
        try {
            dbi.useTransaction((handle, transactionStatus) -> {
                handle.attach(AccountDAO.class).insertAccount(account);
            });
            return String.valueOf(true);
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * The transfer service that provides for test purpose.
     */
    @POST
    @Path("/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    public Result transferMoney(@Valid @NotNull TransferRequest request) {
        if (request.getFromAccount() == null || request.getFromAccount() == null) {
            new Result(false, "010", MoneyTransferConstants.MISSING_ACCOUNT);
        }
        return TransferMoneyBetweenAccounts.getInstance(dbi).transferMoney(request);
    }
}
