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

/* 캠핑 테이블 */
CREATE TABLE camping(	
camping_num NUMBER NOT NULL, 
camp_name VARCHAR2(70 BYTE) NOT NULL, 
CAMP_ADDRESS VARCHAR2(100 BYTE) NOT NULL , 
CAMP_PHONE VARCHAR2(50 BYTE) NOT NULL , 
RCOUNT NUMBER NOT NULL, 
CONSTRAINT CAMPING_PK PRIMARY KEY (CAMPING_NUM)
);

create sequence camping_seq;

/*객실 테이블*/
CREATE TABLE CROOM(
ROOM_NUM NUMBER NOT NULL, 
ROOM_NAME VARCHAR2(70 BYTE) NOT NULL , 
PEOPLE NUMBER(10,0) NOT NULL, 
AREA NUMBER(10,0) NOT NULL, 
PRICE NUMBER(10,0) NOT NULL, 
CHECKIN VARCHAR2(30 BYTE) NOT NULL, 
CHECKOUT VARCHAR2(30 BYTE) NOT NULL, 
INFO CLOB NOT NULL ENABLE, 
FILENAME VARCHAR2(150 BYTE), 
CAMPING_NUM NUMBER NOT NULL, 
UPLOADFILE BLOB, 
 CONSTRAINT ROOM_NUM_PK PRIMARY KEY (ROOM_NUM),
 CONSTRAINT CROOM_FK1 FOREIGN KEY (CAMPING_NUM) REFERENCES CAMPING (CAMPING_NUM)
);
create sequence croom_seq;

/*예약 테이블*/
CREATE TABLE CRESERVE(	
RES_NUM NUMBER NOT NULL, 
RES_NAME VARCHAR2(30 BYTE) NOT NULL, 
RES_PHONE VARCHAR2(15 BYTE) NOT NULL, 
HEADCOUNT NUMBER NOT NULL, 
RES_START VARCHAR2(20 BYTE) NOT NULL, 
RES_END VARCHAR2(20 BYTE) NOT NULL, 
RES_STATE VARCHAR2(20 BYTE) DEFAULT '결제대기' NOT NULL, 
MEM_NUM NUMBER NOT NULL, 
ROOM_NUM NUMBER NOT NULL, 
CAMPING_NUM NUMBER NOT NULL, 
RES_EMAIL VARCHAR2(100 BYTE) NOT NULL, 
RES_PRICE NUMBER NOT NULL, 
CONSTRAINT RES_NUM_PK PRIMARY KEY (RES_NUM),
CONSTRAINT CRESERVE_FK FOREIGN KEY (MEM_NUM) REFERENCES CMEMBER (MEM_NUM), 
CONSTRAINT CRESERVE_FK1 FOREIGN KEY (ROOM_NUM) REFERENCES CROOM (ROOM_NUM), 
CONSTRAINT CRESERVE_FK2 FOREIGN KEY (CAMPING_NUM) REFERENCES CAMPING (CAMPING_NUM)
);
create sequence creserve_seq;

/*예약 상세 테이블*/
CREATE TABLE CRESERVE_DETAIL (	
RESD_NUM NUMBER NOT NULL, 
RES_NUM NUMBER NOT NULL, 
ROOM_NUM NUMBER NOT NULL, 
CONSTRAINT RESD_NUM_PK PRIMARY KEY (RESD_NUM),
CONSTRAINT CRESERVE_DETAIL_FK1 FOREIGN KEY (RES_NUM) REFERENCES CRESERVE (RES_NUM), 
CONSTRAINT CRESERVE_DETAIL_FK2 FOREIGN KEY (ROOM_NUM) REFERENCES CROOM (ROOM_NUM)
);
create sequence creserve_detail_seq;

/*공지사항*/
create table cnotice(
	notice_num number not null,
	title varchar2(150) not null,
	content clob  not null,
	hit number(10,0) default 0  not null,
	reg_date date default sysdate not null,
	modify_date date default sysdate not null,
	uploadfile blob,
	filename varchar2(150),
	ip varchar2(40) not null,
	mem_num number not null,
	constraint cnotice_pk primary key (notice_num),
	constraint cnotice_fk foreign key (mem_num) references cmember (mem_num)
);

create sequence cnotice_seq;

/*공지사항 댓글*/
create table cnotice_reply(
	nre_num number not null,
	re_content clob not null,
	re_ip varchar2(40) not null,
	re_date date default sysdate not null,
	re_modifydate date default sysdate not null,
	notice_num number not null,
	mem_num number not null,
	constraint cnotice_reply_pk primary key (nre_num),
	constraint cnotice_reply_fk1 foreign key (notice_num) references cnotice (notice_num),
	constraint cnotice_reply_fk2 foreign key (mem_num) references cmember (mem_num)
);
create sequence cnotice_reply_seq;

/*거래게시판*/
create table cmarket(
	market_num number not null,
	title varchar2(150) not null,
	content clob  not null,
	hit number(10,0) default 0  not null,
	reg_date date default sysdate not null,
	modify_date date default sysdate not null,
	uploadfile blob,
	filename varchar2(150),
	ip varchar2(40) not null,
	state varchar2(20) default 0 not null,	/* 거래상태 0:거래중, 1:거래완료 */
	choice varchar2(20) default 0 not null,	/* 거래구분 0:삽니다, 1:팝니다*/
	mem_num number not null,
	constraint cmarket_pk primary key (market_num),
	constraint cmarket_fk foreign key (mem_num) references cmember (mem_num)
);

create sequence cmarket_seq;

/*거래게시판 댓글*/
create table cmarket_reply(
	mre_num number not null,
	re_content clob not null,
	re_ip varchar2(40) not null,
	re_date date default sysdate not null,
	re_modifydate date default sysdate not null,
	market_num number not null,
	mem_num number not null,
	constraint cmarket_reply_pk primary key (mre_num),
	constraint cmarket_reply_fk1 foreign key (market_num) references cmarket (market_num),
	constraint cmarket_reply_fk2 foreign key (mem_num) references cmember (mem_num)
);
create sequence cmarket_reply_seq;

/* 결제 테이블 */
CREATE TABLE payment(
	pay_num NUMBER PRIMARY KEY,
	imp_uid VARCHAR(25) NOT NULL,
	merchant_uid VARCHAR(25) NOT NULL,
	biz_email VARCHAR(50) NOT NULL,
	pay_date DATE NOT NULL,
	amount NUMBER NOT NULL,
	res_num NUMBER NOT NULL,
	mem_num NUMBER NOT NULL,
	
	CONSTRAINT payment_fk1 FOREIGN KEY (res_num) REFERENCES creserve(res_num),
	CONSTRAINT payment_fk2 FOREIGN KEY (mem_num) REFERENCES cmember(mem_num)
);
CREATE SEQUENCE payment_seq;

/* 예약 알림 */
CREATE TABLE creserve_notification(
	not_num NUMBER PRIMARY KEY,
	message CLOB NOT NULL,
	date_time DATE DEFAULT SYSDATE NOT NULL,
	read_time DATE DEFAULT SYSDATE NOT NULL,
	res_num NUMBER NOT NULL,
	mem_num NUMBER NOT NULL,
	
	CONSTRAINT creserve_notification_fk1 FOREIGN KEY (res_num) REFERENCES creserve(res_num),
	CONSTRAINT creserve_notification_fk2 FOREIGN KEY (mem_num) REFERENCES cmember(mem_num)
);
CREATE SEQUENCE creserve_notification_seq;














