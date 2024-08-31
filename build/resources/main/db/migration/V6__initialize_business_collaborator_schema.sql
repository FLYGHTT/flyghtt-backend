drop table business_employees;

create table if not exists business_collaborator_requests (

    business_collaborator_request_id bigserial primary key,
    business_id uuid not null,
    collaborator_id uuid not null,
    role varchar(50) not null,
    approval_status varchar(50) not null,

    unique (business_id, collaborator_id),

    constraint fk_business_collaborator_requests_business_id foreign key (business_id)
                                                          references businesses(business_id),
    constraint fk_business_collaborator_requests_collaborator_id foreign key (collaborator_id)
                                                          references users(user_id)
);

create table if not exists business_collaborators (

    business_collaborator_id uuid primary key,
    role varchar(50) not null,

    business_id uuid not null,
    user_id uuid not null,

    unique (business_id, user_id),

    constraint fk_business_collaborators_business_id foreign key (business_id)
        references businesses(business_id),
    constraint fk_business_collaborators_user_id foreign key (user_id)
        references users(user_id)
);
