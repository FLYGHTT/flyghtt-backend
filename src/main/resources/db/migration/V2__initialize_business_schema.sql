create table if not exists businesses (

    business_id uuid primary key,
    name varchar(50) not null,
    description varchar(255) not null,

    created_at timestamp default now(),
    created_by uuid not null,

    unique (created_by, name),

    constraint fk_business_created_by foreign key (created_by)
                                      references users(user_id)
);

create table if not exists business_employees (

    business_id uuid not null,
    employee_id uuid not null,

    unique (business_id, employee_id),

    constraint fk_business_employees_business_id foreign key (business_id)
        references businesses(business_id),
    constraint fk_business_employees_employee_id foreign key (employee_id)
        references users(user_id)
);

create table if not exists tools (

    tool_id uuid primary key,
    name varchar(50) not null,
    description varchar(255) default null,
    link varchar(255) default null,
    is_public boolean not null default false,
    commentable boolean not null default true,
    created_by uuid not null,

    constraint fk_tools_created_by foreign key (created_by)
                                 references users(user_id)
);

create table if not exists tool_columns (

    column_id bigserial primary key,
    name varchar(50) not null,
    description varchar(255) default null,
    tool_id uuid not null,

    constraint tool_columns_tool_id foreign key (tool_id)
                                        references tools(tool_id)
);

create table if not exists column_factors (

    column_factor_id bigint primary key,
    factor_name varchar(50) not null,
    factor_description varchar(255) default null,
    column_id bigint not null,

    constraint column_factors_column_id foreign key (column_id)
                                          references tool_columns(column_id)
);

create table if not exists factor_values (

    factor_value_id bigint primary key,
    factor_value varchar(255) not null,
    business_id uuid not null,
    column_factor_id bigint not null,

    constraint fk_factor_values_factor_value foreign key (column_factor_id)
                                         references column_factors(column_factor_id),
    constraint fk_factor_values_business_id foreign key (business_id)
                                         references businesses(business_id)
);
