create table if not exists tools (

    tool_id uuid default gen_random_uuid() primary key,
    name varchar(50) not null,
    description varchar(255) default null,
    link varchar(255) default null,

    commentable boolean default true,
    is_public boolean default false,

    created_by uuid not null,

    constraint fk_tools_created_by foreign key (created_by)
                                 references users(user_id)
);

create table if not exists columns (

    column_id uuid default gen_random_uuid() primary key,
    name varchar(50) not null,
    description varchar(255) default null,

    tool_id uuid not null,

    unique (tool_id, name),

    constraint fk_columns_tool_id foreign key (tool_id)
                                   references tools(tool_id)
);

create table if not exists factors (

    factor_id uuid default gen_random_uuid() primary key,
    name varchar(50) not null,

    column_id uuid not null,

    unique (column_id, name),

    constraint fk_column_factors_column_id foreign key (column_id)
                                          references columns(column_id)
);

create table if not exists business_tools (

    business_tool_id uuid primary key,
    value varchar(255) not null,

    factor_id uuid not null,
    business_id uuid not null,

    unique (factor_id, business_id),

    constraint fk_business_tools_factor_id foreign key (factor_id)
                                          references factors(factor_id),
    constraint fk_business_tools_business_id foreign key (business_id)
                                          references businesses(business_id)
);
