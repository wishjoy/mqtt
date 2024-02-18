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


-- 电表基础信息填报可区分项目，小区，幢层，状态:0 新增，1启用，2节能，3，停用 增删改查

-- 电表列表查询,包含电表基本信息，是否在线，当前用电量