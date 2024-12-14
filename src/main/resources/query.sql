-- Create a new database called 'certmanagement'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Create the new database if it does not exist already
IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'certmanagement'
)
CREATE DATABASE certmanagement
GO


INSERT INTO [dbo].[category] (description, name) VALUES ('A high-level, class-based, object-oriented programming language.', 'Java');
INSERT INTO [dbo].[category] (description, name) VALUES ('A high-level, dynamic programming language that enables interactive web pages.', 'JavaScript');
INSERT INTO [dbo].[category] (description, name) VALUES ('A high-level, interpreted programming language known for its readability.', 'Python');
INSERT INTO [dbo].[category] (description, name) VALUES ('A modern, object-oriented programming language developed by Microsoft.', 'C#');
INSERT INTO [dbo].[category] (description, name) VALUES ('A dynamic programming language focused on simplicity and productivity.', 'Ruby');


INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (1, 150.0, 'OCP-JAVA11', 'Oracle Certified Professional, Java SE 11 Developer');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (2, 100.0, 'JS-DEV-CERT', 'JavaScript Developer Certificate');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (3, 75.0, 'PCEP-001', 'Python Institute Certified Entry-Level Python Programmer (PCEP)');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (4, 120.0, 'MS-CSHARP', 'Microsoft Certified: C# Developer');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (5, 90.0, 'RUBY-CP', 'Ruby Association Certified Ruby Programmer');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (1, 150.0, 'AWS-DEV', 'AWS Certified Developer – Associate');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (2, 110.0, 'CJD-JS', 'Certified JavaScript Developer');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (3, 85.0, 'PCAP-001', 'PCAP – Certified Associate in Python Programming');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (4, 135.0, 'MS-AZUREDEV', 'Microsoft Certified: Azure Developer Associate');
INSERT INTO [dbo].[certificate] (category_id, cost, id, cert_name) VALUES (5, 95.0, 'ROR-DEV', 'Ruby on Rails Developer Certification');
