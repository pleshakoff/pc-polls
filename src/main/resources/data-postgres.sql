insert into public.poll (id, creation, description, expiration, id_group, id_user, name, poll_state, poll_type)
values (1, '2020-03-04 14:38:37.401000', 'Выбираем место проведения выпускного', '2020-03-04 11:37:39.356000', 21, 1, 'Выпускной', 1, 0);

insert into public.variant (id, description, num, id_poll)
values (21, 'На космическом корабле', 2, 1);
insert into public.variant (id, description, num, id_poll)
values (22, 'В кратере вулкана Тунгнафедльсйёкюдль', 1, 1);
insert into public.variant (id, description, num, id_poll)
values (23, 'В спортзале', 3, 1);


insert into public.voter (id, family_name, first_name, id_student, middle_name, id_poll, id_variant)
values (33, 'Грейнджер', 'Гермиона', 33, null, 1, null);
insert into public.voter (id, family_name, first_name, id_student, middle_name, id_poll, id_variant)
values (31, 'Потер', 'Гарри', 31, null, 1, null);
insert into public.voter (id, family_name, first_name, id_student, middle_name, id_poll, id_variant)
values (32, 'Уизли', 'Рон', 32, 'Артурович', 1, null);


SELECT setval('hibernate_sequence', 100, true);



