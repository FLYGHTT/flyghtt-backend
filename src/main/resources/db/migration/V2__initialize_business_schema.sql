create table if not exists businesses (

    business_id uuid primary key default gen_random_uuid(),
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
