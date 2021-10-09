/* 회원 테이블 */
create table cmember(
  mem_num number not null,
  email varchar2(50) not null,
  auth number(1) default 2 not null, /*0:탈퇴회원, 1:정지회원, 2:일반회원, 3:관리자*/
  constraint cmember_pk primary key (mem_num)
);
/* 회원 상세 테이블 */
create table cmember_detail(
  mem_num number not null,
  name varchar2(30) not null,
  passwd varchar2(35) not null,
  phone varchar2(15) not null,
  zipcode varchar2(5),
  address1 varchar2(90),
  address2 varchar2(90),
  reg_date date default sysdate not null,
  modify_date date default sysdate not null,
  
  constraint cmember_detail_pk primary key (mem_num),
  constraint cmember_detail_fk foreign key (mem_num) references cmember (mem_num)
);

create sequence cmember_seq;









