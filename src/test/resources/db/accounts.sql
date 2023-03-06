insert into public.account_list (account_id, account_name, account_number, account_type, available_balance, balanced_time, currency, customer_id)
 values (100,'SGSaving726','585309209', 'SAVINGS',84372.51,'2022-04-23 05:12:56.719461+00:00','SGD',200);
insert into public.account_list (account_id, account_name, account_number, account_type, available_balance, balanced_time, currency, customer_id)
 values (101,'SGSaving826','200309209', 'CURRENT',95372.51,'2022-04-22 05:12:56.719461+00:00','SGD',200);

insert into public.account_transaction (transaction_id, account_id,credit_amount, debit_amount, value_date, transaction_type, transaction_narrative)
 values (300,100,9540.20,null,'2022-04-23 05:12:56.719461+00:00','DEBIT', null);
insert into public.account_transaction (transaction_id, account_id,credit_amount, debit_amount, value_date, transaction_type, transaction_narrative)
 values (301,100,5640.20,null,'2022-04-25 05:12:56.719461+00:00','DEBIT', null);

insert into public.account_list (account_id, account_name, account_number, account_type, available_balance, balanced_time, currency, customer_id)
 values (102,'AUCurrent626','385309209', 'CURRENT',84372.51,'2022-04-23 05:12:56.719461+00:00','AUD',300);
insert into public.account_transaction (transaction_id, account_id,credit_amount, debit_amount, value_date, transaction_type, transaction_narrative)
 values (302,102,null,3000.29,'2022-04-25 05:12:56.719461+00:00','CREDIT', null);
