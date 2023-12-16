create table booking
(
    id           uuid                        not null
        primary key,
    booked_by    varchar(255)                not null,
    booking_time timestamp(6) with time zone not null
);

alter table booking owner to postgres;

create table phone
(
    id   uuid         not null
        primary key,
    name varchar(255) not null
);

alter table phone owner to postgres;

create table phone_booking
(
    booking_id uuid
        constraint fk_booking
            references booking,
    phone_id   uuid not null
        primary key
        constraint fk_phone
            references phone
);

alter table phone_booking owner to postgres;

