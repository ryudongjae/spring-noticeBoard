package ryudongjae.noticeBoard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Columm(nullable=false)는 DDL(JPA에서 자동으로 DB 스키마를 생성,변경 적용을 해줌)을 도와줌

//@Column(nullable=false)를 설정하면 하이버네이트가 DDL를 만들때 해당컬럼에 not null 제약조건을 추가해준다.
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public File(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
