package com.jisu.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass /* JPA Entity 클래스들이 BaseEntity를 상속할 경우 BaseEntity의 필드들(createDate, modifiedDate)도 컬럼으로 인식 */
@EntityListeners(AuditingEntityListener.class) /* BaseEntity에 Auditing 기능을 포함 */
public class BaseTimeEntity {
    /* 모든 Entity의 상위 클래스가 되어 Entity들의 createDate, modifiedDate를 자동으로 관리하는 역할이다. */

    /* Entity가 생성되어 저장될 때 시간이 자동 저장된다. */
    @CreatedDate
    private LocalDateTime createDate;

    /* 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다. */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
