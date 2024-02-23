CREATE TABLE IF NOT EXISTS public.account
(
    id bigint NOT NULL DEFAULT nextval('account_id_seq'::regclass),
    userid character varying COLLATE pg_catalog."default",
    pwd character varying COLLATE pg_catalog."default",
    sex boolean,
    birthday timestamp with time zone,
                           zipcode character varying COLLATE pg_catalog."default",
                           address1 character varying COLLATE pg_catalog."default",
                           address2 character varying COLLATE pg_catalog."default",
                           address3 character varying COLLATE pg_catalog."default",
                           address4 character varying COLLATE pg_catalog."default",
                           email character varying COLLATE pg_catalog."default",
                           receivemail boolean,
                           createdip character varying COLLATE pg_catalog."default",
                           admintxt character varying COLLATE pg_catalog."default",
                           snsid character varying COLLATE pg_catalog."default",
                           phone character varying COLLATE pg_catalog."default",
                           username character varying COLLATE pg_catalog."default",
                           point integer,
                           loginat timestamp without time zone,
                           ip character varying COLLATE pg_catalog."default",
                           useragent character varying COLLATE pg_catalog."default",
                           referer character varying COLLATE pg_catalog."default",
                           created_at timestamp without time zone,
                           updated_at timestamp without time zone,
                           sns character varying COLLATE pg_catalog."default",
                           role character varying COLLATE pg_catalog."default",
                           wishlist character varying[] COLLATE pg_catalog."default",
                           CONSTRAINT account_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;

-- Table: public.account_status

-- DROP TABLE IF EXISTS public.account_status;

CREATE TABLE IF NOT EXISTS public.account_status
(
    id bigint NOT NULL DEFAULT nextval('account_status_id_seq'::regclass),
    userid character varying COLLATE pg_catalog."default",
    snsid character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    accountid bigint,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    sns character varying COLLATE pg_catalog."default",
    status character varying COLLATE pg_catalog."default",
    CONSTRAINT account_status_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account_status
    OWNER to postgres;

-- Table: public.cart_item

-- DROP TABLE IF EXISTS public.cart_item;

CREATE TABLE IF NOT EXISTS public.cart_item
(
    id bigint NOT NULL DEFAULT nextval('account_id_seq'::regclass),
    itemid character varying COLLATE pg_catalog."default",
    userid character varying COLLATE pg_catalog."default",
    cnt integer,
    checked boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT cart_item_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cart_item
    OWNER to postgres;

-- Table: public.content_save

-- DROP TABLE IF EXISTS public.content_save;

CREATE TABLE IF NOT EXISTS public.content_save
(
    id bigint NOT NULL DEFAULT nextval('content_save_id_seq'::regclass),
    user_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    relation_id character varying(36) COLLATE pg_catalog."default",
    outer_path character varying(10) COLLATE pg_catalog."default",
    inner_path character varying(10) COLLATE pg_catalog."default",
    title character varying(100) COLLATE pg_catalog."default",
    content text COLLATE pg_catalog."default",
    created_at date,
    updated_at date,
    CONSTRAINT content_save_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.content_save
    OWNER to postgres;

-- Table: public.faq

-- DROP TABLE IF EXISTS public.faq;

CREATE TABLE IF NOT EXISTS public.faq
(
    id bigint NOT NULL,
    title character varying(30) COLLATE pg_catalog."default" NOT NULL,
    content character varying(30) COLLATE pg_catalog."default" NOT NULL,
    image_url character varying(500)[] COLLATE pg_catalog."default",
    user_id character varying(36) COLLATE pg_catalog."default",
    public_id character varying(36) COLLATE pg_catalog."default",
    category_public_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    question_id character varying(36) COLLATE pg_catalog."default",
    "storeId" character varying(36) COLLATE pg_catalog."default",
    CONSTRAINT faq_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.faq
    OWNER to postgres;

-- Table: public.faq_category

-- DROP TABLE IF EXISTS public.faq_category;

CREATE TABLE IF NOT EXISTS public.faq_category
(
    id bigint NOT NULL,
    parent_public_id uuid,
    public_id uuid,
    title character varying(30) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    store_id character varying(36) COLLATE pg_catalog."default",
    CONSTRAINT faq_category_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.faq_category
    OWNER to postgres;

-- Table: public.image

-- DROP TABLE IF EXISTS public.image;

CREATE TABLE IF NOT EXISTS public.image
(
    file_name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    file_ori_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    path character varying(100) COLLATE pg_catalog."default" NOT NULL,
    size bigint NOT NULL,
    type character varying(5) COLLATE pg_catalog."default" NOT NULL,
    file_url character varying(100) COLLATE pg_catalog."default" NOT NULL,
    outer_path character varying(10) COLLATE pg_catalog."default" NOT NULL,
    inner_path character varying(10) COLLATE pg_catalog."default",
    width integer NOT NULL,
    height integer NOT NULL,
    id bigint NOT NULL DEFAULT nextval('image_id_seq'::regclass),
    created_at date,
    updated_at date,
    CONSTRAINT image_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.image
    OWNER to postgres;

-- Table: public.item

-- DROP TABLE IF EXISTS public.item;

CREATE TABLE IF NOT EXISTS public.item
(
    public_id character varying COLLATE pg_catalog."default",
    category_public_id character varying COLLATE pg_catalog."default",
    title character varying COLLATE pg_catalog."default",
    content text COLLATE pg_catalog."default",
    summary character varying COLLATE pg_catalog."default",
    price integer,
    sell_cnt integer,
    wish_cnt integer,
    click_cnt integer,
    stock_cnt integer,
    avg_review double precision,
    review_cnt integer,
    qna_cnt integer,
    seller_id character varying COLLATE pg_catalog."default",
    free_delivery character varying COLLATE pg_catalog."default",
    image_urls character varying[] COLLATE pg_catalog."default",
    id bigint NOT NULL DEFAULT nextval('item_id_seq'::regclass),
    review_score integer[],
    option character varying[] COLLATE pg_catalog."default",
    product_number character varying COLLATE pg_catalog."default",
    original_work character varying COLLATE pg_catalog."default",
    size character varying COLLATE pg_catalog."default",
    weight character varying COLLATE pg_catalog."default",
    shipping_cost integer,
    material character varying COLLATE pg_catalog."default",
    deadline timestamp with time zone,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
                             status character varying COLLATE pg_catalog."default",
                             CONSTRAINT item_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.item
    OWNER to postgres;

-- Trigger: insert_timestamp_trigger

-- DROP TRIGGER IF EXISTS insert_timestamp_trigger ON public.item;

CREATE OR REPLACE TRIGGER insert_timestamp_trigger
    BEFORE INSERT
    ON public.item
    FOR EACH ROW
    EXECUTE FUNCTION public.insert_timestamp();

-- Trigger: update_timestamp_trigger

-- DROP TRIGGER IF EXISTS update_timestamp_trigger ON public.item;

CREATE OR REPLACE TRIGGER update_timestamp_trigger
    BEFORE UPDATE
                             ON public.item
                             FOR EACH ROW
                             EXECUTE FUNCTION public.update_timestamp();

-- Table: public.item_category

-- DROP TABLE IF EXISTS public.item_category;

CREATE TABLE IF NOT EXISTS public.item_category
(
    title character varying COLLATE pg_catalog."default",
    id bigint NOT NULL DEFAULT nextval('item_category_id_seq'::regclass),
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
                             public_id character varying COLLATE pg_catalog."default",
                             parent_public_id character varying COLLATE pg_catalog."default",
                             CONSTRAINT item_category_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.item_category
    OWNER to postgres;

-- Trigger: insert_timestamp_trigger

-- DROP TRIGGER IF EXISTS insert_timestamp_trigger ON public.item_category;

CREATE OR REPLACE TRIGGER insert_timestamp_trigger
    BEFORE INSERT
    ON public.item_category
    FOR EACH ROW
    EXECUTE FUNCTION public.insert_timestamp();

-- Trigger: update_timestamp_trigger

-- DROP TRIGGER IF EXISTS update_timestamp_trigger ON public.item_category;

CREATE OR REPLACE TRIGGER update_timestamp_trigger
    BEFORE UPDATE
                             ON public.item_category
                             FOR EACH ROW
                             EXECUTE FUNCTION public.update_timestamp();