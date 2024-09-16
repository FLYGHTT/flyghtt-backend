create table if not exists tools (

    tool_id uuid default gen_random_uuid() primary key,
    name varchar(50) not null,
    description varchar(255) default null,
    link varchar(255) default null,

    commentable boolean default true,
    is_public boolean default false,
    columns varchar not null,

    created_by uuid not null,

    constraint fk_tools_created_by foreign key (created_by)
                                 references users(user_id)
);

create table if not exists business_tools (

    business_tool_id uuid primary key,

    tool_id uuid not null,
    business_id uuid not null,
    name varchar(50) not null,
    created_at timestamp default now(),
    content varchar not null,

    unique (tool_id, business_id),

    constraint fk_business_tools_tool_id foreign key (tool_id)
                                          references tools(tool_id),
    constraint fk_business_tools_business_id foreign key (business_id)
                                          references businesses(business_id)
);
