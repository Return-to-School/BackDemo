use backschooldb;

update student set gender = 1 where gender = "男";

update student set gender = 0 where gender = "女";

alter table student change gender gender tinyint not null;

insert into user value(1,"admin1","12345678Aa",0,null);

insert into user value(2,"admin2","12345678Aa",0,null);

insert into user value(3,"admin3","12345678Aa",0,null);

insert into user value(4,"beijing","12345678Aa",1,"北京");

insert into user value(5,"tianjin","12345678Aa",0,"天津");

insert into user value(6,"hebei","12345678Aa",0,"河北省");

insert into user value(7,"shanxi","12345678Aa",0,"山西省");

insert into user value(8,"neimenggu","12345678Aa",0,"内蒙古自治区");

insert into user value(9,"liaoning","12345678Aa",0,"辽宁省");

insert into user value(10,"jilin","12345678Aa",0,"吉林省");

insert into user value(11,"heilongjiang","12345678Aa",0,"黑龙江省");

insert into user value(12,"shanghai","12345678Aa",0,"上海");

insert into user value(13,"jiangsu","12345678Aa",0,"江苏省");

insert into user value(14,"zhejiang","12345678Aa",0,"浙江省");

insert into user value(15,"anhui","12345678Aa",0,"安徽省");

insert into user value(16,"fujian","12345678Aa",0,"福建省");

insert into user value(17,"jiangxi","12345678Aa",0,"江西省");

insert into user value(18,"shandong","12345678Aa",0,"山东省");

insert into user value(19,"henan","12345678Aa",0,"河南省");

insert into user value(20,"hubei","12345678Aa",0,"湖北省");

insert into user value(21,"hunan","12345678Aa",0,"湖南省");

insert into user value(22,"guangdong","12345678Aa",0,"广东省");

insert into user value(23,"guangxi","12345678Aa",0,"广西壮族自治区");

insert into user value(24,"hainan","12345678Aa",0,"海南省");

insert into user value(25,"chongqing","12345678Aa",0,"重庆");

insert into user value(26,"sichuan","12345678Aa",0,"四川省");

insert into user value(27,"guizhou","12345678Aa",0,"贵州省");

insert into user value(28,"yunnan","12345678Aa",0,"云南省");

insert into user value(29,"xizang","12345678Aa",0,"西藏自治区");

insert into user value(30,"shanxi","12345678Aa",0,"陕西省");

insert into user value(31,"gansu","12345678Aa",0,"甘肃省");

insert into user value(32,"qinghai","12345678Aa",0,"青海省");

insert into user value(33,"ningxia","12345678Aa",0,"宁夏回族自治区");

insert into user value(34,"xinjiang","12345678Aa",0,"新疆维吾尔自治区");

insert into user value(35,"taiwan","12345678Aa",0,"台湾");

insert into user value(36,"xianggang","12345678Aa",0,"香港特别行政区");

insert into user value(37,"aomen","12345678Aa",0,"澳门特别行政区");
