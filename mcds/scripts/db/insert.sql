-------------
---Aggregator
-------------
INSERT INTO mcdstry.aggregators(aggregatorname,weburl,description,tuser,tpassword)
VALUES('Mauj', 'http://www.mauj.com/retail/','Main URL', mcdstry.encrypt('indepay'), mcdstry.encrypt('Pyx1oxL'));
/
-------------
--Configuration
-------------
DELETE FROM CONFIGURATION;
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('General','retry.max','1000','1000','The retry limit while doing buying and cancellation','java.lang.Integer',0,0,0,'test1,test2,test3',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.isoverrideprevious','false','1','The flag will tell, if the content is same do we override','java.lang.Boolean',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.contenturl','http://www.mauj.com/retail/get_catalog.php','http://www.mauj.com/retail/get_catalog.php','Content Catalog URL','java.lang.String',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.default.locid','1','1','Default LOC ID','java.lang.Integer',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.default.posid','1','1','Default POS ID','java.lang.Integer',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.mgserver','1','1','MGalleria server used','java.lang.String',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Menu','terminal.contentpack.contentid','2','2','Content Pack ID','java.lang.Integer',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.mgdownloadsversion','1.0','1.0','This is the version of default download server version.','java.lang.String',0,0,0,'test1,test2,test3',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Aggregator.mauj','aggregator.mauj.phonemappingurl','http://www.mauj.com/retail/service_handset_support.php','http://www.mauj.com/retail/service_handset_support.php','Phone Mapping URL','java.lang.String',0,0,0,'test1',0);
/
Insert into CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) values ('Menu','terminal.top10.contentid','1','1','Content ID for Top 10 Content','java.lang.Integer',0,0,0,'test1',0);
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Terminal', 'terminal.contentpack.name.size', '20', '20', 'What will be the max name size on terminal to show content pack name', 'java.lang.Integer', '0', '1', '20', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Terminal', 'terminal.width', '24', '24', 'Maximum characters can be display in a line on terminal', 'java.lang.Integer', '0', '1', '24', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Terminal', 'terminal.songtitle.size', '13', '13', 'Maximum size of song title which shows on terminal screen', 'java.lang.Integer', '0', '1', '13', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Terminal', 'terminal.display.transaction.max.limit', '10', '10', 'Maximum Last Transaction to be shown on terminal screen', 'java.lang.Integer', '0', '1', '13', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('General', 'transaction.history.avldays', '10', '10', 'Oldest transaction in live table', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Aggregator.mauj', 'aggregator.mauj.purchaseurl', 'http://www.mauj.com/retail/retail_wrapper.php', 'http://www.mauj.com/retail/retail_wrapper.php', 'Purchase URL', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Aggregator.mauj', 'mauj.compare.phonebygeneric', 'false', 'false', 'Is Comapre with Indepay Data', 'java.lang.Boolean', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY,PARAMNAME,PARAMVALUE,DEFAULTVALUE,DESCRIPTION,PARAMTYPE,ISFLOAT,MINVALUE,MAXVALUE,ENUMVALUES,ISCHANGED) VALUES ('Terminal', 'terminal.search.text.min.limit', '3', '3', 'Minimum value required for searching an content', 'java.lang.Integer', '0', '1', '17', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('General', 'mcds.audit.trail', 'gisil.sui.jdbc.sui-ds', 'gisil.sui.jdbc.sui-ds', 'DS for Audit Trail', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('General', 'themis.jndi.name', 'gisil.vas.themis', 'gisil.vas.themis', 'JNDI for themis', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Mail', 'mcds.mail.host', 'smtpout.indepay.com', 'smtpout.indepay.com', 'Mail server for indepay', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Aggregator.mauj', 'aggregator.mauj.statusurl', 'http://www.mauj.com/retail/tran_status.php', 'http://www.mauj.com/retail/tran_status.php', 'Specify the Transaction status URL ', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Mail', 'mcds.mail.host.port', '110', '110', 'EMail Server port', 'java.lang.Integer', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Mail', 'text/plain', 'text/plain', 'text/plain', 'Specify Content Type for Mail', 'java.lang.String', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Mail', 'mcds.mail.user', 'amit_os@gisil.com', 'amit_os@gisil.com', 'Specify user who send the mail', 'java.lang.String', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Mail', 'mcds.mail.to', 'xyz@gisil.com', 'xyz@gisil.com', 'Specify user who recieves the mail', 'java.lang.String', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('General', 'allow.remote.status.non.ex.trx', 'true', 'false', 'Specify Remote status of non existing transaction', 'java.lang.Boolean', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('General', 'retry.limit', '10', '10', 'Specify retry limit', 'java.lang.Integer', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('General', 'support.email', 'mcds.support@gisil.com', 'mcds.support@gisil.com', 'Support Email at which alert will be emaild', 'java.lang.String', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Aggregator', 'transport.timeout', '35000', '15000', 'Timeout in milliseconds while waiting for response', 'java.lang.Integer', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Aggregator', 'transport.connect.timeout', '150000', '150000', 'Timeout in milliseconds while connecting with aggregator', 'java.lang.Integer', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Mail', 'mcds.mail.user.password', 'amt001', 'amt001', 'The password for email sender', 'java.lang.String', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('General', 'mcds.cms.name', 'CMS', 'CMS', 'CMS Value for reciept', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Terminal', 'menu.delivery.smsmode', 'true', 'true', 'Mode of transfer in sms', 'java.lang.Boolean', '0', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES) VALUES ('Terminal', 'menu.delivery.btmode', 'true', 'true', 'mode of transfer in bluetooth', 'java.lang.Boolean', '0', '0', '0', 'test1');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Aggregator.mauj', 'aggregator.mauj.resendurl', 'http://www.mauj.com/retail/resend_wrapper.php', 'http://www.mauj.com/retail/resend_wrapper.php', 'URL to resend the content on Customer Mobile', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.max.content.display', '10', '10', 'maximum record to be displayed on terminal', 'java.lang.Integer', '0', '1', '10', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('General', 'default.country.code', '91', '91', 'hold the default country code', 'java.lang.String', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.display.max.len', '24', '0', 'max character can be displayed on terminal', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION (PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.display.label.max.len', '10', '10', 'hold the max character for displaying label on terminal', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.rows.limit', '12', '12', 'max row on a termnal could be displayed', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.display.title.limit', '15', '15', 'terminal title display limit', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Terminal', 'terminal.display.code.limit', '6', '6', 'terminal display code limit', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('Aggregator', 'aggregator.max.serviceex.count', '25', '25', 'Max count to send mail to support stating service is down', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, ENUMVALUES, ISCHANGED) VALUES ('General', 'min.edit.privilege.level', '2', '2', 'Minimum privilege level to edit any details on UI', 'java.lang.Integer', '0', '0', '0', 'test1', '0');
/

=======
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, STATUS, ENUMVALUES, ISCHANGED) VALUES ('Simulator', 'simulator.status.flag', 'true', 'false', 'Used for Mauj Simulator ', 'java.lang.Boolean', '0', '0', '0', 'ENABLED', 'test1', '0');
/
INSERT INTO CONFIGURATION(PARAMCATEGORY, PARAMNAME, PARAMVALUE, DEFAULTVALUE, DESCRIPTION, PARAMTYPE, ISFLOAT, MINVALUE, MAXVALUE, STATUS, ENUMVALUES, ISCHANGED) VALUES ('Simulator', 'simulator.pre.url', 'http://localhost:8080/mcds/wap/maujSimulator', 'http://localhost:8080/mcds/wap/maujSimulator', 'this is mauj simulator url', 'java.lang.String', '0', '0', '0', 'ENABLED', 'test', '0');
/
-------
---Contents
----------
Insert into CONTENTS (ID,AGGREGATORID,CONTENTNAME,DESCRIPTION, STATUS) values (1,1,'Top 10','Top 10 Contents', 'DISABLED');
/
Insert into CONTENTS (ID,AGGREGATORID,CONTENTNAME,DESCRIPTION) values (2,1,'Content Packs','Contents Packs');
/
---
--- Partner definition
---
INSERT INTO PARTNER (PARTNERNAME,DESCRIPTION )
  VALUES ('Merchant', 'Terminal Owner who initiate transaction on Indepay server');
/
-- Merhcant is a special partner which databse id must be set to 999 
-- as some code is dependent on it for gaining performence.
UPDATE PARTNER SET ID = 999 WHERE PartnerName = 'Merchant';
/
INSERT INTO PARTNER (PARTNERNAME,DESCRIPTION ) VALUES ('Indepay', 'We are a partner of ourself');
/  
INSERT INTO PARTNER (PARTNERNAME,DESCRIPTION ) VALUES ('Mauj', 'Mauj Aggregator');
/
INSERT INTO PARTNER (PARTNERNAME, DESCRIPTION) VALUES ('CMS', 'Cash Management Surcharge');
/
-- CMS is a special partner which databse id must be set to 998 
-- as some code is dependent on it for gaining performence.
UPDATE PARTNER SET ID = 998 WHERE PartnerName = 'CMS';
/

  
---Commission definition

INSERT INTO COMM_FORMULA(FORMULAID, COMMISSIONTYPE ,COMMVALUE  ,PARTNERID ,SURCHARGETYPE ,SURCHARGE ,ISCLASSSURCHARGE)
VALUES(1001, 'FIXED' ,10 ,999 ,'PERCENT',2 , 0);
/
INSERT INTO COMM_FORMULA(FORMULAID, COMMISSIONTYPE ,COMMVALUE ,PARTNERID ,SURCHARGETYPE ,SURCHARGE , ISCLASSSURCHARGE)
  VALUES(1001, 'FIXED',10 ,2 ,'PERCENT' ,0.5, 1);
/
INSERT INTO COMM_FORMULA(FORMULAID, COMMISSIONTYPE ,COMMVALUE ,PARTNERID ,SURCHARGETYPE ,SURCHARGE , ISCLASSSURCHARGE)
  VALUES(1001, 'FIXED',10 ,998 ,'PERCENT' ,0.5, 1);
/

INSERT INTO COMM_FORMULA_MAPPING(AGGREGATORID,COMMFORMULAID,DELNO)
values  (1 ,1001 ,-1);
/

---------------------
--CLASS SURCHARGES
---------------------
INSERT INTO CLASS_SURCHARGES (CLASSCODE, AGGREGATORID, CLASSNAME, SURCHARGE, SURCHARGETYPE) VALUES ('SMS', '1', 'SMS', '2.25', 'FIXED');
/
INSERT INTO CLASS_SURCHARGES (CLASSCODE, AGGREGATORID, CLASSNAME, SURCHARGE, SURCHARGETYPE) VALUES ('BLUETOOTH', '1', 'BLUETOOTH', '10', 'PERCENT');
/
