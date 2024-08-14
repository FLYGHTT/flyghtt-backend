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

create table if not exists tools (

    tool_id uuid primary key,
    name varchar(50) not null,
    description varchar(255) not null,
    is_public boolean default false,
    created_by uuid not null,

    constraint fk_tools_created_by foreign key (created_by)
        references users(user_id)
);

create table if not exists factor_columns (

    factor_column_id bigserial primary key,
    column_name varchar(50) not null,
    factor_name varchar(50) not null,
    tool uuid not null,

    unique (column_name, factor_name),

    constraint fk_factor_columns_tools foreign key (tool)
                                          references tools(tool_id)
);

create table if not exists business_tools (

    business_tool_id uuid primary key,
    business_id uuid not null,
    tool_id uuid not null,

    constraint fk_business_tools_business_id foreign key (business_id)
                                          references businesses(business_id),
    constraint fk_business_tools_tool_id foreign key (tool_id)
                                          references tools(tool_id)
);

create table if not exists factors (

    factor_id bigserial primary key,

    name varchar(50) not null,
    business_tool uuid not null,
    business_id uuid not null,

    constraint fk_factors_business_tool foreign key (business_tool)
                                         references business_tools(business_tool_id),
    constraint fk_factors_business_id foreign key (business_id)
                                   references businesses(business_id)
);

create table if not exists factor_values (

    factor_id bigserial not null,
    value varchar(255) not null,

    constraint fk_factor_values_factor_id foreign key (factor_id)
                                         references factors(factor_id)
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
