package org.yage.transaction;

import org.yage.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

public interface TransactionFactory {
    Transaction newTransaction(Connection conn);

    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
}
