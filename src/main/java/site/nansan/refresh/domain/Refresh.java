package site.nansan.refresh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "refresh")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Refresh {

    @Id @GeneratedValue
    private Long id;
    private String platformId;
    private String refresh;
    private Timestamp expiration;
}
