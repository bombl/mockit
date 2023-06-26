CREATE DATABASE IF NOT EXISTS  `mockit`  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `mockit`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*==============================================================*/
/* Table: mockit_service_class                                  */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS mockit_service_class
(
    id                   varchar(64) comment 'Primary key id',
    service_id           varchar(64) comment 'Service id',
    class_name           varchar(512) comment 'Name of the service class',
    mock_enabled         tinyint(4) default 1 comment 'Indicates whether the service class is mocked or not（0.disabled, 1.enabled）',
    deleted              tinyint(4) default 0 comment 'Marks if the service class is deleted（0.not deleted, 1.deleted）',
    remarks              varchar(512) comment 'Additional information or notes',
    create_at            datetime comment 'Date and time when the service class was created',
    update_at            datetime comment 'Date and time when the service class was updated',
    primary key (id),
    index idx_service_id(service_id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: mockit_service_method                                 */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS mockit_service_method
(
    id                   varchar(64) comment 'Primary key id',
    class_id             varchar(64) comment 'Class id',
    method_name          varchar(128) comment 'Name of the method',
    access_modifer       varchar(16) comment 'Access modifier of the method (e.g., public, private)',
    return_type          varchar(512) comment 'Return type of the method',
    parameters           varchar(1024) comment 'List of method parameters (comma-separated)',
    mock_enabled         tinyint(4) default 1 comment 'Indicates whether the service method is mocked or not（0.disabled, 1.enabled）',
    deleted              tinyint(4) default 0 comment 'Marks if the service method is deleted（0.not deleted, 1.deleted）',
    remarks              varchar(512) comment 'Additional information or notes',
    create_at            datetime comment 'Date and time when the service method was created',
    update_at            datetime comment 'Date and time when the service method was updated',
    primary key (id),
    index idx_class_id(class_id),
    index idx_method_name(method_name)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: mockit_service_method_mock_data                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS mockit_service_method_mock_data
(
    id                   varchar(64) comment 'Primary key id',
    method_id            varchar(64) comment 'Method id',
    mock_value           text comment 'Mock data for the method',
    mock_enabled         tinyint(4) default 1 comment 'Indicates whether the mock data is mocked or not（0.disabled, 1.enabled）',
    deleted              tinyint(4) default 0 comment 'Marks if the mock data is deleted（0.not deleted, 1.deleted）',
    remarks              varchar(512) comment 'Additional information or notes',
    create_at            datetime comment 'Date and time when the mock data was created',
    update_at            datetime comment 'Date and time when the mock data was updated',
    primary key (id),
    index idx_method_id(method_id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: mockit_service_registry                               */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS mockit_service_registry
(
    id                   varchar(64) not null comment 'Primary key id',
    alias                varchar(128) comment 'Service name',
    ip                   varchar(40) comment 'IP address of the service',
    online               tinyint(4) comment 'Indicates if the service is online（0.online, 1.offline）',
    enabled              tinyint(4) default 1 comment 'Indicates if the service is enabled or disabled（0.disabled, 1.enabled）',
    deleted              tinyint(4) default 0 comment 'Marks if the service is deleted（0.not deleted, 1.deleted）',
    remarks              varchar(512) comment 'Additional information or notes',
    create_at            datetime comment 'Date and time when the service was created',
    update_at            datetime comment 'Date and time when the service was updated',
    primary key (id),
    index idx_alias(alias)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;