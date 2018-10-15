package com.resource;

import com.codahale.metrics.annotation.Timed;
import com.domain.Account;
import com.domain.Result;
import com.domain.TransferRequest;
import com.enhancement.AccountEnhancement;
import com.persistence.AccountDB;
import com.workflow.TransferMoneyBetweenAccounts;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/account")
public class TransferResource {
    public TransferResource() {
    }

    @GET
    @Timed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccounts() {
        return AccountDB.getAll();
    }


    @POST
    @Timed
    @Path("/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Result transferMoney(@Valid @NotNull TransferRequest request) {
        if (request.getFromAccountId() == null || request.getFromAccountId() == null) {
            new Result(false, "001");
        }
        Account fromAccount = AccountEnhancement.getInstance().find(request.getFromAccountId());
        Account toAccount = AccountEnhancement.getInstance().find(request.getToAccountId());
        return TransferMoneyBetweenAccounts.getInstance().transferMoney(fromAccount, toAccount, request.getAmount());
    }
}
