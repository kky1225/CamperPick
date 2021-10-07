CREATE TABLE spmember(
	mem_num NUMBER NOT NULL,
	id VARCHAR2(12) UNIQUE NOT NULL,
	auth NUMBER(1) DEFAULT 2 NOT NULL,
	
	CONSTRAINT spmember_pk PRIMARY KEY (mem_num)
);

CREATE TABLE spmember_detail(
	mem_num NUMBER NOT NULL,
	name VARCHAR2(30) NOT NULL,
	passwd VARCHAR2(35) NOT NULL,
	phone VARCHAR2(15) NOT NULL,
	email VARCHAR2(50) NOT NULL,
	zipcode VARCHAR2(5) NOT NULL,
	address1 VARCHAR2(90) NOT NULL,
	address2 VARCHAR2(90) NOT NULL,
	photo BLOB,
	photo_name VARCHAR2(100),
	reg_date DATE DEFAULT SYSDATE NOT NULL,
	modify_date DATE DEFAULT SYSDATE NOT NULL,
	
	CONSTRAINT spmember_detail_pk PRIMARY KEY (mem_num),
	CONSTRAINT spmember_detail_fk FOREIGN KEY (mem_num) REFERENCES spmember (mem_num)
);

CREATE SEQUENCE spmember_sq;
