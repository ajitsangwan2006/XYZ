CREATE TABLE mcdstry.contents( 
                id		NUMBER(10),
		aggregatorid	NUMBER(10)		NOT NULL,
		status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
                parentid	NUMBER(10)		NULL,		
                contentname	VARCHAR2(256)		NOT NULL,
                description	VARCHAR2(1024)		NULL,
		deliverymode	VARCHAR2(16)		DEFAULT 'SMS'		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,
                CONSTRAINT      contents_pk	PRIMARY KEY (id)
        );
/
 CREATE SEQUENCE mcdstry.contents_seq MINVALUE 100;
/
 CREATE OR REPLACE TRIGGER mcdstry.contents_auto
	BEFORE INSERT
	ON mcdstry.contents
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.contents_update
	BEFORE UPDATE
	ON mcdstry.contents
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
-----------------------------------------------------------------------------------

CREATE TABLE mcdstry.items( 
                id		NUMBER(10),		
                itemid		NUMBER(10)		NOT NULL,
                status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
                sku		VARCHAR2(64)		NOT NULL,
                title		VARCHAR2(512)		NOT NULL,
                type		VARCHAR2(64)		NOT NULL,
                cost		NUMBER(10,2)		NOT NULL,
                description	VARCHAR2(1024)		NULL,
                contentid	NUMBER(10)		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,
                CONSTRAINT	items_pk	PRIMARY KEY (id)
        );

/
 CREATE SEQUENCE mcdstry.items_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.items_auto
	BEFORE INSERT
	ON mcdstry.items
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.items_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.items_update
	BEFORE UPDATE
	ON mcdstry.items
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
-------------------------------------------------------------------------------------

 CREATE TABLE mcdstry.phone_mapping( 
                id		NUMBER(10),		
                type		VARCHAR2(64)		NOT NULL,
                aggregatorcode	VARCHAR2(64)		NOT NULL,
                aggregatormake	VARCHAR2(64)		NOT NULL,
                aggregatormodel VARCHAR2(64)		NOT NULL,
                status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
                make		VARCHAR2(64)		NULL,
                model		VARCHAR2(64)		NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,
                CONSTRAINT	phone_mapping_pk PRIMARY KEY (id)
        );
/
 CREATE SEQUENCE mcdstry.phone_mapping_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.phone_mapping_auto
	BEFORE INSERT
	ON mcdstry.phone_mapping
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.phone_mapping_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.phone_mapping_update
	BEFORE UPDATE
	ON mcdstry.phone_mapping
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
---------------------------------------------------------------------------------

CREATE TABLE mcdstry.transaction_archive(
		id		NUMBER(10),
                trxid		NUMBER(10)		NOT NULL,
                delno		NUMBER(12)		NOT NULL,
                merchantid	NUMBER(10)		NULL,
                trxtype		VARCHAR2(64)		DEFAULT 'BUYING'	NOT NULL,
                aggregatorid	NUMBER(10)		NOT NULL,
                baseamount	NUMBER(12,2)		NOT NULL,
                commissionamount NUMBER(12,2)		NOT NULL,
                surcharge	NUMBER(12,2)		DEFAULT 0,
                totalamount	NUMBER(12,2)		NOT NULL,
                status		VARCHAR2(64)		NOT NULL,
                parenttrxid	NUMBER(10)		NULL,
                reconstatus	VARCHAR2(64)		NULL,
                isrefund	NUMBER(1)		DEFAULT 0		NOT NULL,
                trxremarks	VARCHAR2(512)		NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP,
                tsupdated	TIMESTAMP		NOT NULL,
                merchantdebitamt NUMBER(12,2)		NULL,
                merchantremainingbal NUMBER(12,2)	NULL,
                msisdn		VARCHAR2(13)		NOT NULL,
                phonemake	VARCHAR2(64)		NOT NULL,
                phonemodel	VARCHAR2(64)		NOT NULL,
                aggregatorphonecode VARCHAR2(64)	NOT NULL,
                sku		VARCHAR2(64)		NOT NULL,
                skuname		VARCHAR2(512)		NOT NULL,
                paymantmode	VARCHAR2(64)		DEFAULT 'CASH'		NOT NULL,
		aggregatortrxid	VARCHAR2(64)		NULL,
                responsecode	VARCHAR2(10)		NULL,
                responsemessage VARCHAR2(512)		NULL,
		deliverymode	VARCHAR2(16)		NOT NULL,
                commformulaid	NUMBER(10)		NOT NULL,
                tsrequest	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsresponse	TIMESTAMP		NULL,
		
                CONSTRAINT	transaction_archive_pk PRIMARY KEY (id)
               
        );

/
 CREATE SEQUENCE mcdstry.transaction_archive_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_archive_auto
	BEFORE INSERT
	ON mcdstry.transaction_archive
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.transaction_archive_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_archive_update
	BEFORE UPDATE
	ON mcdstry.transaction_archive
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
---------------------------------------------------------------------------

CREATE TABLE mcdstry.pack( 
                id		NUMBER(10)		NOT NULL,
                status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
		catalogid	VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
                sku		VARCHAR2(64)		NOT NULL,
                title		VARCHAR2(512)		NOT NULL,
                type		VARCHAR2(64)		NOT NULL,
                cost		NUMBER(10,2)		NOT NULL,
                description	VARCHAR2(1024)		NULL,
                contentid	NUMBER(10)		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	pack_pk		PRIMARY KEY (id)               
        );

/
 CREATE SEQUENCE mcdstry.pack_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.pack_auto
	BEFORE INSERT
	ON mcdstry.pack
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.pack_update
	BEFORE UPDATE
	ON mcdstry.pack
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
-------------------------------------------------------------------------------------

CREATE TABLE mcdstry.pack_item( 
		id		NUMBER(10)		NOT NULL,
                itemid		NUMBER(10)		NOT NULL,
                status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
                title		VARCHAR2(512)		NOT NULL,
                type		VARCHAR2(64)		NOT NULL,
                packid		NUMBER(10)		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	pack_item_pk	PRIMARY KEY (id)               
        );

/
 CREATE SEQUENCE mcdstry.pack_item_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.pack_item_auto
	BEFORE INSERT
	ON mcdstry.pack_item
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.pack_item_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.pack_item_update
	BEFORE UPDATE
	ON mcdstry.pack_item
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
------------------------------------------------------------------------------

CREATE TABLE mcdstry.aggregator_user(  
		id		NUMBER(10)		NOT NULL,
                aggregatorid	NUMBER(10)		NOT NULL,
                userid		VARCHAR2(25)		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,
		CONSTRAINT	aggregator_user_pk	PRIMARY KEY (id),
                CONSTRAINT	aggregator_user_uk UNIQUE (aggregatorid,userid)           
    );

/
 CREATE SEQUENCE mcdstry.aggregator_user_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.aggregator_user_auto
	BEFORE INSERT
	ON mcdstry.aggregator_user
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.aggregator_user_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.aggregator_user_update
	BEFORE UPDATE
	ON mcdstry.aggregator_user
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
-------------------------------------------------------------------------------

CREATE TABLE mcdstry.configuration( 
                id		NUMBER(10)		NOT NULL,
                paramcategory	VARCHAR2(256)		NOT NULL,
                paramname	VARCHAR2(256)		NOT NULL,
                paramvalue	VARCHAR2(256)		NOT NULL,
                defaultvalue	VARCHAR2(256)		NOT NULL,
                description	VARCHAR2(1028)		NULL,
                paramtype	VARCHAR2(64)		NOT NULL,
                isfloat		NUMBER(1)		DEFAULT 0		NOT NULL,
                minvalue	NUMBER(10,2)		NOT NULL,
                maxvalue	NUMBER(10,2)		NOT NULL,
                status		VARCHAR2(32)		DEFAULT 'ENABLED'	NOT NULL,
                enumvalues	VARCHAR2(256)		NOT NULL,
                ischanged	NUMBER(1)		DEFAULT 0		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	configration_pk	PRIMARY KEY (id),
                CONSTRAINT	configration_uk1 UNIQUE (paramname)             
	);

/
 CREATE SEQUENCE mcdstry.configuration_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.configuration_auto
	BEFORE INSERT
	ON mcdstry.configuration
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.configuration_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.configuration_update
	BEFORE UPDATE
	ON mcdstry.configuration
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
-------------------------------------------------------------------------------

CREATE TABLE mcdstry.transaction_commission( 
                id		NUMBER(10)		NOT NULL,
                trxid		NUMBER(10)		NOT NULL,
                partnerid	NUMBER(10)		NOT NULL,
                commvalue	NUMBER(12,2)		NOT NULL,
                remarks		VARCHAR2(2048)		NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	transaction_commission_pk PRIMARY KEY (id),
                CONSTRAINT	transaction_commission_uk1 UNIQUE (trxid,partnerid)
 );

/
 CREATE SEQUENCE mcdstry.transaction_commission_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_commission_auto
	BEFORE INSERT
	ON mcdstry.transaction_commission
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.transaction_commission_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_commission_update
	BEFORE UPDATE
	ON mcdstry.transaction_commission
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /

-----------------------------------------------------------------------------------

CREATE TABLE mcdstry.trx_commission_archive( 
                id		NUMBER(10)		NOT NULL,
                trx_comm_id     NUMBER(10)		NOT NULL,
                trxid		NUMBER(10)		NOT NULL,
                partnerid	NUMBER(10)		NOT NULL,
                commvalue	NUMBER(12,2)		NOT NULL,
                remarks		VARCHAR2(2048)		NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	trx_commission_archive_pk PRIMARY KEY (id),
                CONSTRAINT	trx_commission_archive_uk1 UNIQUE (trxid,partnerid)
 );

/
 CREATE SEQUENCE mcdstry.trx_commission_archive_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.trx_commission_archive_auto
	BEFORE INSERT
	ON mcdstry.trx_commission_archive
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.trx_commission_archive_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.trx_commission_archive_update
	BEFORE UPDATE
	ON mcdstry.trx_commission_archive
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /

-----------------------------------------------------------------------------------

CREATE TABLE mcdstry.comm_formula( 
                id		NUMBER(10)		NOT NULL,
		formulaid	NUMBER(10)		NOT NULL,
                partnerid	NUMBER(10)		NOT NULL,
                commissiontype	VARCHAR2(16)		NOT NULL,
                commvalue	NUMBER(12,2)		NOT NULL,
                surcharge	NUMBER(12,2)		NOT NULL,
                surchargetype	VARCHAR2(16)		NOT NULL,
		status		VARCHAR2(32)		DEFAULT 'ENABLED'	NOT NULL,
		isclasssurcharge NUMBER(1)		DEFAULT 0		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	comm_formula_pk PRIMARY KEY (id)               
        );


/
 CREATE SEQUENCE mcdstry.comm_formula_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.comm_formula_auto
	BEFORE INSERT
	ON mcdstry.comm_formula
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.comm_formula_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.comm_formula_update
	BEFORE UPDATE
	ON mcdstry.comm_formula
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
------------------------------------------------------------------------------------

CREATE TABLE mcdstry.comm_formula_mapping( 
                id		NUMBER(10)		NOT NULL,
                aggregatorid	NUMBER(10)		NOT NULL,
                commformulaid	NUMBER(10)		NOT NULL,
                delno		NUMBER(10)		NOT NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,               
                CONSTRAINT	comm_formula_mapping_pk PRIMARY KEY (id),
                CONSTRAINT	comm_formula_mapping_uk UNIQUE (aggregatorid, commformulaid, delno)
        );

/
 CREATE SEQUENCE mcdstry.comm_formula_mapping_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.comm_formula_mapping_auto
	BEFORE INSERT
	ON mcdstry.comm_formula_mapping
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.comm_formula_mapping_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.comm_formula_mapping_update
	BEFORE UPDATE
	ON mcdstry.comm_formula_mapping
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
------------------------------------------------------------------------------------------------------------

CREATE TABLE mcdstry.aggregators(	
		id		NUMBER(10), 
		aggregatorname	VARCHAR2(256)		NOT NULL, 
		weburl		VARCHAR2(256)		NULL, 
		description	VARCHAR2(1028)		NULL, 
		tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,     
		status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL, 
		tuser		VARCHAR2(512)		NULL, 
		tpassword	VARCHAR2(512)		NULL, 
		CONSTRAINT	aggregators_pk	PRIMARY KEY(id),
		CONSTRAINT	aggregator_uk	UNIQUE (aggregatorname)
   );

/
 CREATE SEQUENCE mcdstry.aggregators_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.aggregators_auto
	BEFORE INSERT
	ON mcdstry.aggregators
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.aggregators_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.aggregators_update
	BEFORE UPDATE
	ON mcdstry.aggregators
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
----------------------------------------------------------------------------------------------------
CREATE TABLE mcdstry.transaction(
		id		NUMBER(10)		NOT NULL,
                delno		NUMBER(12)		NOT NULL,
                merchantid	NUMBER(10)		NULL,
                trxtype		VARCHAR2(64)		DEFAULT 'BUYING'	NOT NULL,
                aggregatorid	NUMBER(10)		NOT NULL,
                baseamount	NUMBER(12,2)		NOT NULL,
                commissionamount NUMBER(12,2)		NOT NULL,
                surcharge	NUMBER(12,2)		DEFAULT 0,
                totalamount	NUMBER(12,2)		NOT NULL,
                status		VARCHAR2(64)		NOT NULL,
                parenttrxid	NUMBER(10)		NULL,
                reconstatus	VARCHAR2(64)		NULL,
                isrefund	NUMBER(1)		DEFAULT 0		NOT NULL,
                trxremarks	VARCHAR2(512)		NULL,
                tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL,
                merchantdebitamt NUMBER(12,2)		NULL,
                merchantremainingbal NUMBER(12,2)	NULL,
                msisdn		VARCHAR2(13)		NOT NULL,
                phonemake	VARCHAR2(64)		NOT NULL,
                phonemodel	VARCHAR2(64)		NOT NULL,
                aggregatorphonecode VARCHAR2(64)	NOT NULL,
                sku		VARCHAR2(64)		NOT NULL,
                skuname		VARCHAR2(512)		NOT NULL,
                paymantmode	VARCHAR2(64)		DEFAULT 'CASH'		NOT NULL,
		aggregatortrxid	VARCHAR2(64)		NULL,
                responsecode	VARCHAR2(10)		NULL,
                responsemessage VARCHAR2(512)		NULL,
		deliverymode	VARCHAR2(16)		NOT NULL,
                commformulaid	NUMBER(10)		NOT NULL,
                tsrequest	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsresponse	TIMESTAMP		NULL,
		
                CONSTRAINT	transaction_pk  PRIMARY KEY (id)               
        );

/
 CREATE SEQUENCE mcdstry.transaction_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_auto
	BEFORE INSERT
	ON mcdstry.transaction
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
		SELECT ID INTO :NEW.MERCHANTID FROM THEMIS.MERCHANT WHERE DELNO= :NEW.DELNO;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.transaction_update
	BEFORE UPDATE
	ON mcdstry.transaction
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /
---------------------------------------------------------------------------------------------


 CREATE TABLE mcdstry.partner(
		id		NUMBER(10)		NOT NULL,
		partnername	VARCHAR2(128)		NOT NULL,
		description	VARCHAR2(1024)		NULL,
		tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL, 
		status		VARCHAR2(16)		DEFAULT 'ENABLED'	NOT NULL,
		CONSTRAINT	partner_pk	PRIMARY KEY(id),
		CONSTRAINT	partner_uk	UNIQUE(partnername)
	);
/
 CREATE SEQUENCE mcdstry.partner_seq;
/
 CREATE OR REPLACE TRIGGER mcdstry.partner_auto
	BEFORE INSERT
	ON mcdstry.partner
	FOR EACH ROW
	BEGIN
		SELECT mcdstry.partner_seq.NEXTVAL
		INTO :NEW.id
		FROM DUAL;
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.partner_update
	BEFORE UPDATE
	ON mcdstry.partner
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /

---------------------------------------------------------------------------------------------

CREATE TABLE mcdstry.class_surcharges  (
		classcode       VARCHAR2(16)            NOT NULL,
		aggregatorid    NUMBER(10)              NOT NULL,
		classname       VARCHAR2(32)            NOT NULL,
		surcharge       NUMBER(12,2)            NOT NULL,
		surchargetype	VARCHAR2(16)		NOT NULL,
		status		VARCHAR2(32)		DEFAULT 'ENABLED'	NOT NULL,
		tscreated	TIMESTAMP		DEFAULT SYSTIMESTAMP	NOT NULL,
                tsupdated	TIMESTAMP		NOT NULL, 
   CONSTRAINT pk_class_surcharges PRIMARY KEY (classcode,aggregatorid)
);
/

 CREATE OR REPLACE TRIGGER mcdstry.class_surcharges_auto
	BEFORE INSERT
	ON mcdstry.class_surcharges
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
/
 CREATE OR REPLACE TRIGGER mcdstry.class_surcharges_update
	BEFORE UPDATE
	ON mcdstry.class_surcharges
	FOR EACH ROW
	BEGIN
		:NEW.tsupdated := SYSTIMESTAMP;
 END;
 /

--
-- Function for encrypting
--
CREATE OR REPLACE FUNCTION mcdstry.encrypt
(
	input_string	IN	VARCHAR2
) RETURN RAW
IS
	SH1_ECB_ZERO CONSTANT PLS_INTEGER :=  dbms_crypto.HASH_SH1 + dbms_crypto.CHAIN_ECB + dbms_crypto.PAD_ZERO;
	seed VARCHAR2(64) :=  '74ACEF09FC1CF045F5E0799FB49465721E1810E7A5556EE25948E6E18DBAE384';
	converted_seed		RAW(64);
	converted_string	RAW(64);
	encrypted_string	RAW(64);

BEGIN
	converted_seed   := UTL_I18N.STRING_TO_RAW(seed, 'AL32UTF8');
	converted_string := UTL_I18N.STRING_TO_RAW(input_string,
'AL32UTF8');
	encrypted_string :=	dbms_crypto.ENCRYPT(src => converted_string,
typ => SH1_ECB_ZERO, key => converted_seed, iv =>  NULL);

RETURN encrypted_string;
END ;
/
--
-- Function for decrypting
--
CREATE OR REPLACE FUNCTION mcdstry.decrypt
(
	input_string	IN	VARCHAR2
) RETURN VARCHAR2
IS
	SH1_ECB_ZERO CONSTANT PLS_INTEGER :=  dbms_crypto.HASH_SH1 + dbms_crypto.CHAIN_ECB + dbms_crypto.PAD_ZERO;
	seed VARCHAR2(64) :=   '74ACEF09FC1CF045F5E0799FB49465721E1810E7A5556EE25948E6E18DBAE384';
	converted_seed		RAW(64);
	converted_string	VARCHAR2(200);
	decrypted_string	VARCHAR2(200);

BEGIN
	converted_seed   := UTL_I18N.STRING_TO_RAW(seed, 'AL32UTF8');
	converted_string := UTL_I18N.STRING_TO_RAW(input_string,
'AL32UTF8');
	decrypted_string := dbms_crypto.DECRYPT(src => input_string, typ => SH1_ECB_ZERO, key => converted_seed, iv =>  NULL);
	converted_string := UTL_I18N.RAW_TO_CHAR(decrypted_string,
'AL32UTF8');

RETURN converted_string;
END ;
/

---
--Constraints
---

ALTER TABLE mcdstry.contents ADD CONSTRAINT contents_contents_fk FOREIGN KEY(parentid) REFERENCES mcdstry.contents(id);
/
ALTER TABLE mcdstry.items ADD CONSTRAINT items_contents_fk FOREIGN KEY(contentid) REFERENCES mcdstry.contents(id);
/
ALTER TABLE mcdstry.pack_item ADD CONSTRAINT pack_item_pack_fk FOREIGN KEY(packid) REFERENCES mcdstry.pack(id);
/
ALTER TABLE mcdstry.pack ADD CONSTRAINT pack_contents_fk FOREIGN KEY(contentid) REFERENCES mcdstry.contents(id);
/
ALTER TABLE mcdstry.transaction ADD CONSTRAINT transaction_aggregators_fk FOREIGN KEY(AGGREGATORID) REFERENCES mcdstry.aggregators(id);
/
ALTER TABLE mcdstry.transaction ADD CONSTRAINT transaction_transaction_fk FOREIGN KEY(PARENTTRXID) REFERENCES mcdstry.transaction(id);
/
ALTER TABLE mcdstry.transaction_commission ADD CONSTRAINT transaction_comm_trans_fk FOREIGN KEY(TRXID) REFERENCES mcdstry.transaction(id);
/
ALTER TABLE mcdstry.comm_formula ADD CONSTRAINT comm_formula_partner_fk FOREIGN KEY(PARTNERID) REFERENCES mcdstry.partner(id);
/
ALTER TABLE mcdstry.comm_formula_mapping ADD CONSTRAINT comm_formula_mapp_aggr_fk FOREIGN KEY(AGGREGATORID) REFERENCES mcdstry.aggregators(id);
/
ALTER TABLE mcdstry.transaction_archive ADD CONSTRAINT transaction_archive_aggr_fk FOREIGN KEY(AGGREGATORID) REFERENCES mcdstry.aggregators(id);
/
ALTER TABLE mcdstry.transaction_archive ADD CONSTRAINT trans_archive_trans_fk FOREIGN KEY(PARENTTRXID) REFERENCES mcdstry.transaction(id);
/
ALTER TABLE mcdstry.contents ADD CONSTRAINT contents_aggr_fk FOREIGN KEY(AGGREGATORID) REFERENCES mcdstry.AGGREGATORS(id);
/
