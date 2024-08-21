create table if not exists liked_tools (

    user_id uuid not null,
    tool_id uuid not null,

    constraint fk_liked_tools_user_id foreign key (user_id)
                                       references users(user_id),
    constraint fk_liked_tools_tool_id foreign key (tool_id)
                                       references tools(tool_id)
);

create table if not exists favourite_tools (

   user_id uuid not null,
   tool_id uuid not null,

    constraint fk_liked_tools_user_id foreign key (user_id)
        references users(user_id),
    constraint fk_liked_tools_tool_id foreign key (tool_id)
       references tools(tool_id)
);
