package io.seorin.ddongkan.entity;

public enum ReportType {
	CROWDED,        // 혼잡도 제보
	INFO_UPDATE,    // 정보 수정 제보 (영업시간/주소 등)
	CLOSED,         // 폐점/이용불가
	DIRTY,          // 위생 문제 제보
	OTHER           // 기타
}
