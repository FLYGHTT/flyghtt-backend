create schema if not exists "flyghtt_db";

create table if not exists users (

    user_id uuid primary key default gen_random_uuid(),
    email varchar(50) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    password varchar(100) not null,
    email_verified boolean default false not null,
    enabled boolean default true not null,
    role varchar(50) not null,
    created_at timestamp default now()
);

create table if not exists user_followers (

    user_id uuid not null,
    follower_id uuid not null,

    constraint fk_user_followers_user_id foreign key (user_id)
                                          references users(user_id),
    constraint fk_user_followers_follower_id foreign key (follower_id)
                                          references users(user_id)
);

create table if not exists user_otps (

    id serial primary key,
    otp int not null,
    expiry_date timestamp not null,
    user_id uuid not null unique,

    constraint fk_user_otp_user_id foreign key (user_id)
                                    references users(user_id)
);
