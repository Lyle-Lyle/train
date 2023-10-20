
drop table if exists `station`;
create table `station` (
                           `id` bigint not null comment 'id',
                           `name` varchar(20) not null comment '站名',
                           `city` varchar(10) not null comment '车站所属城市',
#                            `name_pinyin` varchar(50) not null comment '站名拼音',
#                            `name_py` varchar(50) not null comment '站名拼音首字母',
                           `create_time` datetime(3) comment '新增时间',
                           `update_time` datetime(3) comment '修改时间',
                           primary key (`id`),
                           unique key `name_unique` (`name`)
) engine=innodb default charset=utf8mb4 comment='车站';


drop table if exists `train`;
create table `train` (
                         `id` bigint not null comment 'id',
                         `code` varchar(20) not null comment '车次编号',
                         `type` char(1) not null comment '车次类型|枚举[TrainTypeEnum]',
                         `origin` varchar(20) not null comment '始发站',
#                          `start_pinyin` varchar(50) not null comment '始发站拼音',
                         `departure` time not null comment '出发时间',
                         `destination` varchar(20) not null comment '终点站',
#                          `end_pinyin` varchar(50) not null comment '终点站拼音',
                         `arrival` time not null comment '到站时间',
                         `create_time` datetime(3) comment '新增时间',
                         `update_time` datetime(3) comment '修改时间',
                         primary key (`id`),
                         unique key `code_unique` (`code`)
) engine=innodb default charset=utf8mb4 comment='车次';

drop table if exists `train_station`;
create table `train_station` (
                                 `id` bigint not null comment 'id',
                                 `train_code` varchar(20) not null comment '车次编号',
                                 `station_index` int not null comment '站序',
                                 `name` varchar(20) not null comment '站名',
                                 `arrival` time comment '进站时间',
                                 `departure` time comment '出站时间',
                                 `km` decimal(8, 2) not null comment '里程（公里）|从上一站到本站的距离',
                                 `create_time` datetime(3) comment '新增时间',
                                 `update_time` datetime(3) comment '修改时间',
                                 primary key (`id`),
                                 unique key `train_code_index_unique` (`train_code`, `station_index`),
                                 unique key `train_code_name_unique` (`train_code`, `name`)
) engine=innodb default charset=utf8mb4 comment='火车车站';


drop table if exists `train_carriage`;
create table `train_carriage` (
                                  `id` bigint not null comment 'id',
                                  `train_code` varchar(20) not null comment '车次编号',
                                  `car_index` int not null comment '箱号',
                                  `seat_type` char(1) not null comment '座位类型|枚举[SeatTypeEnum]',
                                  `seat_count` int not null comment '座位数',
                                  `row_count` int not null comment '排数',
                                  `col_count` int not null comment '列数',
                                  `create_time` datetime(3) comment '新增时间',
                                  `update_time` datetime(3) comment '修改时间',
                                  unique key `train_code_index_unique` (`train_code`, `car_index`),
                                  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='火车车箱';


drop table if exists `train_seat`;
create table `train_seat` (
                              `id` bigint not null comment 'id',
                              `train_code` varchar(20) not null comment '车次编号',
                              `car_index` int not null comment '厢序',
                              `row_num` char(2) not null comment '排号|01, 02',
                              `col_num` char(1) not null comment '列号|枚举[SeatColEnum]',
                              `seat_type` char(1) not null comment '座位类型|枚举[SeatTypeEnum]',
                              `carriage_seat_index` int not null comment '同车厢座序',
                              `create_time` datetime(3) comment '新增时间',
                              `update_time` datetime(3) comment '修改时间',
                              primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='座位';


drop table if exists `daily_train`;
create table `daily_train` (
                               `id` bigint not null comment 'id',
                               `date` date not null comment '日期',
                               `code` varchar(20) not null comment '车次编号',
                               `type` char(1) not null comment '车次类型|枚举[TrainTypeEnum]',
                               `origin` varchar(20) not null comment '始发站',
                               `departure` time not null comment '出发时间',
                               `destination` varchar(20) not null comment '终点站',
                               `arrival` time not null comment '到站时间',
                               `create_time` datetime(3) comment '新增时间',
                               `update_time` datetime(3) comment '修改时间',
                               primary key (`id`),
                               unique key `date_code_unique` (`date`, `code`)
) engine=innodb default charset=utf8mb4 comment='每日车次';


drop table if exists `daily_train_station`;
create table `daily_train_station` (
                                       `id` bigint not null comment 'id',
                                       `date` date not null comment '日期',
                                       `train_code` varchar(20) not null comment '车次编号',
                                       `index` int not null comment '站序',
                                       `name` varchar(20) not null comment '站名',
                                       `arrival` time comment '进站时间',
                                       `departure` time comment '出站时间',
                                       `stop_time` time comment '停站时长',
                                       `km` decimal(8, 2) not null comment '里程（公里）|从上一站到本站的距离',
                                       `create_time` datetime(3) comment '新增时间',
                                       `update_time` datetime(3) comment '修改时间',
                                       primary key (`id`),
                                       unique key `date_train_code_index_unique` (`date`, `train_code`, `index`),
                                       unique key `date_train_code_name_unique` (`date`, `train_code`, `name`)
) engine=innodb default charset=utf8mb4 comment='每日车站';


drop table if exists `daily_train_carriage`;
create table `daily_train_carriage` (
                                        `id` bigint not null comment 'id',
                                        `date` date not null comment '日期',
                                        `train_code` varchar(20) not null comment '车次编号',
                                        `index` int not null comment '箱序',
                                        `seat_type` char(1) not null comment '座位类型|枚举[SeatTypeEnum]',
                                        `seat_count` int not null comment '座位数',
                                        `row_count` int not null comment '排数',
                                        `column_count` int not null comment '列数',
                                        `create_time` datetime(3) comment '新增时间',
                                        `update_time` datetime(3) comment '修改时间',
                                        primary key (`id`),
                                        unique key `date_train_code_index_unique` (`date`, `train_code`, `index`)
) engine=innodb default charset=utf8mb4 comment='每日车箱';


drop table if exists `daily_train_seat`;
create table `daily_train_seat` (
                                    `id` bigint not null comment 'id',
                                    `date` date not null comment '日期',
                                    `train_code` varchar(20) not null comment '车次编号',
                                    `carriage_index` int not null comment '箱序',
                                    `row_num` char(2) not null comment '排号|01, 02',
                                    `col_num` char(1) not null comment '列号|枚举[SeatColEnum]',
                                    `seat_type` char(1) not null comment '座位类型|枚举[SeatTypeEnum]',
                                    `carriage_seat_index` int not null comment '同车箱座序',
                                    `sell` varchar(50) not null comment '售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖',
                                    `create_time` datetime(3) comment '新增时间',
                                    `update_time` datetime(3) comment '修改时间',
                                    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='每日座位';


drop table if exists `daily_train_ticket`;
create table `daily_train_ticket` (
                                      `id` bigint not null comment 'id',
                                      `date` date not null comment '日期',
                                      `train_code` varchar(20) not null comment '车次编号',
                                      `origin` varchar(20) not null comment '出发站',
                                      `departure` time not null comment '出发时间',
                                      `start_index` int not null comment '出发站序|本站是整个车次的第几站',
                                      `destination` varchar(20) not null comment '到达站',
                                      `arrival` time not null comment '到站时间',
                                      `end_index` int not null comment '到站站序|本站是整个车次的第几站',
                                      `Bu` int not null comment '商务座余票',
                                      `Bu_price` decimal(8, 2) not null comment '商务座票价',
                                      `Ec_p` int not null comment '经济plus余票',
                                      `Ec_p_price` decimal(8, 2) not null comment '经济plus票价',
                                      `Ec` int not null comment '经济余票',
                                      `Ec_price` decimal(8, 2) not null comment '经济票价',
                                      `create_time` datetime(3) comment '新增时间',
                                      `update_time` datetime(3) comment '修改时间',
                                      primary key (`id`),
                                      unique key `date_train_code_start_end_unique` (`date`, `train_code`, `origin`, `destination`)
) engine=innodb default charset=utf8mb4 comment='余票信息';
