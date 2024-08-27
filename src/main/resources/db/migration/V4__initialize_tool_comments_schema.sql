create table if not exists tool_comments (

    tool_comment_id uuid primary key default gen_random_uuid(),
    comment varchar(255) not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),

    tool_id uuid not null,
    user_id uuid not null,

    constraint fk_tool_comments_tool_id foreign key (tool_id)
                                         references tools(tool_id),
    constraint fk_tool_comments_user_id foreign key (user_id)
                                         references users(user_id)
);
