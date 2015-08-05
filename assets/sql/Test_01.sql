-- 競技場
INSERT INTO ARENA (name, location) VALUES ('不明', 'ふめい');/
INSERT INTO ARENA (name, location) VALUES ('練習場1', '新越谷 ダイエー');/
INSERT INTO ARENA (name, location) VALUES ('競技場1', '浦和');/


-- 試合情報
INSERT INTO GAME (date, time, title, place, team_A, team_B) VALUES ('2011-1-1', '12:00', 'WC準決勝', '1', '1', '2');/
INSERT INTO GAME (date, time, title, place, team_A, team_B) VALUES ('2011-2-1', '14:30', 'WC決勝', '2', '1', '3');/
INSERT INTO GAME (date, time, title, place, team_A, team_B) VALUES ('2011-3-1', '13:00', '宇宙予選', '3', '2', '3');/


-- 人
INSERT INTO PERSON (name, organization) VALUES ( '高井', '1');/
INSERT INTO PERSON (name, organization) VALUES ( '喜本', '1');/
INSERT INTO PERSON (name, organization) VALUES ( '高岡', '2');/
INSERT INTO PERSON (name, organization) VALUES ( '加藤', '2');/
INSERT INTO PERSON (name, organization) VALUES ( '小野', '3');/


-- 得点
INSERT INTO POINT (game, person, section, time, amount) VALUES ( '1', '1', '前半', '5', '1');/
INSERT INTO POINT (game, person, section, time, amount) VALUES ( '1', '4', '後半', '20', '1');/
INSERT INTO POINT (game, person, section, time, amount) VALUES ( '1', '4', '後半', '30', '1');/
INSERT INTO POINT (game, person, section, time, amount) VALUES ( '2', '5', '後半', '20', '1');/


-- チーム情報
INSERT INTO TEAM (name, location, leader, contact) VALUES ( 'ING Aチーム', '台東区', '喜本', '001');/
INSERT INTO TEAM (name, location, leader, contact) VALUES ( 'ING Bチーム', '台東区', '喜本', '001');/
INSERT INTO TEAM (name, location, leader, contact) VALUES ( '浦和REDS', 'さいたま市', 'おれ', '777');


