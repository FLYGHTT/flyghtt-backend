create table if not exists business_logos (

    business_logo_id uuid primary key,
    type varchar(50) not null,
    image_data bytea not null,

    business_id uuid not null unique,

    constraint fk_business_logos_business_id foreign key (business_id)
                                          references businesses(business_id)
);
