insert into product(product_name, buy_url, image_url, lowest_price, highest_price, mall_name, product_id, naver_product_type, product_type)
values ('애플 맥북 에어 13형 2019년형 MVFH2KH/A',
'https://search.shopping.naver.com/gate.nhn?id=20514607838',
'https://shopping-phinf.pstatic.net/main_2051460/20514607838.20190806111610.jpg',
'1236010',
'2748800',
'네이버',
'20514607838',
'1',
'1');

insert into product(product_name, buy_url, image_url, lowest_price, highest_price, mall_name, product_id, naver_product_type, product_type)
values ('애플 맥북 프로 13형 2019년형 MUHN2KH/A',
'https://search.shopping.naver.com/gate.nhn?id=20486074380',
'https://shopping-phinf.pstatic.net/main_2048607/20486074380.20191210164911.jpg',
'1443870',
'2880000',
'네이버',
'20486074380',
'1',
'1');

insert into product(product_name, buy_url, image_url, lowest_price, highest_price, mall_name, product_id, naver_product_type, product_type)
values ('한성컴퓨터 BossMonster MLv.70',
'https://search.shopping.naver.com/gate.nhn?id=12459629599',
'https://shopping-phinf.pstatic.net/main_1245962/12459629599.20171023180958.jpg',
'27900',
'38820',
'네이버',
'12459629599',
'1',
'2');

insert into member(oauth_id, username, role, grade, email, motto, image_url, introduce)
values (20608121, 'JunHoPark93', 2, 1, 'abc@gmail.com', '장비충개발자', 'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg', '안녕 난 제이');

insert into member(oauth_id, username, role, grade, email, motto, image_url, introduce)
values (25656510, 'dasistHYOJIN', 2, 2, 'abc1@gmail.com', '자바개발자', 'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg', '안녕 난 에헴');

insert into member(oauth_id, username, role, grade, email, motto, image_url, introduce)
values (47378236, 'kmdngyu', 2, 1, 'abc2@gmail.com', '폭풍개발자', 'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg', '안녕 난 규동');

insert into member(oauth_id, username, role, grade, email, motto, image_url, introduce)
values (20608120, 'JunHo', 2, 1, 'abcd@gmail.com', '장비충개발자', 'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg', '안녕 난 제이');

insert into member(oauth_id, username, role, grade, email, motto, image_url, introduce)
values (30608121, 'ParkJunHo', 2, 1, 'abce@gmail.com', '장비충개발자', 'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg', '안녕 난 제이');


insert into user_product(comment, product_type, product_id, user_id)
values ('ㅎㅎ장비좋아요ㅋㅋ', '1', '1', '1');

insert into user_product(comment, product_type, product_id, user_id)
values ('ㅎㅎ장비좋아요ㅋㅋㅋ', '1', '1', '2');

insert into user_product(comment, product_type, product_id, user_id)
values ('ㅎㅎ장비좋아요ㅋㅋㅋㅋ', '2', '1', '3');

insert into user_product(comment, product_type, product_id, user_id)
values ('ㅎㅎ장비좋아요ㅋㅋㅋㅋ', '1', '2', '1');

insert into likes(user_source_id, user_target_id)
values (1, 3);

insert into notice(notice_type, header, contents)
values ('1', '1번공지사항', '상남자이바');

insert into notice(notice_type, header, contents)
values ('1', '2번공지사항', '전략가베디');

insert into notice(notice_type, header, contents)
values ('1', '3번공지사항', '폭풍규동');

insert into notice(notice_type, header, contents)
values ('1', '4번공지사항', '목장인에헴');
