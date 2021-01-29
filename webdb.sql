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