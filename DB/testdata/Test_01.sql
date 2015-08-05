-- 競技場
INSERT INTO ARENA (ID, name, location) VALUES ('1', '練習場1', '新越谷 ダイエー');
INSERT INTO ARENA (ID, name, location) VALUES ('2', '競技場1', '浦和');
INSERT INTO ARENA (ID, name, location) VALUES ('0', '不明', 'ふめい');


-- 試合情報
INSERT INTO GAME (ID, date, time, title, place, team_A, team_B) VALUES ('1', '2011-1-1', '12:00', 'WC準決勝', '1', '1', '2');
INSERT INTO GAME (ID, date, time, title, place, team_A, team_B) VALUES ('2', '2011-2-1', '14:30', 'WC決勝', '2', '1', '3');


-- 人
INSERT INTO PERSON (ID, name, organization) VALUES ('1', '高井', '1');
INSERT INTO PERSON (ID, name, organization) VALUES ('2', '澤野', '1');
INSERT INTO PERSON (ID, name, organization) VALUES ('3', '本田', '2');
INSERT INTO PERSON (ID, name, organization) VALUES ('4', '加藤', '2');
INSERT INTO PERSON (ID, name, organization) VALUES ('5', '小野', '3');


-- 得点
INSERT INTO POINT (ID, game, person, section, time, amount) VALUES ('1', '1', '1', '前半', '5', '1');
INSERT INTO POINT (ID, game, person, section, time, amount) VALUES ('2', '1', '4', '後半', '30', '1');
INSERT INTO POINT (ID, game, person, section, time, amount) VALUES ('3', '2', '5', '後半', '20', '');


-- チーム情報
INSERT INTO TEAM (ID, name, location, leader, contact) VALUES ('1', 'ING Aチーム', '台東区', '本田', '001');
INSERT INTO TEAM (ID, name, location, leader, contact) VALUES ('2', 'ING Bチーム', '台東区', 'おれ', '001');
INSERT INTO TEAM (ID, name, location, leader, contact) VALUES ('3', '浦和REDS', 'さいたま市', '小野', '777');


