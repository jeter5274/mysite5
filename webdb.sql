/********mysite- GuestBook ********/
--테이블, 시퀀스 삭제
drop table guestbook;
drop sequence seq_guestbook_no;

--테이블 생성
create table guestbook(
    no number,
    name varchar2(80),
    password varchar2(20),
    content varchar2(2000),
    reg_date date,
    primary key (no)
);

--시퀀스 생성
create sequence seq_guestbook_no
increment by 1
start with 1;

-- 데이터 삽입
insert into guestbook values (seq_guestbook_no.nextval, '관리자', '1234', '테스트입니다', sysdate);
insert into guestbook values (seq_guestbook_no.nextval, '아무개', '1234', '테스트입니다2', sysdate);

-- 커밋
commit;

-- 셀렉트 all
select  no,
        name,
        password,
        content,
        reg_date
from guestbook;

--삭제
delete guestbook
where no = 2;

/******** mysite- users ********/
--테이블/시퀀스 삭제
drop table users;
drop sequence seq_user_no;

--테이블 생성 
create table users(
    no number,   --회원식별 번호
    id varchar2(20) unique not null,    --아이디
    password varchar2(20) not null,     --패스워드
    name varchar2(20),     --이름
    gender varchar2(10),    --성별
    primary key(no)
);

--시퀀스 생성
create sequence seq_user_no
increment by 1
start with 1
nocache;                        --내부적으로 속도를 향상하기 위해 숫자를 미리 잡아놓아 껐다가 켰을때 손실되는 숫자가 있는데 이를 안하고 바로 연번으로 찍기위해 사용

-- 데이터 삽입
insert into users values (seq_user_no.nextval, 'admin', '1234', '관리자', 'male');
insert into users values (seq_user_no.nextval, 'test', '1234', '테스트', 'female');

-- 커밋
commit;

-- 셀렉트 all
select  no,
        id,
        password,
        name,
        gender
from users;

--삭제
delete users
where no = 2;

--수정
update users
set name = '강호동'
where no=6;


/******** mysite- board ********/

--테이블/시퀀스 삭제

--테이블 생성
create table board(
    no number,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number default 0,
    reg_date date not null,
    user_no number not null,
    primary key (no),
    constraint fk_user_no foreign key (user_no)
    references users(no)
);

--시퀀스 생성
create sequence seq_board_no
increment by 1
start with 1
nocache;

--데이터 삽입
insert into board values (seq_board_no.nextval, '제목', '내용', default, sysdate, 1);
insert into board values (seq_board_no.nextval, 'test', 'test', default, sysdate, 2);

--셀렉트 all
select  no,
        title,
        content,
        hit,
        to_char(reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        user_no as userNo
from board;

commit;
rollback;

--셀렉트 게시판 리스트
select  bo.no,
        bo.title,
        bo.content,
        bo.hit,
        to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        bo.user_no as userNo,
        us.name
from board bo left join users us
on bo.user_no = us.no
order by bo.no asc;

--셀렉트 게시글
select  bo.no,
        bo.title,
        bo.content,
        bo.hit,
        to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        bo.user_no as userNo,
        us.name
from board bo left join users us
on bo.user_no = us.no
where bo.no = 1;

--조회수 증가
update board
set hit = 0
where no = 1;

--글 수정
update board
set title = '타이틀이지롱',
    content = '콘텐트이지롱'
where no = 1;

--글삭제
delete board
where no = 3;

/******** mysite- rboard ********/

--테이블/시퀀스 삭제
drop table rboard;
drop sequence seq_rboard_no;

--테이블 생성
create table rboard(
    no number,
    user_no number not null,
    title varchar2(500),
    content varchar2(4000),
    hit number default 0,
    reg_date date,
    group_no number,
    order_no number,
    depth number,
    del_status varchar2(50) default null,
    primary key(no),
    constraint seq_rboard foreign key(user_no) references users(no) 
);

--시퀀스 생성
create sequence seq_rboard_no
increment by 1
start with 1
nocache;

--데이터 삽입
insert into rboard values(seq_rboard_no.nextval, 1, '제목', '내용', default, sysdate, 1, 1, 0, default);
insert into rboard values(seq_rboard_no.nextval, 2, '타이틀', '콘텐트', default, sysdate, 2, 1, 0, default);
insert into rboard values(seq_rboard_no.nextval, 3, '회식합시다', '회식합시다', default, sysdate, 3, 1, 0, default);
insert into rboard values(seq_rboard_no.nextval, 4, '봄입니다.', '봄이야', default, sysdate, 4, 1, 0, default);
insert into rboard values(seq_rboard_no.nextval, 5, '어디서요1?', '서울 경기?', default, sysdate, 3, 4, 1, default);
insert into rboard values(seq_rboard_no.nextval, 6, '어디서요2?', '서울에서하자', default, sysdate, 3, 2, 1, default);
insert into rboard values(seq_rboard_no.nextval, 9, '어시서요2-1?', '서울 좋지', default, sysdate, 3, 3, 2, default);

insert into rboard values(seq_rboard_no.nextval, 9, 'test', 'test', default, sysdate, seq_rboard_no.currval, 1, 0, default);

--커밋
commit;

select *
from rboard;

--전체 셀렉트
select  rb.no,
        rb.user_no userNo,
        rb.title,
        rb.content,
        rb.hit,
        to_char(rb.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        rb.group_no groupNo,
        rb.order_no orderNo,
        rb.depth,
        us.name writer
from rboard rb left join users us
on rb.user_no = us.no
order by group_no desc, order_no asc;



--롤백
rollback;

--수정
update rboard
set group_no = 2
where no = 2;

--게시글 1개 셀렉트
select  rb.no,
        rb.user_no userNo,
        rb.title,
        rb.content,
        rb.hit,
        to_char(rb.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        rb.group_no groupNo,
        rb.order_no orderNo,
        rb.depth,
        us.name writer
from rboard rb left join users us
on rb.user_no = us.no
where rb.no = 1;

--조회수 +1
update rboard
set hit = hit+1
where no =1;

--게시글 그룹의 orderNo +1
update rboard
set order_no = order_no+1
where group_no = 3
and order_no >= 2;

--글 수정
update rboard
set title = '타이틀이지롱',
    content = '콘텐트이지롱'
where no = 1;

--삭제
delete rboard
where no = 11;

--삭제 글 표시
update rboard
set title = '삭제된 글입니다.',
del_status = 'delete'
where no = 1;

--삭제상태의 글목록을 order_no desc, depth desc로 정렬해서 depth가 큰 경우 부터 삭제할 수 있도록 함
select  no,
        user_no userNo,
        title,
        content,
        hit,
        to_char(reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        group_no groupNo,
        order_no orderNo,
        depth,
        del_status delStatus
from rboard
where del_status = 'delete'
order by group_no asc, order_no desc, depth desc;


select  rb.no,
        rb.user_no userNo,
        rb.title,
        rb.content,
        rb.hit,
        to_char(rb.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        rb.group_no groupNo,
        rb.order_no orderNo,
        rb.depth,
        us.name writer,
        rb.del_status delStatus
from rboard rb left join users us
on rb.user_no = us.no
where rb.title like '%정%'
or us.name like '%정%'
and rb.del_status is null;

select  bo.no,
        bo.title,
        bo.hit,
        to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
        bo.user_no as userNo,
        us.name as writer
from board bo, users us
where bo.user_no = us.no
and title like '%test%'
order by no desc;

delete
from board;

--rownum 추가
select  r.rn,
        r.no,
        r.title,
        r.hit,
        r.regDate,
        r.userNo,
        r.writer
from (select rownum rn,
             o.no,
             o.title,
             o.hit,
             o.regDate,
             o.userNo,
             o.writer
       from (select bo.no,
                    bo.title,
                    bo.hit,
                    to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate,
                    bo.user_no as userNo,
                    us.name as writer
             from board bo, users us
             where bo.user_no = us.no
             
             and title like '%1%'
             
             order by no desc
       ) o
) r
where r.rn>=1
and r.rn<=10;

