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
"RES_PRICE" NUMBER NOT NULL, 
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













