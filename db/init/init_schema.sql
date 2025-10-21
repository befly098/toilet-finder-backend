-- ===========================================================
-- 💾 똥칸찾기 DB 스키마 초기화 (PostgreSQL)
-- Version: 2025-10-21
-- ===========================================================

-- =====================
-- 1. 기존 테이블 삭제 (개발용)
-- =====================
DROP TABLE IF EXISTS report CASCADE;
DROP TABLE IF EXISTS rating CASCADE;
DROP TABLE IF EXISTS toilet CASCADE;

-- =====================
-- 2. toilet (화장실 기본정보)
-- =====================
CREATE TABLE toilet (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    address VARCHAR(255) NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    open_hours VARCHAR(120),
    accessible BOOLEAN NOT NULL DEFAULT FALSE,
    diaper BOOLEAN NOT NULL DEFAULT FALSE,
    unisex BOOLEAN NOT NULL DEFAULT FALSE,
    rating_sum BIGINT NOT NULL DEFAULT 0,
    rating_count BIGINT NOT NULL DEFAULT 0,
    cleanliness_sum BIGINT NOT NULL DEFAULT 0,
    cleanliness_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

CREATE INDEX idx_toilet_lat_lng ON toilet(lat, lng);
CREATE INDEX idx_toilet_name ON toilet(name);

COMMENT ON TABLE toilet IS '화장실 기본정보';
COMMENT ON COLUMN toilet.accessible IS '장애인 화장실 여부';
COMMENT ON COLUMN toilet.diaper IS '기저귀 교환대 여부';
COMMENT ON COLUMN toilet.unisex IS '남녀 공용 여부';
COMMENT ON COLUMN toilet.rating_sum IS '별점 총합';
COMMENT ON COLUMN toilet.cleanliness_sum IS '청결도 총합';

-- =====================
-- 3. rating (별점·청결도 기록)
-- =====================
CREATE TABLE rating (
    id BIGSERIAL PRIMARY KEY,
    toilet_id BIGINT NOT NULL,
    score INTEGER NOT NULL CHECK (score BETWEEN 1 AND 5),
    cleanliness_score INTEGER NULL CHECK (cleanliness_score IS NULL OR cleanliness_score BETWEEN 1 AND 5),
    device_hash VARCHAR(120) NOT NULL,
    ip_hash VARCHAR(120),
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,

    CONSTRAINT fk_rating_toilet FOREIGN KEY (toilet_id)
        REFERENCES toilet(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_rating_toilet_device UNIQUE (toilet_id, device_hash)
);

CREATE INDEX idx_rating_toilet ON rating(toilet_id);
CREATE INDEX idx_rating_created_at ON rating(created_at);

COMMENT ON TABLE rating IS '사용자별 평점(필수) + 청결도(선택) 기록';
COMMENT ON COLUMN rating.cleanliness_score IS '청결도 점수(1~5, null 허용)';
COMMENT ON COLUMN rating.device_hash IS '디바이스 식별 해시(익명화)';

-- =====================
-- 4. report (사용자 제보)
-- =====================
CREATE TABLE report (
    id BIGSERIAL PRIMARY KEY,
    toilet_id BIGINT NOT NULL,
    type VARCHAR(24) NOT NULL,
    payload TEXT,
    client_hint VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,

    CONSTRAINT fk_report_toilet FOREIGN KEY (toilet_id)
        REFERENCES toilet(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_report_toilet ON report(toilet_id);
CREATE INDEX idx_report_type_created ON report(type, created_at);

COMMENT ON TABLE report IS '사용자 제보 (혼잡, 정보수정, 폐점, 위생 등)';
COMMENT ON COLUMN report.type IS '제보 종류: CROWDED, INFO_UPDATE, CLOSED, DIRTY, OTHER';
COMMENT ON COLUMN report.payload IS 'JSON 포맷 제보 내용';
COMMENT ON COLUMN report.client_hint IS '기기/브라우저 정보';

-- ===========================================================
-- ✅ 완료 메시지
-- ===========================================================
DO $$
BEGIN
    RAISE NOTICE '똥칸찾기 DB 스키마 생성 완료!';
END $$;