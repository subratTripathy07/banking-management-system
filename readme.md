mysql> use banking_system;
Database changed
mysql> show tables;
+--------------------------+
| Tables_in_banking_system |
+--------------------------+
| accounts                 |
| user                     |
+--------------------------+
2 rows in set (0.061 sec)

mysql> select * from accounts;
+----------------+-----------+--------------------------+-------------+--------------+
| account_number | full_name | email                    | balance     | security_pin |
+----------------+-----------+--------------------------+-------------+--------------+
|     7894409727 | subrat    | subrattripathy@gmail.com | 10000000.00 | 7894         |
|    22222222222 | Pramiit   | pramit@gmail.com         |    10000.00 | 1234         |
+----------------+-----------+--------------------------+-------------+--------------+
2 rows in set (0.015 sec)

mysql> select * from user;
+-----------+---------------------+----------+
| full_name | email               | password |
+-----------+---------------------+----------+
| subrat    | anurag667@gmail.com | 9999     |
| pramit    | pramiit@gmail.com   | 1234     |
+-----------+---------------------+----------+
2 rows in set (0.018 sec)
