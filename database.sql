```sql
/* =========================================
   BANKING MANAGEMENT SYSTEM DATABASE
   Oracle XE SQL File
========================================= */


/* EMPLOYEE TABLE */

create table employee
(
    name varchar2(20) NOT NULL,
    password varchar2(20) NOT NULL,
    email varchar2(50) NOT NULL,
    contact number NOT NULL
);



/* ACCOUNT DATA TABLE */

create table accountdata
(
    name varchar2(20) NOT NULL,
    accountno number NOT NULL,
    adhar number NOT NULL,
    mobileno number NOT NULL,
    email varchar2(50) NOT NULL,
    fathername varchar2(20) NOT NULL,
    accounttype varchar2(20) NOT NULL,
    balance number NOT NULL,
    gender varchar2(20) NOT NULL,
    updatedata varchar2(20)
);



/* CREDIT TABLE */

create table credit
(
    name varchar2(20) NOT NULL,
    amount number NOT NULL,
    datecrdit date NOT NULL
);



/* DEBIT TABLE */

create table debit
(
    name varchar2(20) NOT NULL,
    amount number NOT NULL,
    datedebit date NOT NULL
);



/* TRANSACTION TABLE */

create table transaction
(
    name varchar2(20) NOT NULL,
    sender number NOT NULL,
    reciever number NOT NULL,
    amount number NOT NULL,
    datetransaction date NOT NULL
);



/* SAMPLE DATA */

insert into employee
values
(
    'admin',
    'admin123',
    'admin@gmail.com',
    9876543210
);


insert into accountdata
values
(
    'Mukul',
    45630863030,
    8888,
    7410258963,
    'mukul@gmail.com',
    'Ramesh',
    'Current',
    5100,
    'Male',
    '13-MAY-2026'
);


commit;
```
