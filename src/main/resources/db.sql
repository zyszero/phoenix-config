create table if not exists `configs`
(
    `app`  varchar(64)  not null,
    `env`  varchar(64)  not null,
    `ns`   varchar(64)  not null,
    `pkey` varchar(64)  not null,
    `pval` varchar(128)  null
);

insert into configs(app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'zys.a', 'dev100');

insert into configs(app, env, ns, pkey, pval)
values ('app2', 'dev', 'public', 'zys.b', 'http://localhost:9192');

insert into configs(app, env, ns, pkey, pval)
values ('app3', 'dev', 'public', 'zys.c', 'cc100');