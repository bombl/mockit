/*==============================================================*/
/* Table: "mockit_service_class"                                */
/*==============================================================*/
create table "mockit_service_class"
(
    "id"                 VARCHAR2(64),
    "service_id"         VARCHAR2(64),
    "class_name"         VARCHAR2(512),
    "mock_enabled"       NUMBER(3) default 1,
    "deleted"            NUMBER(3) default 0,
    "remarks"            VARCHAR2(512),
    "create_at"          DATE,
    "update_at"          DATE,
    constraint PK_MOCKIT_SERVICE_CLASS primary key ("id")
);

comment on column "mockit_service_class"."id" is
    'Primary key id';

comment on column "mockit_service_class"."service_id" is
    'Service id';

comment on column "mockit_service_class"."class_name" is
    'Name of the service class';

comment on column "mockit_service_class"."mock_enabled" is
    'Indicates whether the service class is mocked or not（0.disabled, 1.enabled）';

comment on column "mockit_service_class"."deleted" is
    'Marks if the service class is deleted（0.not deleted, 1.deleted）';

comment on column "mockit_service_class"."remarks" is
    'Additional information or notes';

comment on column "mockit_service_class"."create_at" is
    'Date and time when the service class was created';

comment on column "mockit_service_class"."update_at" is
    'Date and time when the service class was updated';

/*==============================================================*/
/* Index: "idx_service_id"                                      */
/*==============================================================*/
create index "idx_service_id" on "mockit_service_class" (
                                                         "service_id" ASC
    );

/*==============================================================*/
/* Table: "mockit_service_method"                               */
/*==============================================================*/
create table "mockit_service_method"
(
    "id"                 VARCHAR2(64),
    "class_id"           VARCHAR2(64),
    "method_name"        VARCHAR2(128),
    "access_modifer"     VARCHAR2(16),
    "return_type"        VARCHAR2(512),
    "parameters"         VARCHAR2(1024),
    "mock_enabled"       NUMBER(3) default 1,
    "deleted"            NUMBER(3) default 0,
    "remarks"            VARCHAR2(512),
    "create_at"          DATE,
    "update_at"          DATE,
    constraint PK_MOCKIT_SERVICE_METHOD primary key ("id")
);

comment on column "mockit_service_method"."id" is
    'Primary key id';

comment on column "mockit_service_method"."class_id" is
    'Class id';

comment on column "mockit_service_method"."method_name" is
    'Name of the method';

comment on column "mockit_service_method"."access_modifer" is
    'Access modifier of the method (e.g., public, private)';

comment on column "mockit_service_method"."return_type" is
    'Return type of the method';

comment on column "mockit_service_method"."parameters" is
    'List of method parameters (comma-separated)';

comment on column "mockit_service_method"."mock_enabled" is
    'Indicates whether the service method is mocked or not（0.disabled, 1.enabled）';

comment on column "mockit_service_method"."deleted" is
    'Marks if the service method is deleted（0.not deleted, 1.deleted）';

comment on column "mockit_service_method"."remarks" is
    'Additional information or notes';

comment on column "mockit_service_method"."create_at" is
    'Date and time when the service method was created';

comment on column "mockit_service_method"."update_at" is
    'Date and time when the service method was updated';

/*==============================================================*/
/* Index: "idx_class_id"                                        */
/*==============================================================*/
create index "idx_class_id" on "mockit_service_method" (
                                                        "class_id" ASC
    );

/*==============================================================*/
/* Index: "idx_method_name"                                     */
/*==============================================================*/
create index "idx_method_name" on "mockit_service_method" (
                                                           "method_name" ASC
    );

/*==============================================================*/
/* Table: "mockit_service_method_mock_data"                      */
/*==============================================================*/
create table "mockit_service_method_mock_data"
(
    "id"                 VARCHAR2(64),
    "method_id"          VARCHAR2(64),
    "mock_value"         CLOB,
    "mock_enabled"       NUMBER(3) default 1,
    "deleted"            NUMBER(3) default 0,
    "remarks"            VARCHAR2(512),
    "create_at"          DATE,
    "update_at"          DATE,
    constraint PK_MOCKIT_SERVICE_METHOD_MOCK_DATA primary key ("id")
);

comment on column "mockit_service_method_mock_data"."id" is
    'Primary key id';

comment on column "mockit_service_method_mock_data"."method_id" is
    'Method id';

comment on column "mockit_service_method_mock_data"."mock_value" is
    'Mock data for the method';

comment on column "mockit_service_method_mock_data"."mock_enabled" is
    'Indicates whether the mock data is mocked or not（0.disabled, 1.enabled）';

comment on column "mockit_service_method_mock_data"."deleted" is
    'Marks if the mock data is deleted（0.not deleted, 1.deleted）';

comment on column "mockit_service_method_mock_data"."remarks" is
    'Additional information or notes';

comment on column "mockit_service_method_mock_data"."create_at" is
    'Date and time when the mock data was created';

comment on column "mockit_service_method_mock_data"."update_at" is
    'Date and time when the mock data was updated';

/*==============================================================*/
/* Index: "idx_method_id"                                       */
/*==============================================================*/
create index "idx_method_id" on "mockit_service_method_mock_data" (
                                                                  "method_id" ASC
    );

/*==============================================================*/
/* Table: "mockit_service_registry"                             */
/*==============================================================*/
create table "mockit_service_registry"
(
    "id"                 VARCHAR2(64)         not null,
    "alias"              VARCHAR2(128),
    "ip"                 VARCHAR2(40),
    "online"             NUMBER(3),
    "enabled"            NUMBER(3) default 1,
    "deleted"            NUMBER(3) default 0,
    "remarks"            VARCHAR2(512),
    "create_at"          DATE,
    "update_at"          DATE,
    constraint PK_MOCKIT_SERVICE_REGISTRY primary key ("id")
);

comment on column "mockit_service_registry"."id" is
    'Primary key id';

comment on column "mockit_service_registry"."alias" is
    'Service name';

comment on column "mockit_service_registry"."ip" is
    'IP address of the service';

comment on column "mockit_service_registry"."online" is
    'Indicates if the service is online（0.online, 1.offline）';

comment on column "mockit_service_registry"."enabled" is
    'Indicates if the service is enabled or disabled（0.disabled, 1.enabled）';

comment on column "mockit_service_registry"."deleted" is
    'Marks if the service is deleted（0.not deleted, 1.deleted）';

comment on column "mockit_service_registry"."remarks" is
    'Additional information or notes';

comment on column "mockit_service_registry"."create_at" is
    'Date and time when the service was created';

comment on column "mockit_service_registry"."update_at" is
    'Date and time when the service was updated';

/*==============================================================*/
/* Index: "idx_alias"                                           */
/*==============================================================*/
create index "idx_alias" on "mockit_service_registry" (
                                                       "alias" ASC
    );