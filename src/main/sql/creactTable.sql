-- 元数据
CREATE TABLE meter_collection
(
    id            varchar(50) PRIMARY KEY COMMENT '记录唯一标识',
    type          varchar(10) COMMENT '数据传输类型，real 全部、change变化、his 断线续传',
    gateway_sn    VARCHAR(20) COMMENT '网关序列号',
    gateway_time  TIMESTAMP COMMENT '网关上报时间',
    meter_sn      VARCHAR(20) COMMENT '电表序列号',
    point_id      VARCHAR(20) COMMENT '测点ID',
    point_quality INT COMMENT '测点质量',
    point_value   DECIMAL(10, 3) COMMENT '测点数值'
) COMMENT '电表数据采集表';
-- 添加索引
CREATE INDEX idx_meter_sn ON meter_collection (meter_sn);
CREATE INDEX idx_gateway_time ON meter_collection (gateway_time);

-- 历史功率
CREATE TABLE meter_electric_energy
(
    id            varchar(50) PRIMARY KEY  COMMENT '记录唯一标识',
    collection_id varchar(50) COMMENT '源数据id',
    gateway_sn    VARCHAR(20) COMMENT '网关序列号',
    gateway_time  TIMESTAMP COMMENT '网关上报时间',
    meter_sn      VARCHAR(20) COMMENT '电表序列号',
    value         DECIMAL(10, 3) COMMENT '测点数值',
    upValue       DECIMAL(10, 3) COMMENT '增加数值'
) COMMENT '历史功率';
-- 添加索引
CREATE INDEX idx_meter_sn ON meter_electric_energy (meter_sn);
CREATE INDEX idx_gateway_time ON meter_electric_energy (gateway_time);

-- 按日统计
CREATE TABLE meter_electric_energy_day
(
    id            varchar(50) PRIMARY KEY  COMMENT '记录唯一标识',
    meter_sn      VARCHAR(20) COMMENT '电表序列号',
    value         DECIMAL(10, 3) COMMENT '测点数值',
    date          DATE  COMMENT '统计日期',
    insert_time   TIMESTAMP COMMENT '入库时间'
) COMMENT '用电量按日统计';
-- 添加索引
CREATE INDEX idx_meter_sn ON meter_electric_energy_day (meter_sn);
CREATE INDEX idx_gateway_time ON meter_electric_energy_day (date);

DROP TABLE electricity_meter;
CREATE TABLE `electricity_meter` (
                                     `id` varchar(50) PRIMARY KEY COMMENT '记录唯一标识',
                                     `meter_sn` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '电表的序列号，具有唯一性',
                                     `brand` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电表的品牌',
                                     `model` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电表的型号',
                                     `installation_date` date DEFAULT NULL COMMENT '电表的安装日期',
                                     `last_reading_date` date DEFAULT NULL COMMENT '最近一次电表读数的日期',
                                     `last_reading_value` decimal(10,2) DEFAULT NULL COMMENT '最近一次电表读数的值',
                                     `reading_unit` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电表读数的单位',
                                     `status` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电表的当前状态',
                                     `apartment_number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电表所在的户号',
                                     `project_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '电表所属的项目id',
                                     `space_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电表所在空间id',
                                     `location_description` text COLLATE utf8_bin COMMENT '电表的详细位置描述',
                                     `gateway_sn`  VARCHAR(20) COMMENT '网关序列号',
                                     `is_online` TINYINT(1) DEFAULT 0 COMMENT '电表是否在线',
                                     `device_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '连接在电表上的设备类型，如冰箱、空调等',
                                     `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建的时间',
                                     `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后一次修改的时间',
                                     `note` text COLLATE utf8_bin COMMENT '额外的备注信息',
                                     UNIQUE KEY `meter_sn` (`meter_sn`),
                                     KEY `project_id` (`project_id`),
                                     KEY `model` (`model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电表的基础信息表';


CREATE TABLE `building_area` (
                                 `id` varchar(50) PRIMARY KEY COMMENT '记录唯一标识',
                                 `block` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '幢',
                                 `building` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '楼',
                                 `room` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '室',
                                 `space` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '空间',
                                 `space_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '空间名称',
                                 `detailed_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
                                 `space_description` text COLLATE utf8_bin COMMENT '空间描述',
                                 `note` text COLLATE utf8_bin COMMENT '额外的备注信息',
                                 `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建的时间',
                                 `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后一次修改的时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理建筑区域的信息表';


CREATE TABLE `organization_unit` (
                                     `id` varchar(50) PRIMARY KEY COMMENT '记录唯一标识',
                                     `unit_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
                                     `unit_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '单位类型',
                                     `credit_code` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '企业统一社会信用代码',
                                     `project_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '所属项目ID',
                                     `note` text COLLATE utf8_bin COMMENT '额外的备注信息',
                                     `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建的时间',
                                     `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后一次修改的时间',
                                     KEY `project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理与电表相关联的单位信息表';


CREATE TABLE `project` (
                           `id` varchar(50) PRIMARY KEY COMMENT '记录唯一标识',
                           `project_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '项目名称',
                           `project_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '项目类型',
                           `project_description` text COLLATE utf8_bin COMMENT '项目描述',
                           `note` text COLLATE utf8_bin COMMENT '额外的备注信息',
                           `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建的时间',
                           `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后一次修改的时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目表';












