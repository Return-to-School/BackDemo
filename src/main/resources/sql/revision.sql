use backschooldb;

update student set gender = 1 where gender = "男";

update student set gender = 0 where gender = "女";

alter table student change gender gender tinyint not null;

insert into user value("admin1","12345678Aa",0,null);

insert into user value("admin2","12345678Aa",0,null);

insert into user value("admin3","12345678Aa",0,null);

insert into user value("beijing","12345678Aa",1,"北京");

insert into user value("tianjin","12345678Aa",1,"天津");

insert into user value("hebei","12345678Aa",1,"河北省");

insert into user value("shanxi0","12345678Aa",1,"山西省");

insert into user value("neimenggu","12345678Aa",1,"内蒙古自治区");

insert into user value("liaoning","12345678Aa",1,"辽宁省");

insert into user value("jilin","12345678Aa",1,"吉林省");

insert into user value("heilongjiang","12345678Aa",1,"黑龙江省");

insert into user value("shanghai","12345678Aa",1,"上海");

insert into user value("jiangsu","12345678Aa",1,"江苏省");

insert into user value("zhejiang","12345678Aa",1,"浙江省");

insert into user value("anhui","12345678Aa",1,"安徽省");

insert into user value("fujian","12345678Aa",1,"福建省");

insert into user value("jiangxi","12345678Aa",1,"江西省");

insert into user value("shandong","12345678Aa",1,"山东省");

insert into user value("henan","12345678Aa",1,"河南省");

insert into user value("hubei","12345678Aa",1,"湖北省");

insert into user value("hunan","12345678Aa",1,"湖南省");

insert into user value("guangdong","12345678Aa",1,"广东省");

insert into user value("guangxi","12345678Aa",1,"广西壮族自治区");

insert into user value("hainan","12345678Aa",1,"海南省");

insert into user value("chongqing","12345678Aa",1,"重庆");

insert into user value("sichuan","12345678Aa",1,"四川省");

insert into user value("guizhou","12345678Aa",1,"贵州省");

insert into user value("yunnan","12345678Aa",1,"云南省");

insert into user value("xizang","12345678Aa",1,"西藏自治区");

insert into user value("shanxi1","12345678Aa",1,"陕西省");

insert into user value("gansu","12345678Aa",1,"甘肃省");

insert into user value("qinghai","12345678Aa",1,"青海省");

insert into user value("ningxia","12345678Aa",1,"宁夏回族自治区");

insert into user value("xinjiang","12345678Aa",1,"新疆维吾尔自治区");

insert into user value("taiwan","12345678Aa",1,"台湾");

insert into user value("xianggang","12345678Aa",1,"香港特别行政区");

insert into user value("aomen","12345678Aa",1,"澳门特别行政区");
